package org.example.Game;

import org.example.Question.JeopardyQuestion;

import java.time.LocalDateTime;

/**
 * Represents a single turn in the Jeopardy game.
 * Tracks all information needed for reporting and process mining.
 */
public class JeopardyTurn {
    private int turnNumber;
    private Player player;
    private String category;
    private JeopardyQuestion question;
    private String selectedAnswer; // The answer option selected (e.g., "A", "B", "C", "D")
    private String answerText; // The full text of the selected answer
    private boolean isCorrect;
    private int pointsAwarded;
    private int scoreBeforeTurn;
    private int scoreAfterTurn;
    private LocalDateTime timestamp;

    public JeopardyTurn(int turnNumber, Player player) {
        this.turnNumber = turnNumber;
        this.player = player;
        this.timestamp = LocalDateTime.now();
        this.scoreBeforeTurn = player != null ? player.getScore() : 0;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public void setQuestion(JeopardyQuestion question) {
        this.question = question;
    }

    public void setAnswer(String selectedAnswer, String answerText, boolean isCorrect, int pointsAwarded) {
        this.selectedAnswer = selectedAnswer;
        this.answerText = answerText;
        this.isCorrect = isCorrect;
        this.pointsAwarded = pointsAwarded;
        this.scoreAfterTurn = player != null ? player.getScore() : 0;
    }

    // Getters
    public Player getPlayer() { return player; }
    public String getCategory() { return category; }
    public JeopardyQuestion getQuestion() { return question; }
    public String getSelectedAnswer() { return selectedAnswer; }
    public String getAnswerText() { return answerText; }
    public boolean isCorrect() { return isCorrect; }
    public int getPointsAwarded() { return pointsAwarded; }
    public int getScoreAfterTurn() { return scoreAfterTurn; }

}

