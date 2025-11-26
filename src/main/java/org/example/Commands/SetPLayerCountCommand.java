package org.example.Commands;

import  org.example.Game.JeopardyGame;

public class SetPLayerCountCommand implements Command{
    private final JeopardyGame game;
    private final int playerCount;
    //private int oldPlayerCount;
    public SetPLayerCountCommand(JeopardyGame game, int playerCount){
        this.game = game;
        this.playerCount = playerCount;
    }
    @Override
    public void execute(){
        game.setPlayerCount(playerCount);
        System.out.println("Player count set to "+playerCount);
    }
    @Override
    public void undo(){
        game.setPlayerCount(1);
        System.out.println("Player count reset to "+ 1);
    }
}