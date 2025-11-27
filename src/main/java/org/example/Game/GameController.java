package org.example.Game;

import org.example.Commands.*;
import org.example.Question.JeopardyQuestion;

import java.util.*;

public class GameController {
    private List<Command> commandHistory;
    private List<Player> players;
    private int currentPlayerIndex;
    private boolean gameStarted;
    private JeopardyGame game;
    private Scanner scanner;

    public GameController(JeopardyGame game) {
        this.game = game;
        this.players = new ArrayList<>();
        this.commandHistory = new ArrayList<>();
        this.currentPlayerIndex = 0;
        this.gameStarted = false;
        this.scanner = null;
    }

    public GameController(JeopardyGame game, Scanner scanner) {
        this.game = game;
        this.players = new ArrayList<>();
        this.commandHistory = new ArrayList<>();
        this.currentPlayerIndex = 0;
        this.gameStarted = false;
        this.scanner = scanner;
    }

    public void addPlayer(Player player) {
        if (player == null) {
            throw new IllegalArgumentException("Player cannot be null");
        }
        if (hasPlayerWithName(player.getName())) {
            throw new IllegalArgumentException("A player with the name '" + player.getName() + "' already exists. Player names must be unique.");
        }
        players.add(player);
        if (game != null) {
            game.addPlayer(player);
        }
    }

    public boolean hasPlayerWithName(String name) {
        if (name == null || name.trim().isEmpty()) {
            return false;
        }
        for (Player p : players) {
            if (p.getName().equalsIgnoreCase(name.trim())) {
                return true;
            }
        }
        return false;
    }

    public void removePlayer(Player player) {
        players.remove(player);
    }

    public Player getCurrentPlayer() {
        if (players.isEmpty()) {
            return null;
        }
        return players.get(currentPlayerIndex);
    }

    public void nextPlayer() {
        if (players.isEmpty()) {
            return;
        }
        currentPlayerIndex = (currentPlayerIndex + 1) % players.size();
    }

    public void startGame() {
        if (players.isEmpty()) {
            throw new IllegalStateException("Cannot start game with no players");
        }
        gameStarted = true;
        currentPlayerIndex = 0;
    }

    public void endGame() {
        gameStarted = false;
    }

    public boolean hasGameStarted(){
        return gameStarted;
    }

    public void executeCommand(Command command) {
        commandHistory.add(command);
        command.execute();
    }

    public List<Command> getCommandHistory() {
        return new ArrayList<>(commandHistory);
    }
    public List<Player> getPlayers() {
        return new ArrayList<>(players);
    }

    public JeopardyGame getGame() {
        return game;
    }

    public int getCurrentPlayerIndex() {
        return currentPlayerIndex;
    }

    public void runCLIGame() {
        if (scanner == null) {
            throw new IllegalStateException("Scanner not set for CLI mode");
        }

        System.out.println("\n" + "=".repeat(60));
        System.out.println("          WELCOME TO JEOPARDY PROGRAMMING GAME");
        System.out.println("=".repeat(60) + "\n");

        setupPlayers();
        startGameCLI();
        gameLoop();
    }

    private void setupPlayers() {
        System.out.println("SETUP PLAYERS");
        System.out.println("-".repeat(60));

        int numPlayers = getIntInput("Enter number of players (1-4): ", 1, 4);
        
        SetPLayerCountCommand setCountCmd = new SetPLayerCountCommand(game, numPlayers);
        executeCommand(setCountCmd);

        for (int i = 1; i <= numPlayers; i++) {
            String name;
            boolean nameValid = false;
            
            do {
                System.out.print("Enter name for Player " + i + ": ");
                name = scanner.nextLine().trim();
                if (name.isEmpty()) {
                    name = "Player " + i;
                }
                
                if (hasPlayerWithName(name)) {
                    System.out.println("Error: A player with the name '" + name + "' already exists. Please choose a different name.");
                    nameValid = false;
                } else {
                    nameValid = true;
                }
            } while (!nameValid);

            Player player = new Player(name);
            AddPlayerCommand addCmd = new AddPlayerCommand(this, player);
            executeCommand(addCmd);
            System.out.println("Player " + name + " added!\n");
        }
    }

    private void startGameCLI() {
        System.out.println("\n" + "=".repeat(60));
        System.out.println("                    GAME STARTING");
        System.out.println("=".repeat(60) + "\n");

        StartGameCommand startCmd = new StartGameCommand(this);
        executeCommand(startCmd);

        DisplayScoresCommand scoresCmd = new DisplayScoresCommand(game);
        executeCommand(scoresCmd);
    }

