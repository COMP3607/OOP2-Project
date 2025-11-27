package org.example.Commands;

import org.example.Game.JeopardyGame;
import org.example.Game.Player;
import org.example.Logging.GameEvent;
import org.example.Question.JeopardyQuestion;
import org.example.Question.Option;

public class SelectQuestionCommand implements Command {
    private final JeopardyQuestion question;
    private final Player currentPlayer;
    private final JeopardyGame game;

    public SelectQuestionCommand(JeopardyQuestion question, Player currentPlayer, JeopardyGame game) {
        this.question = question;
        this.currentPlayer = currentPlayer;
        this.game = game;
    }

    @Override
    public void execute() {
        if (question.isAnswered()) {
            System.out.println("This question has already been answered!");
            return;
        }

        // Start a new turn and track category/question selection
        if (game != null && currentPlayer != null) {
            game.startTurn(currentPlayer);
            game.setCurrentTurnCategory(question.getCategory());
            game.setCurrentTurnQuestion(question);
            
            if (game.getGameLogger() != null) {
                GameEvent event = new GameEvent("Select Question", currentPlayer.getName(), null, String.valueOf(question.getValue()), "N/A");
                event.setCategory(question.getCategory());
                event.setScoreAfterPlay(currentPlayer.getScore());
                game.getGameLogger().logGameEvent(event);
            }
        }

        System.out.println("\n" + "=".repeat(50));
        System.out.println("Category: " + question.getCategory() + "  |  Value: $" + question.getValue());
        System.out.println("Player: " + currentPlayer.getName());
        System.out.println("\nCLUE:");
        System.out.println(question.getPrompt());

        System.out.println("\nOptions:");
        for (Option opt : question.getOptions()) {
            System.out.println(opt.getLabel() + ". " + opt.getText());
        }
        System.out.println("=".repeat(50));
    }

    @Override
    public void undo() {
    }
}