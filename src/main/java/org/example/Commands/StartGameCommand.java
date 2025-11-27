package org.example.Commands;

import org.example.Game.GameController;
import org.example.Logging.GameEvent;

import java.time.LocalDateTime;

public class StartGameCommand implements Command {
    private final GameController controller;

    public StartGameCommand(GameController controller) {
        this.controller = controller;
    }

    @Override
    public void execute() {
        controller.startGame();
        System.out.println("Game started with " + controller.getPlayers().size() + " players!");
        
        if (controller.getGame() != null && controller.getGame().getGameLogger() != null) {
            GameEvent event = new GameEvent("Start Game", "System", LocalDateTime.now());
            controller.getGame().getGameLogger().logGameEvent(event);
        }
    }

    @Override
    public void undo() {
        controller.endGame();
    }
}