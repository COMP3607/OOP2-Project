package org.example.Game;
public class Player {
    private String name;
    private int score;
    private JeopardyGame game;  

    public Player(String name) {
        this.name = name;
        this.score = 0;
        this.game = null;
    }

    public String getName() { return name; }
    public int getScore() { return score; }

    public void changeScore(int value) {
        this.score += value;
        if (game != null) {
            game.updatePlayerScore(this, this.score);
        }
    }
}