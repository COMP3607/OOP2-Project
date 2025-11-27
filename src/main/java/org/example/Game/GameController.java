package org.example.Game;


import org.example.Commands.Command;

import java.util.ArrayList;
import java.util.List;

public class GameController {

    private ArrayList<Command> commandHistory;
    private ArrayList<Player> players;
    private int currentPlayerIndex;
    private boolean gameStarted;
    private final JeopardyGame jeopardyGame;

    public GameController(){
        this.players = new ArrayList<>();
        this.commandHistory = new ArrayList<>();
        this.currentPlayerIndex = 0;
        this.gameStarted = false;
        this.jeopardyGame = new JeopardyGame(new ArrayList<>());
    }

    public void addPlayer(Player player) {
        if (player == null) {
            throw new IllegalArgumentException("Player cannot be null");
        }
        players.add(player);
    }

    public void removePlayer(Player player) {
        players.remove(player);
    }

    public Player getCurrentPlayer() {
        if (players.isEmpty()) {
            return null;
        }
        return players.get(currentPlayerIndex);
    }

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
    public void endGame() {
        gameStarted = false;
    }

    public boolean hasGameStarted(){
        return gameStarted;
    }

    public List<Command> getCommandHistory() {
        return new ArrayList<>(commandHistory);
    }
    public List<Player> getPlayers() {
        return new ArrayList<>(players);
    }



}
