
package org.example.Game;

import org.example.Question.JeopardyQuestion;
import org.example.Question.Option;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class JeopardyGameTest {

    private JeopardyGame game;
    private Player josh;
    private Player emma;

    @BeforeEach
    public void setUp() {
        // Create sample questions in-memory (no file needed)
        List<Option> options = List.of(
            new Option("A", "Red"),
            new Option("B", "Blue"),
            new Option("C", "Green"),
            new Option("D", "Yellow")
        );

        List<JeopardyQuestion> questions = new ArrayList<>();
        questions.add(new JeopardyQuestion("Blue", "Color of the sky", "Science", 200, options));
        questions.add(new JeopardyQuestion("Paris", "Capital of France", "Geography", 400, options));

        game = new JeopardyGame(questions);

        josh = new Player("Josh");
        emma = new Player("Emma");

        game.addPlayer(josh);
        game.addPlayer(emma);
    }

    @Test
    public void testPlayerCreation() {
        Player p = new Player("Alex");
        assertEquals("Alex", p.getName());
        assertEquals(0, p.getScore());
    }

    @Test
    public void testAddPlayer_AppearsInAllPlayersAndScoreMap() {
        Player newPlayer = new Player("Trey");
        game.addPlayer(newPlayer);

        assertTrue(game.getAllPlayers().contains(newPlayer));
        assertEquals(0, game.getPlayerScore(newPlayer));
        assertEquals(3, game.getAllPlayers().size());
    }

    @Test
    public void testGetPlayer_ByName_IsCaseInsensitiveAndReturnsCorrectPlayer() {
        assertEquals(josh, game.getPlayer("Josh"));
        assertEquals(josh, game.getPlayer("josh"));
        assertEquals(josh, game.getPlayer("JOSH"));

        assertEquals(emma, game.getPlayer("Emma"));
        assertNull(game.getPlayer("Unknown"));
    }
/*
    @Test
    public void testGetPlayerScore_UsesGetOrDefault_ReturnsZeroForNonExistent() {
        Player stranger = new Player("Stranger");
        assertEquals(0, game.getPlayerScore(stranger)); // Not added → defaults to 0

        game.addPlayer(stranger);
        assertEquals(0, game.getPlayerScore(stranger));

        josh.changeScore(1000);
        assertEquals(1000, game.getPlayerScore(josh));
    }
*/
    @Test
    public void testGetAllPlayers_ReturnsDefensiveCopy() {
        List<Player> list = game.getAllPlayers();
        assertEquals(2, list.size());

        list.clear(); // Should NOT affect internal map
        assertEquals(2, game.getAllPlayers().size());
    }

    @Test
    public void testQuestionsAreLoadedAndAccessible() {
        assertEquals(2, game.getQuestions().size());

        JeopardyQuestion q = game.getQuestions().get(0);
        assertEquals("Science", q.getCategory());
        assertEquals("Color of the sky", q.getPrompt());
        assertEquals(200, q.getValue());
        assertEquals(4, q.getOptions().size());
        assertFalse(q.isAnswered());
    }

    @Test
    public void testPrintQuestions_RunsWithoutError() {
        assertDoesNotThrow(() -> game.printQuestions());
    }

    @Test
    public void testSetPlayerCount_UpdatesFieldCorrectly() {
        game.setPlayerCount(5);
        assertEquals(5, game.playerCount);
    }
/*
    @Test
    public void testScoreSync_PlayerChangeScore_UpdatesMapViaGetPlayerScore() {
        josh.changeScore(800);
        assertEquals(800, josh.getScore());
        assertEquals(800, game.getPlayerScore(josh));

        emma.changeScore(-400);
        assertEquals(-400, game.getPlayerScore(emma));
    }
*/
    @Test
    public void testGameStartsWithDefaultPlayerCount() {
        JeopardyGame newGame = new JeopardyGame(new ArrayList<>());
        assertEquals(1, newGame.playerCount); // default
    }
}
