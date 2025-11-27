package org.example.Commands;

import org.example.Game.GameController;
import org.example.Game.Player;

/**
 * A command that removes a {@link Player} from the {@link GameController}.
 * 
 * <p>This class follows the Command pattern. Executing the command removes the
 * player, while undoing the command adds them back to the game.</p>
 */
public class RemovePlayerCommand implements Command {
    
    /** The controller that manages player state. */
    private final GameController controller;

    /** The player to remove or restore. */
    private final Player player;

    /**
     * Creates a new RemovePlayerCommand.
     *
     * @param controller the game controller responsible for player management
     * @param player the player to be removed
     */
    public RemovePlayerCommand(GameController controller, Player player) {
        this.controller = controller;
        this.player = player;
    }

    /**
     * Executes the command by removing the player from the controller.
     */
    @Override
    public void execute() {
        controller.removePlayer(player);
    }

    /**
     * Undoes the command by adding the player back to the controller.
     */
    @Override
    public void undo() {
        controller.addPlayer(player);
    }
}
