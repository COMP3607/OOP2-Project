package org.example.Commands;

import org.example.Game.GameController;
import org.example.Game.JeopardyGame;

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
      DisplayScoresCommand(game).execute();
      System.out.println("\nGoodbye!\n");
      System.exit(0);
    }
    @Override
    public void undo(){
      System.out.println("Cannot undo exit.");
    }
}