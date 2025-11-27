package org.example.Logging;

import org.example.Game.Player;
import org.example.Game.JeopardyTurn;

import java.io.File;
import java.io.FileFilter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ReportGenerator {
    private EventLogger logger;

    public ReportGenerator(EventLogger logger) {
        this.logger = logger;
    }

    public String generateHumanReadableReport() {
        StringBuilder report = new StringBuilder();
        List<JeopardyTurn> turns = logger.getLoggedTurns();
        List<Player> players = logger.getLoggedPlayers();

        report.append("JEOPARDY PROGRAMMING GAME REPORT\n");
        report.append("================================\n\n");
        report.append("Case ID: ").append(logger.getCaseID()).append("\n\n");

        report.append("Players: ");
        if (!players.isEmpty()) {
            for (int i = 0; i < players.size(); i++) {
                if (i > 0) {
                    report.append(", ");
                }
                report.append(players.get(i).getName());
            }
        }
        report.append("\n\n");

        report.append("Gameplay Summary:\n");
        report.append("-----------------\n");

        int turnNum = 1;
        for (JeopardyTurn turn : turns) {
            if (turn.getQuestion() == null || turn.getPlayer() == null) {
                continue;
            }

            report.append("Turn ").append(turnNum).append(": ");
            report.append(turn.getPlayer().getName());
            report.append(" selected ").append(turn.getCategory());
            report.append(" for ").append(turn.getQuestion().getValue()).append(" pts\n");

            report.append("Question: ").append(turn.getQuestion().getPrompt()).append("\n");

            if (turn.getAnswerText() != null && !turn.getAnswerText().isEmpty()) {
                report.append("Answer: ").append(turn.getAnswerText());
            } else if (turn.getSelectedAnswer() != null) {
                report.append("Answer: ").append(turn.getSelectedAnswer());
            }

            if (turn.isCorrect()) {
                report.append(" — Correct (+").append(turn.getPointsAwarded()).append(" pts)\n");
            } else {
                report.append(" — Incorrect (-").append(Math.abs(turn.getPointsAwarded())).append(" pts)\n");
            }

            report.append("Score after turn: ").append(turn.getPlayer().getName())
                    .append(" = ").append(turn.getScoreAfterTurn()).append("\n\n");

            turnNum++;
        }

        report.append("Final Scores:\n");
        for (Player player : players) {
            int finalScore = player.getScore();
            report.append(player.getName()).append(": ").append(finalScore).append("\n");
        }

        return report.toString();
    }

    public String generateEventLog() {
        StringBuilder csv = new StringBuilder();

        csv.append("Case_ID,Player_ID,Activity,Timestamp,Category,Question_Answer_Given,Result,Score_After_Play\n");

        List<GameEvent> gameEvents = new ArrayList<>();
        if (logger instanceof GameLogger) {
            gameEvents = ((GameLogger) logger).getLoggedGameEvents();
        }

        List<JeopardyTurn> turns = logger.getLoggedTurns();

        for (GameEvent event : gameEvents) {
            csv.append(logger.getCaseID() != null ? logger.getCaseID() : "").append(",");
            csv.append(event.getPlayerID() != null ? event.getPlayerID() : "").append(",");
            csv.append(event.getActivity() != null ? event.getActivity() : "").append(",");
            csv.append(event.getTimestamp() != null ? event.getTimestamp().toString() : "").append(",");
            csv.append(event.getCategory() != null ? event.getCategory() : "").append(",");
            csv.append(event.getQuestionAnswerGiven() != null ? event.getQuestionAnswerGiven() : "").append(",");
            csv.append(event.getResult() != null ? event.getResult() : "N/A").append(",");
            csv.append(event.getScoreAfterPlay() != null ? event.getScoreAfterPlay().toString() : "").append("\n");
        }

        for (JeopardyTurn turn : turns) {
            if (turn.getPlayer() == null) {
                continue;
            }

            String activity = getActivityName(turn);
            if ("Answer Question".equals(activity)) {
                continue;
            }
            
            if ("Select Category".equals(activity)) {
                csv.append(logger.getCaseID() != null ? logger.getCaseID() : "").append(",");
                csv.append(turn.getPlayer().getName() != null ? turn.getPlayer().getName() : "").append(",");
                csv.append("Select Category").append(",");
                csv.append("").append(",");
                csv.append(turn.getCategory() != null ? turn.getCategory() : "").append(",");
                csv.append("").append(",");
                csv.append("N/A").append(",");
                csv.append(turn.getPlayer().getScore()).append("\n");
            } else if ("Select Question".equals(activity) && turn.getQuestion() != null) {
                csv.append(logger.getCaseID() != null ? logger.getCaseID() : "").append(",");
                csv.append(turn.getPlayer().getName() != null ? turn.getPlayer().getName() : "").append(",");
                csv.append("Select Question").append(",");
                csv.append("").append(",");
                csv.append(turn.getCategory() != null ? turn.getCategory() : "").append(",");
                csv.append(String.valueOf(turn.getQuestion().getValue())).append(",");
                csv.append("N/A").append(",");
                csv.append(turn.getPlayer().getScore()).append("\n");
            }
        }

        return csv.toString();
    }

    public void saveReport(String filename) throws IOException {
        String finalFilename = filename;
        if (!filename.endsWith(".txt")) {
            finalFilename = filename + ".txt";
        }
        
        File mostRecent = getMostRecentFile(finalFilename, ".txt");
        if (mostRecent != null) {
            finalFilename = mostRecent.getName();
        }
        
        try (FileWriter writer = new FileWriter(finalFilename)) {
            writer.write(generateHumanReadableReport());
        }
    }

    public void saveEventLog(String filename) throws IOException {
        String finalFilename = filename;
        if (!filename.endsWith(".csv")) {
            finalFilename = filename + ".csv";
        }
        
        File mostRecent = getMostRecentFile(finalFilename, ".csv");
        if (mostRecent != null) {
            finalFilename = mostRecent.getName();
        }
        
        try (FileWriter writer = new FileWriter(finalFilename)) {
            writer.write(generateEventLog());
        }
    }

    public File getMostRecentFile(String baseFilename, String extension) {
        String baseName;
        if (baseFilename.endsWith(extension)) {
            baseName = baseFilename.substring(0, baseFilename.length() - extension.length());
        } else {
            baseName = baseFilename;
        }
        
        final String finalBaseName = baseName;
        File directory = new File(".");
        FileFilter filter = new FileFilter() {
            @Override
            public boolean accept(File pathname) {
                String name = pathname.getName();
                return name.startsWith(finalBaseName) && name.endsWith(extension);
            }
        };
        File[] matchingFiles = directory.listFiles(filter);
        
        if (matchingFiles != null && matchingFiles.length > 0) {
            File mostRecent = matchingFiles[0];
            long mostRecentTime = mostRecent.lastModified();
            for (int i = 1; i < matchingFiles.length; i++) {
                long fileTime = matchingFiles[i].lastModified();
                if (fileTime > mostRecentTime) {
                    mostRecent = matchingFiles[i];
                    mostRecentTime = fileTime;
                }
            }
            return mostRecent;
        }
        
        return null;
    }

    private String getActivityName(JeopardyTurn turn) {
        if (turn.getQuestion() == null) {
            return "Select Category";
        }
        if (turn.getSelectedAnswer() == null && turn.getAnswerText() == null) {
            return "Select Question";
        }
        return "Answer Question";
    }

}
