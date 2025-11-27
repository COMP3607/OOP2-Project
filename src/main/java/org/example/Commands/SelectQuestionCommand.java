package org.example.Commands;

import org.example.Game.Player;
import org.example.Question.JeopardyQuestion;
import org.example.Question.Option;

/**
 * A command that displays the details of a selected {@link JeopardyQuestion}
 * for the current player. 
 * 
 * <p>Executing this command prints the question category, value, clue, and
 * available answer options to the console. If the question has already been
 * answered, the command notifies the user and does nothing.</p>
 * 
 * <p>This command has no side effects on game state, and therefore its
 * {@link #undo()} method performs no action.</p>
 */
public class SelectQuestionCommand implements Command {

    /** The question being presented to the player. */
    private final JeopardyQuestion question;

    /** The player currently selecting the question. */
    private final Player currentPlayer;

    /**
     * Creates a new SelectQuestionCommand.
     *
     * @param question the question the player is attempting to view
     * @param currentPlayer the active player selecting the question
     */
    public SelectQuestionCommand(JeopardyQuestion question, Player currentPlayer) {
        this.question = question;
        this.currentPlayer = currentPlayer;
    }

    /**
     * Executes the command by printing the question details if it has not
     * already been answered.
     */
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

    /**
     * This command performs no state changes, so undoing has no effect.
     */
    @Override
    public void undo() {
        // No operation
    }
}
