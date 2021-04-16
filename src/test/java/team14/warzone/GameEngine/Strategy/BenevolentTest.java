package team14.warzone.GameEngine.Strategy;

import org.junit.Before;
import org.junit.Test;
import team14.warzone.Console.Console;
import team14.warzone.GameEngine.GameEngine;
import team14.warzone.GameEngine.Player;
import team14.warzone.MapModule.AdapterMapEditor;
import team14.warzone.MapModule.MapEditorConquest;

public class BenevolentTest {
    private Benevolent d_BP;
    private Player d_Player;
    private GameEngine d_GE;
    private Console d_Console;
    private AdapterMapEditor d_MapEditor;

    
    /** 
     * @throws Exception
     */
    @Before
    public void setUp() throws Exception {
        // object instantiation
        d_Console = new Console();
        d_MapEditor = new AdapterMapEditor(new MapEditorConquest());
        d_GE = new GameEngine(d_Console, d_MapEditor);
        d_Player = new Player("p1", "human", d_GE);
        d_BP = new Benevolent();
    }

    @Test
    public void issueOrder() {

    }
}