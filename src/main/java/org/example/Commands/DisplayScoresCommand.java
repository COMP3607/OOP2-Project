package org.example.Commands;

import org.example.Game.JeopardyGame;
import org.example.Game.Player;

import java.util.ArrayList;
import java.util.List;

public class DisplayScoresCommand implements Command {
    private final JeopardyGame game;

    public DisplayScoresCommand(JeopardyGame game) {
        this.game = game;
    }

    @Override
    public void execute() {
        System.out.println("\n" + "=".repeat(50));
        System.out.println("             CURRENT SCORES");
        System.out.println("=".repeat(50));

        if (game.getAllPlayers().isEmpty()) {
            System.out.println("       No players in the game yet.");
        } else {
            List<Player> players = new ArrayList<>(game.getAllPlayers());
            for (int i = 0; i < players.size() - 1; i++) {
                for (int j = i + 1; j < players.size(); j++) {
                    int scoreI = game.getPlayerScore(players.get(i));
                    int scoreJ = game.getPlayerScore(players.get(j));
                    if (scoreJ > scoreI) {
                        Player temp = players.get(i);
                        players.set(i, players.get(j));
                        players.set(j, temp);
                    }
                }
            }
            
            for (Player player : players) {
                int score = game.getPlayerScore(player);
                String scoreStr = score >= 0 ? "$" + score : "-$" + Math.abs(score);
                System.out.printf("  %-20s %15s%n", player.getName() + ":", scoreStr);
            }
        }
        System.out.println("=".repeat(50) + "\n");
    }

    @Override
    public void undo(){ }
  }