    private void gameLoop() {
        List<JeopardyQuestion> availableQuestions = new ArrayList<>(game.getQuestions());

        while (!availableQuestions.isEmpty() && gameStarted) {
            Player currentPlayer = getCurrentPlayer();
            System.out.println("\n" + "=".repeat(60));
            System.out.println("Current Player: " + currentPlayer.getName() + " | Score: $" + currentPlayer.getScore());
            System.out.println("=".repeat(60));

            displayCategories(availableQuestions);

            JeopardyQuestion selectedQuestion = selectQuestion(availableQuestions);
            if (selectedQuestion == null) {
                break;
            }

            answerQuestion(selectedQuestion, currentPlayer);
            availableQuestions.remove(selectedQuestion);

            DisplayScoresCommand scoresCmd = new DisplayScoresCommand(game);
            executeCommand(scoresCmd);

            if (game.areAllQuestionsAnswered()) {
                break;
            }

            nextPlayer();
        }

        endGameCLI();
    }

    private void displayCategories(List<JeopardyQuestion> questions) {
        Map<String, List<JeopardyQuestion>> categories = new HashMap<>();
        for (JeopardyQuestion q : questions) {
            String category = q.getCategory();
            if (!categories.containsKey(category)) {
                categories.put(category, new ArrayList<>());
            }
            categories.get(category).add(q);
        }

        System.out.println("\nAvailable Categories:");
        int index = 1;
        List<String> categoryList = new ArrayList<>(categories.keySet());
        for (String category : categoryList) {
            System.out.println(index + ". " + category + " (" + categories.get(category).size() + " questions)");
            index++;
        }
    }

    private JeopardyQuestion selectQuestion(List<JeopardyQuestion> questions) {
        Map<String, List<JeopardyQuestion>> categories = new HashMap<>();
        for (JeopardyQuestion q : questions) {
            String category = q.getCategory();
            if (!categories.containsKey(category)) {
                categories.put(category, new ArrayList<>());
            }
            categories.get(category).add(q);
        }

        List<String> categoryList = new ArrayList<>(categories.keySet());
        if (categoryList.isEmpty()) {
            return null;
        }

        int categoryChoice = getIntInput("\nSelect a category (1-" + categoryList.size() + "): ", 1, categoryList.size());
        String selectedCategory = categoryList.get(categoryChoice - 1);
        
        SelectCategoryCommand categoryCmd = new SelectCategoryCommand(this, game, selectedCategory);
        executeCommand(categoryCmd);

        List<JeopardyQuestion> categoryQuestions = categories.get(selectedCategory);
        System.out.println("\nQuestions in " + selectedCategory + ":");
        for (int i = 0; i < categoryQuestions.size(); i++) {
            JeopardyQuestion q = categoryQuestions.get(i);
            System.out.println((i + 1) + ". $" + q.getValue());
        }

        int questionChoice = getIntInput("Select a question (1-" + categoryQuestions.size() + "): ", 1, categoryQuestions.size());
        JeopardyQuestion selectedQuestion = categoryQuestions.get(questionChoice - 1);

        SelectQuestionCommand selectCmd = new SelectQuestionCommand(
            selectedQuestion,
            getCurrentPlayer(),
            game
        );
        executeCommand(selectCmd);

        return selectedQuestion;
    }

    private void answerQuestion(JeopardyQuestion question, Player player) {
        System.out.print("\nEnter your answer (A, B, C, or D): ");
        String answer = scanner.nextLine().trim().toUpperCase();

        while (!answer.matches("[A-D]")) {
            System.out.print("Invalid input. Enter A, B, C, or D: ");
            answer = scanner.nextLine().trim().toUpperCase();
        }

        AnswerQuestionCommand answerCmd = new AnswerQuestionCommand(
            player,
            question,
            answer,
            game,
            this
        );
        executeCommand(answerCmd);

        System.out.println("\n" + "=".repeat(60));
        if (answerCmd.wasCorrect()) {
            System.out.println("CORRECT! You earned $" + question.getValue());
        } else {
            System.out.println("INCORRECT! You lost $" + question.getValue());
            System.out.println("Correct answer was: " + question.getAnswer());
        }
        System.out.println("=".repeat(60));
    }

    private void endGameCLI() {
        System.out.println("\n" + "=".repeat(60));
        System.out.println("                    GAME OVER");
        System.out.println("=".repeat(60) + "\n");

        EndGameCommand endCmd = new EndGameCommand(this, game);
        executeCommand(endCmd);
    }

    private int getIntInput(String prompt, int min, int max) {
        int value = -1;
        while (value < min || value > max) {
            System.out.print(prompt);
            try {
                value = Integer.parseInt(scanner.nextLine().trim());
                if (value < min || value > max) {
                    System.out.println("Please enter a number between " + min + " and " + max);
                }
            } catch (NumberFormatException e) {
                System.out.println("Please enter a valid number");
            }
        }
        return value;
    }
}
