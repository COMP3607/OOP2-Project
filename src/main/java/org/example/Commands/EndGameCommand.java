package org.example.Commands;

import org.example.Game.GameController;
import org.example.Game.JeopardyGame;

/**
 * A command that ends the current Jeopardy game session 
 * using the provided {@link GameController} and {@link JeopardyGame}.
 * 
 * <p>Executing this command stops the game, displays final scores, prints a
 * farewell message, and then terminates the program. Because exiting the
 * application cannot be reversed, the {@link #undo()} method performs no action.</p>
 */
public class EndGameCommand implements Command {

    /** The controller managing the game's active state. */
    private final GameController controller;

    /** The Jeopardy game instance whose scores will be displayed. */
    private final JeopardyGame game;

    /**
     * Creates a new EndGameCommand.
     *
     * @param controller the controller used to end the game
     * @param game the game instance used to display final scores
     */
    public EndGameCommand(GameController controller, JeopardyGame game) {
        this.controller = controller;
        this.game = game;
    }

    /**
     * Executes the command by ending the game, printing final results,
     * and exiting the program.
     */
    @Override
    public void execute() {
        controller.endGame();
        System.out.println("\nThank you for playing Jeopardy! Final scores:");
        new DisplayScoresCommand(game).execute();
        System.out.println("\nGoodbye!\n");
        System.exit(0);
    }

    /**
     * Undo is not supported for this command because the program exits.
     */
    @Override
    public void undo() {
        System.out.println("Cannot undo exit.");
    }
}