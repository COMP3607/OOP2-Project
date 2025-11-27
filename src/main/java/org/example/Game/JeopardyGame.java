package org.example.Game;

import org.example.Question.JeopardyQuestion;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class JeopardyGame {
    private HashMap<Player,Integer> playerScoreMap;
    int playerCount;
    private List<JeopardyQuestion> questions;


    public JeopardyGame(List<JeopardyQuestion> questions){
        this.playerScoreMap = new HashMap<Player,Integer>();
        this.questions = questions;
        this.playerCount = 1;
    }

    public void printQuestions(){
        for(JeopardyQuestion q : this.questions){
            System.out.println(q);
        }
    }

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
    public List<Player> getAllPlayers() {
        return new ArrayList<>(playerScoreMap.keySet());
    }
    public int getPlayerScore(Player player) {
        return playerScoreMap.getOrDefault(player, 0);
    }
    
    public void setPlayerCount(int playerCount){
        this.playerCount = playerCount;
    }

    public List<JeopardyQuestion> getQuestions() {
        return this.questions;
    }
}
