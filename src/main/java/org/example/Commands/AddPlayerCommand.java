package org.example.Commands;

import org.example.Game.GameController;
import org.example.Game.Player;

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
    }

    @Override
    public void undo() {
        controller.removePlayer(player);
    }
}
