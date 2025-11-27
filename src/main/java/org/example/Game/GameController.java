package org.example.Game;


import org.example.Commands.Command;

import java.util.ArrayList;
import java.util.List;


/**
 * Controls the flow of a general game, including managing players,
 * tracking the current player, maintaining a history of commands,
 * and handling game state.
*/
public class GameController {

    /** List of executed commands for undo/redo or logging purposes */
    private ArrayList<Command> commandHistory;

    /** List of players participating in the game */
    private ArrayList<Player> players;

    /** Index of the current player in the players list */
    private int currentPlayerIndex;

    /** Indicates whether the game has started */
    private boolean gameStarted;
    private final JeopardyGame jeopardyGame;

    /**
     * Constructs a new GameController with an empty player list
     * and command history.
    */
    public GameController(){
        this.players = new ArrayList<>();
        this.commandHistory = new ArrayList<>();
        this.currentPlayerIndex = 0;
        this.gameStarted = false;
        this.jeopardyGame = new JeopardyGame(new ArrayList<>());
    }


    /**
     * Adds a player to the game.
     * 
     * @param player the Player to add; must not be null
     * @throws IllegalArgumentException if the player is null
    */
    public void addPlayer(Player player) {
        if (player == null) {
            throw new IllegalArgumentException("Player cannot be null");
        }
        players.add(player);
    }

    /**
     * Removes a player from the game.
     * 
     * @param player the Player to remove
    */
    public void removePlayer(Player player) {
        players.remove(player);
    }

    /**
     * Retrieves the player whose turn it currently is.
     * 
     * @return the current Player, or null if no players exist
    */
    public Player getCurrentPlayer() {
        if (players.isEmpty()) {
            return null;
        }
        return players.get(currentPlayerIndex);
    }


    /**
     * Starts the game by setting the game state to started
     * and resetting the current player index to 0.
     * 
     * @throws IllegalStateException if there are no players
    */
    public void startGame() {
        if (players.isEmpty()) {
            throw new IllegalStateException("Cannot start game with no players");
        }
        gameStarted = true;
        currentPlayerIndex = 0;
    }

    
    public void playerScored(Player player, int points) {
        player.changeScore(points);
        jeopardyGame.updatePlayerScore(player, points);
    }


    /**
     * Ends the game by setting the game state to not started.
    */

    public void endGame() {
        gameStarted = false;
    }

    /**
     * Checks whether the game has started.
     * 
     * @return true if the game has started; false otherwise
    */
    public boolean hasGameStarted(){
        return gameStarted;
    }

    /**
     * Returns a copy of the command history.
     * 
     * @return a List of executed Command objects
    */
    public List<Command> getCommandHistory() {
        return new ArrayList<>(commandHistory);
    }

    /**
     * Returns a copy of the list of players.
     * 
     * @return a List of Player objects
    */
    public List<Player> getPlayers() {
        return new ArrayList<>(players);
    }



}
