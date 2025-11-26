package org.example.Commands;

import org.example.Game.Player;
import org.example.Question.JeopardyQuestion;
import org.example.Question.Option;

public class SelectQuestionCommand implements Command {
    private final JeopardyQuestion question;
    private final Player currentPlayer;

    public SelectQuestionCommand(JeopardyQuestion question, Player currentPlayer) {
        this.question = question;
        this.currentPlayer = currentPlayer;
    }

    @Override
    public void execute() {
        if (question.isAnswered()) {
            System.out.println("This question has already been answered!");
            return;
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