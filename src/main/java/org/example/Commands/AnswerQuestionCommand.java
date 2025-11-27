package org.example.Commands;

import org.example.Game.GameController;
import org.example.Game.JeopardyGame;
import org.example.Game.Player;
import org.example.Logging.GameEvent;
import org.example.Question.JeopardyQuestion;
import org.example.Question.Option;

public class AnswerQuestionCommand implements Command {
    private final JeopardyQuestion question;
    private final int points;
    private final String userChoice; // This is a label like "A", "B", "C", "D"
    private boolean applied = false;
    private boolean wasCorrect = false;
    private int scoreChange = 0;
    private Player player = null;
    private JeopardyGame game = null;
    private GameController controller = null;
    private String answerText = null;

    public boolean wasCorrect() {
        return wasCorrect;
    }

    public AnswerQuestionCommand(Player player,
                                 JeopardyQuestion question, String choice) {
        this.player = player;
        this.question = question;
        this.points = question.getValue();
        this.userChoice = choice;
    }

    public AnswerQuestionCommand(Player player,
                                 JeopardyQuestion question, String choice, JeopardyGame game) {
        this.player = player;
        this.question = question;
        this.points = question.getValue();
        this.userChoice = choice;
        this.game = game;
        this.controller = null;
    }

    public AnswerQuestionCommand(Player player,
                                 JeopardyQuestion question, String choice, JeopardyGame game, GameController controller) {
        this.player = player;
        this.question = question;
        this.points = question.getValue();
        this.userChoice = choice;
        this.game = game;
        this.controller = controller;
    }

    @Override
    public void execute() {
        if (applied || question.isAnswered()) {
            return;
        }
        if (player == null) {
            throw new IllegalStateException("No current player available");
        }

        int currentScore = player.getScore();
        String correctAnswer = question.getAnswer();
        Option selectedOption = null;
        for (Option opt : question.getOptions()) {
            if (opt.getLabel().equalsIgnoreCase(userChoice != null ? userChoice.trim() : "")) {
                selectedOption = opt;
                break;
            }
        }


        if (selectedOption != null && correctAnswer != null) {
            String answerTrimmed = correctAnswer.trim();
            //unsure which we'd account for so just check  for both for now
            //change later
            String labelTrimmed = selectedOption.getLabel().trim();
            String textTrimmed = selectedOption.getText().trim();

            wasCorrect = answerTrimmed.equalsIgnoreCase(labelTrimmed) ||
                        answerTrimmed.equalsIgnoreCase(textTrimmed);
            answerText = selectedOption.getText();
        } else {
            wasCorrect = false;
            answerText = userChoice; // Fallback to the choice label
        }

        scoreChange = wasCorrect ? points : -points;
        player.changeScore(scoreChange);
        int newScore = player.getScore();
        question.markAnswered();
        
        if (controller != null && player != null) {
            System.out.println("Score updated for " + player.getName() + 
                ": " + (scoreChange >= 0 ? "+" : "") + scoreChange + 
                " points. New score: $" + newScore);
        }
        
        // Complete the turn and log it
        if (game != null) {
            game.completeTurn(userChoice, answerText, wasCorrect, scoreChange);
            
            if (game.getGameLogger() != null) {
                GameEvent event = new GameEvent("Answer Question", player.getName(), null, answerText, wasCorrect ? "Correct" : "Incorrect");
                event.setCategory(question.getCategory());
                event.setScoreAfterPlay(newScore);
                game.getGameLogger().logGameEvent(event);
            }
        }
        
        applied = true;
    }

    @Override
    public void undo() {
        if (!applied) return;
        if (player == null) {
            return;
        }
        int currentScore = player.getScore();
        player.changeScore(currentScore - scoreChange);
        question.reset();
        applied = false;
        wasCorrect = false;
        scoreChange = 0;
    }
}
