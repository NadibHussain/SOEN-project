package team14.warzone.GameEngine;

import org.junit.Before;
import org.junit.Test;
import team14.warzone.Console.Console;
import team14.warzone.MapModule.AdapterMapEditor;
import team14.warzone.MapModule.MapEditorConquest;

public class GameEngineTest {
    /**
     * console field
     */
    private Console d_Console;
    /**
     * map editor field
     */
    private AdapterMapEditor d_MapEditor;
    /**
     * game engine field
     */
    private GameEngine d_GE;

    /**
     * Method runs once before test case and initializes the context
     */
    @Before
    public void setup() {
        d_Console = new Console();
        d_MapEditor = new AdapterMapEditor(new MapEditorConquest());
        d_GE = new GameEngine(d_Console, d_MapEditor);
    }

    /**
     * Test that the game engine runs through all the phases
     */
    @Test
    public void phaseShiftTest() {
        org.junit.Assert.assertSame(d_GE.getD_PreMapLoadPhase(), d_GE.getD_CurrentPhase());
        d_GE.getD_CurrentPhase().next();
        org.junit.Assert.assertSame(d_GE.getD_CurrentPhase(), d_GE.getD_PostMapEditLoadPhase());
        d_GE.getD_CurrentPhase().loadMap("bigeurope.map");
        org.junit.Assert.assertSame(d_GE.getD_CurrentPhase(), d_GE.getD_StartupPhase());
        d_GE.getD_CurrentPhase().next();
        org.junit.Assert.assertSame(d_GE.getD_CurrentPhase(), d_GE.getD_IssueOrdersPhase());
        d_GE.getD_CurrentPhase().next();
        org.junit.Assert.assertSame(d_GE.getD_CurrentPhase(), d_GE.getD_ExecuteOrdersPhase());
    }
}