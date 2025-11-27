package org.example.Commands;

import org.example.Game.GameController;
import org.example.Game.JeopardyGame;

/**
 * A command that prints all available questions managed by the {@link JeopardyGame}. 
 * 
 * <p>This command is informational only and has no side effects on game state.
 * As such, the {@link #undo()} operation performs no action.</p>
 */
public class PrintQuestionsCommand implements Command {

    /** The Jeopardy game providing access to the question list. */
    private final JeopardyGame game;

    /**
     * Creates a new PrintQuestionsCommand.
     *
     * @param game the game that holds the questions
     */
    public PrintQuestionsCommand(JeopardyGame game) {
        this.game = game;
    }

    /**
     * Executes the command by printing all available questions.
     */
    @Override
    public void execute() {
        game.printQuestions();
    }

    /**
     * No action is taken because printing is not reversible.
     */
    @Override
    public void undo() {
        // nothing to revert
    }
}
