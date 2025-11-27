package org.example.Logging;

import java.time.LocalDateTime;

public class GameEvent {
    private String activity;
    private String playerID;
    private LocalDateTime timestamp;
    private String category;
    private String questionAnswerGiven;
    private String result;
    private Integer scoreAfterPlay;

    public GameEvent(String activity, String playerID, LocalDateTime timestamp) {
        this.activity = activity;
        this.playerID = playerID;
        this.timestamp = timestamp;
        this.category = "";
        this.questionAnswerGiven = "";
        this.result = "N/A";
        this.scoreAfterPlay = null;
    }

    public GameEvent(String activity, String playerID, LocalDateTime timestamp, String questionAnswerGiven, String result) {
        this.activity = activity;
        this.playerID = playerID;
        this.timestamp = timestamp;
        this.category = "";
        this.questionAnswerGiven = questionAnswerGiven != null ? questionAnswerGiven : "";
        this.result = result != null ? result : "N/A";
        this.scoreAfterPlay = null;
    }

    public String getActivity() {
        return activity;
    }

    public String getPlayerID() {
        return playerID;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public String getCategory() {
        return category != null ? category : "";
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getQuestionAnswerGiven() {
        return questionAnswerGiven != null ? questionAnswerGiven : "";
    }

    public void setQuestionAnswerGiven(String questionAnswerGiven) {
        this.questionAnswerGiven = questionAnswerGiven;
    }

    public String getResult() {
        return result != null ? result : "N/A";
    }

    public void setResult(String result) {
        this.result = result;
    }

    public Integer getScoreAfterPlay() {
        return scoreAfterPlay;
    }

    public void setScoreAfterPlay(Integer scoreAfterPlay) {
        this.scoreAfterPlay = scoreAfterPlay;
    }
}

