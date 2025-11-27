package org.example.Logging;

import org.example.Game.Player;
import org.example.Question.JeopardyQuestion;

import java.time.LocalDateTime;

/**
 * Represents a single event recorded in the Jeopardy game activity log.
 * 
 * <p>Each log entry captures information about a player's action or a system
 * activity, including timestamps, question details, results, and score changes.
 * Entries can be exported to CSV format for persistent storage or analytics.</p>
 */
public class EventLogEntry {

    /** Unique ID representing the case or event instance. */
    private String caseID;

    /** ID of the player performing the activity (or "System" for non-player actions). */
    private String playerID;

    /** Description of the activity (e.g., "Answered Question", "Game Started"). */
    private String activity;

    /** Time the activity occurred. */
    private LocalDateTime timestamp;

    /** Category of the question involved, if applicable. */
    private String category;

    /** Dollar value of the question involved, if applicable. */
    private String questionValue;

    /** The answer the player submitted, if applicable. */
    private String answerGiven;

    /** The result of the player's action (e.g., "Correct", "Incorrect"). */
    private String result;

    /** The player's score immediately after the activity. */
    private Integer scoreAfterPlay;

    /**
     * Creates a fully populated log entry.
     *
     * @param caseID         unique case or event ID
     * @param playerID       ID of the player or "System"
     * @param activity       description of the activity performed
     * @param timestamp      time the activity occurred
     * @param category       question category (may be null)
     * @param questionValue  question value as text (may be null)
     * @param answerGiven    the answer the player submitted (may be null)
     * @param result         outcome of the attempt (may be null)
     * @param scoreAfterPlay the player’s score after the activity
     */
    public EventLogEntry(String caseID, String playerID, String activity,
                         LocalDateTime timestamp, String category, String questionValue,
                         String answerGiven, String result, Integer scoreAfterPlay) {
        this.caseID = caseID;
        this.playerID = playerID;
        this.activity = activity;
        this.timestamp = timestamp;
        this.category = category;
        this.questionValue = questionValue;
        this.answerGiven = answerGiven;
        this.result = result;
        this.scoreAfterPlay = scoreAfterPlay;
    }

    /** @return the unique case ID */
    public String getCaseID() { return caseID; }

    /** @return the ID of the player involved in this activity */
    public String getPlayerID() { return playerID; }

    /** @return the description of the logged activity */
    public String getActivity() { return activity; }

    /** @return the timestamp of the event */
    public LocalDateTime getTimestamp() { return timestamp; }

    /** @return the category of the related question (if applicable) */
    public String getCategory() { return category; }

    /** @return the value of the related question (if applicable) */
    public String getQuestionValue() { return questionValue; }

    /** @return the answer submitted by the player */
    public String getAnswerGiven() { return answerGiven; }

    /** @return the result of the activity (“Correct”, “Incorrect”, etc.) */
    public String getResult() { return result; }

    /** @return the player's score after completing the action */
    public Integer getScoreAfterPlay() { return scoreAfterPlay; }

    /**
     * Converts this log entry into a CSV-formatted line.
     * Null fields are replaced with empty strings or default values.
     *
     * @return a CSV-formatted string representing this entry
     */
    public String toCSV() {
        return String.join(",",
                caseID != null ? caseID : "",
                playerID != null ? playerID : "System",
                activity,
                timestamp.toString(),
                category != null ? category : "",
                questionValue != null ? questionValue : "",
                answerGiven != null ? answerGiven : "",
                result != null ? result : "",
                scoreAfterPlay != null ? scoreAfterPlay.toString() : ""
        );
    }

    /**
     * Creates a simplified log entry with only an activity description.
     * The timestamp is automatically set to the current time.
     *
     * @param activity description of the activity performed
     */
    public EventLogEntry(String activity) {
        this.activity = activity;
        this.timestamp = LocalDateTime.now();
    }
}