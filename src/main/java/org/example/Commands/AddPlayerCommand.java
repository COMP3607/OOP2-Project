package org.example.Commands;

import org.example.Game.GameController;
import org.example.Game.Player;
import org.example.Logging.GameEvent;

public class AddPlayerCommand implements Command{
    private final GameController controller;
    private final Player player;

    public AddPlayerCommand(GameController controller, Player player) {
        this.controller = controller;
        this.player = player;
    }

    @Override
    public void execute() {
        controller.addPlayer(player);
        
        if (controller.getGame() != null && controller.getGame().getGameLogger() != null) {
            GameEvent event = new GameEvent("Enter Player Name", player.getName(), null, player.getName(), "N/A");
            controller.getGame().getGameLogger().logGameEvent(event);
        }
    }

    @Override
    public void undo() {
        controller.removePlayer(player);
    }
}
