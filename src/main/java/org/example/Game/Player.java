package org.example.Game;

/**
 * Represents a player in a game, tracking their name and score.
*/

public class Player {
    
    /** The name of the player */
    private String name;

    /** The current score of the player */
    private int score;
    private JeopardyGame game;  

    public Player(String name) {

    /**
     * Constructs a Player with a given name and an initial score of 0.
     * 
     * @param name the name of the player
    */
        this.name = name;
        this.score = 0;
        this.game = null;
    }

    /**
     * Returns the name of the player.
     * 
     * @return the player's name
    */
    public String getName() {
        return name;
    }


    /**
     * Returns the current score of the player.
     * 
     * @return the player's score
    */
    public int getScore() {
        return score;
    }


    /**
     * Modifies the player's score by a given value.
     * Can increase or decrease the score.
     * 
     * @param value the amount to add to the score
    */
    public void changeScore(int value){
        this.score += value;
        if (game != null) {
            game.updatePlayerScore(this, this.score);
        }
    }
}