package org.example.Commands;

import org.example.Game.GameController;
import org.example.Game.JeopardyGame;
import org.example.Logging.GameEvent;

import java.time.LocalDateTime;

public class EndGameCommand implements Command{
    private final GameController controller;
    private final JeopardyGame game;

   public EndGameCommand(GameController controller, JeopardyGame game){
        this.controller = controller;
        this.game = game;
    }
    @Override
    public void execute(){
        controller.endGame();
        System.out.println("\nThank you for playing Jeopardy! Final scores:");
        DisplayScoresCommand scoresCmd = new DisplayScoresCommand(game);
        controller.executeCommand(scoresCmd);
        String caseID = game.getGameLogger().getGameCaseID();
        GenerateReportCommand reportCmd = new GenerateReportCommand(game, caseID + "_report.txt");
        controller.executeCommand(reportCmd);
        
        GenerateEventLogCommand eventLogCmd = new GenerateEventLogCommand(game, caseID + "_event_log.csv");
        controller.executeCommand(eventLogCmd);
        
        if (game.getGameLogger() != null) {
            GameEvent event = new GameEvent("Exit Game", "System", LocalDateTime.now(), "", "N/A");
            game.getGameLogger().logGameEvent(event);
            
            try {
                game.generateEventLog(caseID + "_event_log.csv");
            } catch (java.io.IOException e) {
                System.err.println("Error regenerating event log: " + e.getMessage());
            }
        }
        
        int commandCount = controller.getCommandHistory().size();
        for (int i = 0; i < commandCount; i++) {
            Command cmd = controller.getCommandHistory().get(i);
            System.out.println("  " + (i + 1) + ". " + cmd.getClass().getSimpleName());
        }


        System.exit(0);
    }
    @Override
    public void undo(){
      System.out.println("Cannot undo exit.");
    }
}