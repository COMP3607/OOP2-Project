package org.example.Commands;

import org.example.Game.JeopardyGame;
import org.example.Logging.GameEvent;

import java.time.LocalDateTime;

public class SetPLayerCountCommand implements Command{
    private final JeopardyGame game;
    private final int playerCount;
    
    public SetPLayerCountCommand(JeopardyGame game, int playerCount){
        this.game = game;
        this.playerCount = playerCount;
    }
    @Override
    public void execute(){
        game.setPlayerCount(playerCount);
        System.out.println("Player count set to "+playerCount);
        
        if (game != null && game.getGameLogger() != null) {
            GameEvent event = new GameEvent("Select Player Count", "System", LocalDateTime.now(), String.valueOf(playerCount), "N/A");
            game.getGameLogger().logGameEvent(event);
        }
    }
    @Override
    public void undo(){
        game.setPlayerCount(1);
        System.out.println("Player count reset to "+ 1);
    }
}