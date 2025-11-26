package org.example.Logging;

import org.example.Game.Player;
import org.example.Question.JeopardyQuestion;

import java.time.LocalDateTime;

public class EventLogEntry {
        private  String caseID;
        private  String playerID;
        private  String activity;
        private LocalDateTime timestamp;
        private  String category;
        private  String questionValue;
        private  String answerGiven;
        private  String result;
        private  Integer scoreAfterPlay;

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

        public String getCaseID() { return caseID; }
        public String getPlayerID() { return playerID; }
        public String getActivity() { return activity; }
        public LocalDateTime getTimestamp() { return timestamp; }
        public String getCategory() { return category; }
        public String getQuestionValue() { return questionValue; }
        public String getAnswerGiven() { return answerGiven; }
        public String getResult() { return result; }
        public Integer getScoreAfterPlay() { return scoreAfterPlay; }

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


    public EventLogEntry(String activity){
        this.activity = activity;
        this.timestamp = LocalDateTime.now();
    }


}
