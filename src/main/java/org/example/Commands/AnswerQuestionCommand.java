package org.example.Commands;

import org.example.Game.GameController;
import org.example.Game.Player;
import org.example.Question.JeopardyQuestion;
import org.example.Question.Option;

public class AnswerQuestionCommand implements Command {
    private final GameController controller;
    private final JeopardyQuestion question;
    private final int points;
    private final String userChoice; // This is a label like "A", "B", "C", "D"
    private boolean applied = false;
    private boolean wasCorrect = false;
    private int scoreChange = 0;

    public AnswerQuestionCommand(GameController controller,
                                 JeopardyQuestion question, String choice) {
        this.controller = controller;
        this.question = question;
        this.points = question.getValue();
        this.userChoice = choice;
    }

    @Override
    public void execute() {
        if (applied || question.isAnswered()) {
            return;
        }

        Player p = controller.getCurrentPlayer();
        if (p == null) {
            throw new IllegalStateException("No current player available");
        }

        int currentScore = p.getScore();
        String correctAnswer = question.getAnswer();
        Option selectedOption = null;
        for (Option opt : question.getOptions()) {
            if (opt.getLabel().equalsIgnoreCase(userChoice != null ? userChoice.trim() : "")) {
                selectedOption = opt;
                break;
            }
        }


        if (selectedOption != null && correctAnswer != null) {
            String answerTrimmed = correctAnswer.trim();
            String labelTrimmed = selectedOption.getLabel().trim();
            String textTrimmed = selectedOption.getText().trim();

            wasCorrect = answerTrimmed.equalsIgnoreCase(labelTrimmed) ||
                        answerTrimmed.equalsIgnoreCase(textTrimmed);
        } else {
            wasCorrect = false;
        }

        scoreChange = wasCorrect ? points : -points;
        p.changeScore(currentScore + scoreChange);
        question.markAnswered();
        applied = true;
    }

    @Override
    public void undo() {
        if (!applied) return;
        Player p = controller.getCurrentPlayer();
        if (p == null) {
            return;
        }
        int currentScore = p.getScore();
        p.changeScore(currentScore - scoreChange);
        question.reset();
        applied = false;
        wasCorrect = false;
        scoreChange = 0;
    }
}
