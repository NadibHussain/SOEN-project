package team14.warzone.GameEngine.State;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import team14.warzone.Console.Console;
import team14.warzone.GameEngine.GameEngine;
import team14.warzone.MapModule.AdapterMapEditor;
import team14.warzone.MapModule.MapEditorConquest;

public class GameOverPhaseTest {
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
    public void init() {
        d_Console = new Console();
        d_MapEditor = new AdapterMapEditor(new MapEditorConquest());
        d_GE = new GameEngine(d_Console, d_MapEditor);
        // map editor phase
        d_GE.getD_CurrentPhase().loadMap("bigeurope.map");
        // startup phase
        d_GE.getD_CurrentPhase().addPlayer("p1", "human");
        d_GE.getD_CurrentPhase().addPlayer("p2", "human");
        d_GE.getD_CurrentPhase().assignCountries();
        // assign all countries to p1
        d_GE.getD_PlayerList().get(0).setD_CountriesOwned(d_GE.getD_LoadedMap().getD_Countries());
        // issue order to execute phase
        d_GE.getD_CurrentPhase().next();
    }

    /**
     * Runs gameloop after all countries have been owned by single player. Game engine should move to game-over-phase.
     */
    @Test
    public void gameOverTest() {
        d_GE.getD_CurrentPhase().run();
        // phase should be set to Game Over Phase
        Assert.assertSame(d_GE.getD_GameOverPhase(), d_GE.getD_CurrentPhase());
    }
}