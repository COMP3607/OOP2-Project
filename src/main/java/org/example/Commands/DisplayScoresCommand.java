package org.example.Commands;

import org.example.Game.JeopardyGame;
import org.example.Game.Player;

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
            
            game.getAllPlayers().stream()
                .sorted((p1, p2) -> Integer.compare(
                    game.getPlayerScore(p2), game.getPlayerScore(p1)))
                .forEach(player -> {
                    int score = game.getPlayerScore(player);
                    String scoreStr = score >= 0 ? "$" + score : "-$" + Math.abs(score);
                    System.out.printf("  %-20s %15s%n", player.getName() + ":", scoreStr);
                });
        }
        System.out.println("=".repeat(50) + "\n");
    }

    @Override
    public void undo(){ }
  }