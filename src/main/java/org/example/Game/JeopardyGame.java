package org.example.Game;

import org.example.Question.JeopardyQuestion;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Represents a Jeopardy game, including the questions and tracking scores for each player.
*/
public class JeopardyGame {

    /** Mapping from Player to their current score */
    private HashMap<Player,Integer> playerScoreMap;

    /** Number of players in the game */
    int playerCount;
    
    /** List of questions for the game */
    private List<JeopardyQuestion> questions;

    /**
     * Constructs a JeopardyGame with a predefined list of questions.
     * 
     * @param questions the List of JeopardyQuestion objects
    */
    public JeopardyGame(List<JeopardyQuestion> questions){
        this.playerScoreMap = new HashMap<Player,Integer>();
        this.questions = questions;
        this.playerCount = 1;
    }

    /**
     * Prints all questions to the console.
    */
    public void printQuestions(){
        for(JeopardyQuestion q : this.questions){
            System.out.println(q);
        }
    }

    /**
     * Adds a player to the game and initializes their score to 0.
     * 
     * @param p the Player to add
    */
    public void addPlayer(Player p){
        playerScoreMap.put(p,0);
    }
    public Player getPlayer(String name) {
        for (Player player : playerScoreMap.keySet()) {
            if (player.getName().equalsIgnoreCase(name)) {
                return player;
            }
        }
        return null;
    }

    /**
     * Returns all players in the game.
     * 
     * @return a List of Player objects
    */
    public List<Player> getAllPlayers() {
        return new ArrayList<>(playerScoreMap.keySet());
    }

    /**
     * Retrieves the current score for a player.
     * 
     * @param player the Player whose score is requested
     * @return the integer score; 0 if the player is not found
    */
    public int getPlayerScore(Player player) {
        return playerScoreMap.getOrDefault(player, 0);
    }

    public void updatePlayerScore(Player player, int newScore) {
        playerScoreMap.put(player, newScore);
    }
    
    /**
     * Sets the total number of players in the game.
     * 
     * @param playerCount the number of players
    */
    public void setPlayerCount(int playerCount){
        this.playerCount = playerCount;
    }

    /**
     * Returns the list of questions in the game.
     * 
     * @return a List of JeopardyQuestion objects
    */
    public List<JeopardyQuestion> getQuestions() {
        return this.questions;
    }

    
}
