package org.example.Commands;

import org.example.Game.GameController;
import org.example.Game.Player;

/**
 * A command that adds a {@link Player} to the {@link GameController}.
 * 
 * <p>This class follows the Command design pattern. Executing the command
 * will add the specified player, while undoing the command will remove
 * that same player.</p>
 */

public class AddPlayerCommand implements Command{
    
    /** The game controller that manages players and game state. */
    private final GameController controller;

    /** The player instance to add or remove. */
    private final Player player;

    /**
     * Creates a new AddPlayerCommand.
     *
     * @param controller the game controller responsible for managing players
     * @param player the player to be added or removed
     */
    public AddPlayerCommand(GameController controller, Player player) {
        this.controller = controller;
        this.player = player;
    }

    /**
     * Executes the command by adding the player to the controller.
     */
    @Override
    public void execute() {
        controller.addPlayer(player);
    }

    /**
     * Undoes the command by removing the previously added player.
     */
    @Override
    public void undo() {
        controller.removePlayer(player);
    }
}
