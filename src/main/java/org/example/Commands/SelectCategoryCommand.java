package org.example.Commands;

import org.example.Game.GameController;
import org.example.Game.JeopardyGame;
import org.example.Logging.GameEvent;

public class SelectCategoryCommand implements Command {
    private final GameController controller;
    private final JeopardyGame game;
    private final String category;

    public SelectCategoryCommand(GameController controller, JeopardyGame game, String category) {
        this.controller = controller;
        this.game = game;
        this.category = category;
    }

    @Override
    public void execute() {
        if (game != null && category != null) {
            System.out.println("Category selected: " + category);
            
            if (controller != null && controller.getCurrentPlayer() != null && game.getGameLogger() != null) {
                GameEvent event = new GameEvent("Select Category", controller.getCurrentPlayer().getName(), null, "", "N/A");
                event.setCategory(category);
                event.setScoreAfterPlay(controller.getCurrentPlayer().getScore());
                game.getGameLogger().logGameEvent(event);
            }
        }
    }

    @Override
    public void undo() {
    }
}

