package org.example.Game;

import org.example.Logging.EventLogger;
import org.example.Logging.GameLogger;
import org.example.Logging.ReportGenerator;
import org.example.Question.JeopardyQuestion;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class JeopardyGame {
    private static int gameCounter = 0;
    
    private HashMap<Player,Integer> playerScoreMap;
    private int playerCount;
    private List<JeopardyQuestion> questions;
    private GameLogger gameLogger;
    private int currentTurnNumber;
    private JeopardyTurn currentJeopardyTurn;


    public JeopardyGame(List<JeopardyQuestion> questions){
        this.playerScoreMap = new HashMap<Player,Integer>();
        this.questions = questions;
        this.playerCount = 1;
        this.currentTurnNumber = 0;
        String caseID = "GAME" + String.format("%03d", gameCounter);
        this.gameLogger = new GameLogger(caseID);
        gameCounter++;
    }


    public void addPlayer(Player p){
        playerScoreMap.put(p,0);
    }
    public Player getPlayer(String p){
        for(Player player : this.playerScoreMap.keySet()){
            if(player.getName().equals(p)){
                return player;
            }
        }
        return null;
    }
    public List<Player> getAllPlayers() {
        return new ArrayList<>(playerScoreMap.keySet());
    }
    public int getPlayerScore(Player player) {
        return playerScoreMap.getOrDefault(player,0);
    }
    
    public void setPlayerCount(int playerCount){
        this.playerCount = playerCount;
    }

    public List<JeopardyQuestion> getQuestions() {
        return this.questions;
    }

    /**
     * Starts a new turn for the given player.
     */
    public JeopardyTurn startTurn(Player player) {
        currentTurnNumber++;
        currentJeopardyTurn = new JeopardyTurn(currentTurnNumber, player);
        gameLogger.addPlayer(player);
        return currentJeopardyTurn;
    }

    /**
     * Gets the current turn being played.
     */
    public JeopardyTurn getCurrentTurn() {
        return currentJeopardyTurn;
    }

    /**
     * Sets the category for the current turn.
     */
    public void setCurrentTurnCategory(String category) {
        if (currentJeopardyTurn != null) {
            currentJeopardyTurn.setCategory(category);
        }
    }

    /**
     * Sets the question for the current turn.
     */
    public void setCurrentTurnQuestion(JeopardyQuestion question) {
        if (currentJeopardyTurn != null) {
            currentJeopardyTurn.setQuestion(question);
        }
    }

    /**
     * Completes the current turn with answer information and logs it.
     */
    public void completeTurn(String selectedAnswer, String answerText, boolean isCorrect, int pointsAwarded) {
        if (currentJeopardyTurn != null) {
            currentJeopardyTurn.setAnswer(selectedAnswer, answerText, isCorrect, pointsAwarded);
            gameLogger.logEvent(currentJeopardyTurn);
            currentJeopardyTurn = null; // Clear current turn after logging
        }
    }

    /**
     * Gets the game logger for generating reports.
     */
    public GameLogger getGameLogger() {
        return gameLogger;
    }

    /**
     * Gets the event logger interface.
     * 
     * @return The EventLogger instance
     */
    public EventLogger getEventLogger() {
        return gameLogger;
    }

    public void generateReport(String filename) throws IOException {
        ReportGenerator generator = new ReportGenerator(gameLogger);
        generator.saveReport(filename);
    }

    public void generateEventLog(String filename) throws IOException {
        ReportGenerator generator = new ReportGenerator(gameLogger);
        generator.saveEventLog(filename);
    }
    public void updatePlayerScore(Player player, int newScore) {
        playerScoreMap.put(player, newScore);
    }

    public boolean areAllQuestionsAnswered() {
        if (questions == null || questions.isEmpty()) {
            return false;
        }
        for (JeopardyQuestion question : questions) {
            if (!question.isAnswered()) {
                return false;
            }
        }
        return true;
    }
}
