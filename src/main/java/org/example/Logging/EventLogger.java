package org.example.Logging;

import org.example.Game.JeopardyTurn;
import org.example.Game.Player;

import java.util.List;

public interface EventLogger {
    void logEvent(JeopardyTurn jeopardyTurn);
    void logPlayerAdded(Player player);
    List<JeopardyTurn> getLoggedTurns();
    List<Player> getLoggedPlayers();
    String getCaseID();
    void clear();
}

