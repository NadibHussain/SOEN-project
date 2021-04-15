package team14.warzone.GameEngine.Strategy;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import team14.warzone.Console.Console;
import team14.warzone.GameEngine.GameEngine;
import team14.warzone.MapModule.AdapterMapEditor;
import team14.warzone.MapModule.Country;
import team14.warzone.MapModule.MapEditorConquest;

/**
 * This class tests the aggressive player strategy
 */
public class AggressiveTest {
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
        d_GE.getD_CurrentPhase().addPlayer("p1", "aggressive");
        d_GE.getD_CurrentPhase().addPlayer("p2", "benevolent");
        d_GE.getD_CurrentPhase().assignCountries();
    }

    /**
     * Choose a country owned by p1, add armies to it then check if the aggressive player will deploy to this country,
     * since it will be the strongest country he owns
     * then check if number of armies in a country with enemy neighbours is doubled after cheater issue order is executed
     */
    @Test
    public void aggressiveTest() {
        Country l_CountryOwnedByPlayer = d_GE.getD_PlayerList().get(0).getD_CountriesOwned().get(1);
        l_CountryOwnedByPlayer.setD_NumberOfArmies(20);
        d_GE.getD_CurrentPhase().reinforce();
        d_GE.getD_PlayerList().get(0).issueOrder();
        d_GE.setD_CurrentPhase(d_GE.getD_ExecuteOrdersPhase());
        //check if number of armies in the strongest country increased by 50 (initial number of armies owned by player at game start)
        d_GE.getD_CurrentPhase().run();
        Assert.assertEquals(70, l_CountryOwnedByPlayer.getD_NumberOfArmies());
    }
}