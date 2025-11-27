package org.example.Commands;

import org.example.Game.GameController;
import org.example.Game.Player;
import org.example.Question.JeopardyQuestion;
import org.example.Question.Option;

/**
 * A command that processes a player's multiple-choice answer for a
 * {@link JeopardyQuestion}. 
 * 
 * <p>The command determines whether the selected option is correct, updates
 * the player's score, and marks the question as answered. Undoing the
 * command restores the previous score and resets the question state.</p>
 * 
 * <p>This command is single-use — it cannot be executed more than once
 * or executed on a question that is already marked as answered.</p>
 */
public class AnswerQuestionCommand implements Command {

    /** Controller that manages overall game flow and player state. */
    private final GameController controller;

    /** The question being answered. */
    private final JeopardyQuestion question;

    /** The number of points associated with the question. */
    private final int points;

    /** The label of the option chosen by the player (e.g., "A", "B", "C", "D"). */
    private final String userChoice;

    /** Tracks whether the command has already been applied. */
    private boolean applied = false;

    /** Whether the player's answer was correct during execution. */
    private boolean wasCorrect = false;

    /** How much the player's score changed (positive or negative). */
    private int scoreChange = 0;

    /**
     * Creates a new AnswerQuestionCommand.
     *
     * @param controller the active game controller
     * @param question the question being answered
     * @param choice the option label selected by the user (e.g., "A")
     */
    public AnswerQuestionCommand(GameController controller,
                                 JeopardyQuestion question, String choice) {
        this.controller = controller;
        this.question = question;
        this.points = question.getValue();
        this.userChoice = choice;
    }

    /**
     * Executes the command by validating the chosen option, determining
     * correctness, updating the player's score, and marking the question
     * as answered.
     * <p>
     * If the command has already been executed or the question was
     * previously answered, the operation is ignored.
     *
     * @throws IllegalStateException if no current player exists
     */
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

        // Find the option corresponding to the user's choice
        Option selectedOption = null;
        for (Option opt : question.getOptions()) {
            if (opt.getLabel().equalsIgnoreCase(userChoice != null ? userChoice.trim() : "")) {
                selectedOption = opt;
                break;
            }
        }

        // Determine correctness by comparing labels OR full text
        if (selectedOption != null && correctAnswer != null) {
            String answerTrimmed = correctAnswer.trim();
            String labelTrimmed = selectedOption.getLabel().trim();
            String textTrimmed = selectedOption.getText().trim();

            wasCorrect = answerTrimmed.equalsIgnoreCase(labelTrimmed)|| 
                        answerTrimmed.equalsIgnoreCase(textTrimmed);
        } else {
            wasCorrect = false;
        }

        scoreChange = wasCorrect ? points : -points;
        p.changeScore(currentScore + scoreChange);
        question.markAnswered();
        applied = true;
    }

    /**
     * Undoes the command by restoring the player's previous score and
     * resetting the question to its unanswered state.
     * <p>
     * If the command has not been applied, this method does nothing.
     */
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
