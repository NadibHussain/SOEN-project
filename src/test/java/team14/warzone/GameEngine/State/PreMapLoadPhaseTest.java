package team14.warzone.GameEngine.State;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import team14.warzone.Console.Console;
import team14.warzone.GameEngine.GameEngine;
import team14.warzone.MapModule.AdapterMapEditor;
import team14.warzone.MapModule.MapEditorConquest;

/**
 * Class test the functionalities of the phase before a map has been loaded
 */
public class PreMapLoadPhaseTest {
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
     * Setup context for test case
     */
    @Before
    public void setUp() {
        d_Console = new Console();
        d_MapEditor = new AdapterMapEditor(new MapEditorConquest());
        d_GE = new GameEngine(d_Console, d_MapEditor);
    }

    /**
     * Test the editmap feature.
     * Check that editmap command loads a map into the map editor and moves to Post map load phase.
     */
    @Test
    public void editMapTest() {
        Assert.assertNull(d_GE.getD_MapEditor().getD_LoadedMap());
        d_GE.getD_CurrentPhase().editMap("bigeurope.map");
        Assert.assertNotNull(d_GE.getD_MapEditor().getD_LoadedMap());
        Assert.assertSame(d_GE.getD_PostMapEditLoadPhase(), d_GE.getD_CurrentPhase());
    }

    /**
     * Test the loadmap feature.
     * Check that loadmap command loads a map into the game engine and moves to startup phase.
     */
    @Test
    public void loadMapTest() {
        Assert.assertNull(d_GE.getD_LoadedMap());
        d_GE.getD_CurrentPhase().loadMap("bigeurope.map");
        Assert.assertNotNull(d_GE.getD_LoadedMap());
        Assert.assertSame(d_GE.getD_StartupPhase(), d_GE.getD_CurrentPhase());
    }
}