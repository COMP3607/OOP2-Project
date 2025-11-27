
import org.example.FileImportTemplates.JSONImporter;
import org.example.Game.JeopardyGame;
import org.example.Game.Player;
import org.example.Question.JeopardyQuestion;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
public class JeopardyGameTest {
    private JeopardyGame game;
    private JSONImporter importer;
    private Player p;
    @BeforeEach
    public void setUp() {
         importer = new JSONImporter();
         game = new JeopardyGame(importer.Questions("sample_game_JSON.json"));
         p = new Player("Josh");
    }
    @Test
    public void testCreatePlayer(){
        assertEquals("Josh", p.getName());
        assertEquals(0,p.getScore());
    }

    public void testAddPlayer(){
        game.addPlayer(p);
        assertEquals(game.getPlayer("Josh"), p);
    }

}
