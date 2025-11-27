package org.example.Commands;

import org.example.Game.JeopardyGame;
import org.example.Game.Player;

/**
 * Command that displays all player scores currently stored in a {@link JeopardyGame}.
 * 
 * <p>The scores are shown from highest to lowest value and formatted to resemble
 * traditional Jeopardy-style currency formatting. Negative scores are displayed
 * using a prefixed minus sign (e.g., -$200).</p>
 * 
 * <p>This command is view-only and does not modify game state; therefore, {@link #undo()}
 * performs no action.</p>
 */
public class DisplayScoresCommand implements Command {

    /** The game instance whose player scores will be displayed. */
    private final JeopardyGame game;

    /**
     * Creates a new DisplayScoresCommand.
     *
     * @param game the Jeopardy game containing players and scores
     */
    public DisplayScoresCommand(JeopardyGame game) {
        this.game = game;
    }

    /**
     * Prints a formatted leaderboard of all players and their current scores.
     * 
     * - Displays a header section<br>
     * - Sorts players by descending score<br>
     * - Formats scores as Jeopardy currency values<br>
     * - Prints a message if no players are present<br>
     */
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

    /**
     * No action is taken because displaying scores does not alter the program state.
     */
    @Override
    public void undo(){ }
  }