package org.example.Commands;

import org.example.Game.JeopardyGame;
import org.example.Logging.GameEvent;

import java.io.IOException;
import java.time.LocalDateTime;

public class GenerateEventLogCommand implements Command {
    private final JeopardyGame game;
    private final String filename;

    public GenerateEventLogCommand(JeopardyGame game, String filename) {
        this.game = game;
        this.filename = filename;
    }

    @Override
    public void execute() {
        try {
            if (game != null && game.getGameLogger() != null) {
                GameEvent event = new GameEvent("Generate Event Log", "System", LocalDateTime.now(), "", "N/A");
                game.getGameLogger().logGameEvent(event);
            }
            
            game.generateEventLog(filename);
            System.out.println("Event log generated: " + filename);
        } catch (IOException e) {
            System.err.println("Error generating event log: " + e.getMessage());
        }
    }

    @Override
    public void undo() {
    }
}

