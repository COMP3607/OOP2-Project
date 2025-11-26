package org.example.Commands;

import org.example.Game.JeopardyGame;

public class PrintQuestionsCommand implements Command {
    private final JeopardyGame game;

    public PrintQuestionsCommand(JeopardyGame game) {
        this.game = game;
    }

    @Override
    public void execute() {
        game.printQuestions();
    }

    @Override
    public void undo() {
        // nothing to revert
    }
}
