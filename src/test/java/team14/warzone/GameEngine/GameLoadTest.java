package team14.warzone.GameEngine;

import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.DisplayName;
import team14.warzone.Console.Console;
import team14.warzone.Console.InputValidator;
import team14.warzone.MapModule.AdapterMapEditor;
import team14.warzone.MapModule.MapEditorConquest;

import static org.junit.Assert.assertEquals;

/**
 * This is a test class for Load Game feature
 */
public class GameLoadTest {
    /**
     * Game Engine Field
     */
    private GameEngine d_GE;
    /**
     * Map Editor Field
     */
    private AdapterMapEditor d_MapEditor;
    /**
     * Console Field
     */
    private Console d_Console;
    /**
     * GameSaveLoad Field
     */
    private GameSaveLoad d_GameSaveLoad;

    /**
     * Method initializes the context under which test should run
     * Load a map, add players, assigncountries, deploy armies to countries and save the game
     * at this point
     */
    @Before
    public void setup() {
        d_MapEditor = new AdapterMapEditor(new MapEditorConquest());
        d_Console = new Console();
        d_GE = new GameEngine(d_Console, d_MapEditor);
        d_GE.getD_CurrentPhase().loadMap("bigeurope.map");
        InputValidator.CURRENT_PHASE = InputValidator.Phase.STARTUP;
        d_GE.getD_CurrentPhase().addPlayer("p1", "human");
        d_GE.getD_CurrentPhase().addPlayer("p2", "human");
        d_GE.getD_CurrentPhase().assignCountries();
        InputValidator.CURRENT_PHASE = InputValidator.Phase.GAMEPLAY;
        d_GE.setD_CurrentPlayer(d_GE.getD_PlayerList().get(0));
        d_GE.getD_LoadedMap().findCountry("s1").setD_NumberOfArmies(50);
        d_GE.setD_CurrentPlayer(d_GE.getD_PlayerList().get(1));
        d_GE.getD_LoadedMap().findCountry("s2").setD_NumberOfArmies(50);
        Player l_P1 = d_GE.getD_PlayerList().get(0);
        d_GE.setD_CurrentPlayer(l_P1);
        d_GameSaveLoad = new GameSaveLoad(d_GE);
        d_GameSaveLoad.runSaveGame("TestLoad");
    }

    /**
     * The test loads the saved file and checks for various parameters which were setup earlier
     * checks the players names added, country owned by a player, number of armies on a country after deploy
     */
    @Test
    @DisplayName("Testing Load Game")
    public void loadTest() {
        GameEngine d_TestGE;
        d_GameSaveLoad.runLoadGame("TestLoad");
        d_TestGE = d_GameSaveLoad.getD_loadGameEngine();
        assertEquals("p1", d_TestGE.getD_PlayerList().get(0).getD_Name());
        assertEquals("p2", d_TestGE.getD_PlayerList().get(1).getD_Name());
        assertEquals("p1", d_TestGE.getD_PlayerList().get(0).getD_CountriesOwned().get(0).getD_CurrentOwner());
        assertEquals("p1", d_TestGE.getD_CurrentPlayer().getD_Name());
        assertEquals(50, d_TestGE.getD_LoadedMap().getD_Countries().get(0).getD_NumberOfArmies());
    }

}
