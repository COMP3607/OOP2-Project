package org.example.Commands;

import org.example.Game.GameController;
import org.example.Game.Player;

public class RemovePlayerCommand implements Command {
    private final GameController controller;
    private final Player player;

    public RemovePlayerCommand(GameController controller, Player player) {
        this.controller = controller;
        this.player = player;
    }

    @Override
    public void execute() {
        controller.removePlayer(player);
    }

    @Override
    public void undo() {
        controller.addPlayer(player);
    }
}
