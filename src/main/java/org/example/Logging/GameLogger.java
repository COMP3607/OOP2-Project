package org.example.Logging;

import org.example.Game.JeopardyTurn;
import org.example.Game.Player;

import java.util.ArrayList;
import java.util.List;

public class GameLogger implements EventLogger {
    private List<JeopardyTurn> jeopardyTurns;
    private String gameCaseID;
    private List<Player> players;
    private List<GameEvent> gameEvents;

    public GameLogger(String gameCaseID) {
        this.gameCaseID = gameCaseID;
        this.jeopardyTurns = new ArrayList<>();
        this.players = new ArrayList<>();
        this.gameEvents = new ArrayList<>();
    }

    @Override
    public void logEvent(JeopardyTurn jeopardyTurn) {
        jeopardyTurns.add(jeopardyTurn);
        if (jeopardyTurn.getPlayer() != null) {
            logPlayerAdded(jeopardyTurn.getPlayer());
        }
    }

    @Override
    public void logPlayerAdded(Player player) {
        if (!players.contains(player)) {
            players.add(player);
        }
    }

    @Override
    public List<JeopardyTurn> getLoggedTurns() {
        return new ArrayList<>(jeopardyTurns);
    }

    @Override
    public List<Player> getLoggedPlayers() {
        return new ArrayList<>(players);
    }

    @Override
    public String getCaseID() {
        return gameCaseID;
    }

    @Override
    public void clear() {
        jeopardyTurns.clear();
        players.clear();
    }

    public void addPlayer(Player player) {
        logPlayerAdded(player);
    }

    public String getGameCaseID() {
        return getCaseID();
    }


    public void logGameEvent(GameEvent event) {
        if (event != null) {
            gameEvents.add(event);
        }
    }

    public List<GameEvent> getLoggedGameEvents() {
        return new ArrayList<>(gameEvents);
    }
}

