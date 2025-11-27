package org.example.Commands;

import  org.example.Game.JeopardyGame;

/**
 * A command that sets the number of players for the {@link JeopardyGame}.
 * 
 * <p>Executing this command updates the player count in the game. Undoing the
 * command resets the player count to a default value of 1.</p>
 */
public class SetPLayerCountCommand implements Command {

    /** The Jeopardy game instance being modified. */
    private final JeopardyGame game;

    /** The new desired number of players. */
    private final int playerCount;

    /**
     * Creates a new SetPlayerCountCommand.
     *
     * @param game the Jeopardy game whose player count will be updated
     * @param playerCount the number of players to set
     */
    public SetPLayerCountCommand(JeopardyGame game, int playerCount) {
        this.game = game;
        this.playerCount = playerCount;
    }

    /**
     * Executes the command by updating the game's player count.
     */
    @Override
    public void execute() {
        game.setPlayerCount(playerCount);
        System.out.println("Player count set to " + playerCount);
    }

    /**
     * Undoes the command by resetting the player count to 1.
     */
    @Override
    public void undo() {
        game.setPlayerCount(1);
        System.out.println("Player count reset to " + 1);
    }
}