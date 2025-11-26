package org.example.Commands;

import org.example.Game.GameController;

public class StartGameCommand implements Command {
    private final GameController controller;

    public StartGameCommand(GameController controller) {
        this.controller = controller;
    }

    @Override
    public void execute() {
        controller.startGame();
        System.out.println("Game started with " + controller.getPlayers().size() + " players!");
    }

    @Override
    public void undo() {
        controller.endGame();
    }
}