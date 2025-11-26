package org.example.Commands;

import org.example.Game.GameController;
import org.example.Game.Player;
import org.example.Question.JeopardyQuestion;

public class AnswerQuestionCommand implements Command {
    private final GameController controller;
    private final JeopardyQuestion question;
    private final int points;
    private boolean applied = false;

    public AnswerQuestionCommand(GameController controller,
                                 JeopardyQuestion question) {
        this.controller = controller;
        this.question = question;
        this.points = question.getValue();
    }

    @Override
    public void execute() {
        Player p = controller.getCurrentPlayer();
        int cur = p.getScore();
        p.changeScore(cur + points);
        question.markAnswered();
        applied = true;
    }

    @Override
    public void undo() {
        if (!applied) return;
        Player p = controller.getCurrentPlayer();
        int cur = p.getScore();
        p.changeScore(cur - points);
        question.reset();
    }
}
