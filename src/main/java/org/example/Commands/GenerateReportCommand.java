package org.example.Commands;

import org.example.Game.JeopardyGame;
import org.example.Logging.GameEvent;

import java.io.IOException;
import java.time.LocalDateTime;

public class GenerateReportCommand implements Command {
    private final JeopardyGame game;
    private final String filename;

    public GenerateReportCommand(JeopardyGame game, String filename) {
        this.game = game;
        this.filename = filename;
    }

    @Override
    public void execute() {
        try {
            game.generateReport(filename);
            System.out.println("Report generated: " + filename);
            
            if (game != null && game.getGameLogger() != null) {
                GameEvent event = new GameEvent("Generate Report", "System", LocalDateTime.now(), "", "N/A");
                game.getGameLogger().logGameEvent(event);
            }
        } catch (IOException e) {
            System.err.println("Error generating report: " + e.getMessage());
        }
    }

    @Override
    public void undo() {
    }
}

