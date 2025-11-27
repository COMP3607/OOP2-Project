package org.example.Commands;

import org.example.Game.GameController;

/**
 * A command that starts the game using the provided {@link GameController}.
 * 
 * <p>When executed, this command initializes the game and outputs a summary of
 * the number of players. Undoing the command ends the game session.</p>
 */
public class StartGameCommand implements Command {

    /** The controller responsible for handling game flow. */
    private final GameController controller;

    /**
     * Creates a new StartGameCommand.
     *
     * @param controller the controller that manages the game life cycle
     */
    public StartGameCommand(GameController controller) {
        this.controller = controller;
    }

    /**
     * Executes the command by starting the game through the controller.
     */
    @Override
    public void execute() {
        controller.startGame();
        System.out.println("Game started with " + controller.getPlayers().size() + " players!");
    }

    /**
     * Undoes the command by ending the game session.
     */
    @Override
    public void undo() {
        controller.endGame();
    }
}