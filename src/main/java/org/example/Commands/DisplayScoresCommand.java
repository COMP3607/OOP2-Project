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
            for (Player player : players) {
                int score = player.getScore();
                String scoreStr = score >= 0 ? "$" + score : "-$" + Math.abs(score);
                System.out.printf("  %-20s %15s%n", player.getName() + ":", scoreStr);
            }
        }
        System.out.println("=".repeat(50) + "\n");
    }

    @Override
    public void undo(){ }
  }