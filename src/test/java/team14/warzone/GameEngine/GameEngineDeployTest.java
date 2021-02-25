package team14.warzone.GameEngine;

import org.junit.Before;
import org.junit.Test;
import team14.warzone.Console.Console;
import team14.warzone.Console.InputValidator;
import team14.warzone.MapModule.MapEditor;

/**
 * Test the deploy command
 * Verify that a player cannot deploy more armies than what they have in their pool
 */
public class GameEngineDeployTest {
    /**
     * console field
     */
    private Console d_Console;
    /**
     * map editor field
     */
    private MapEditor d_MapEditor;
    /**
     * game engine field
     */
    private GameEngine d_GE;

    /**
     * Method initializes the context under which test should run
     * Loads a map, adds two players, sets the turn to p1 and changes phase to gameplay
     */
    @Before
    public void setup() {
        // object instantiation
        d_Console = new Console();
        d_MapEditor = new MapEditor();
        d_GE = new GameEngine(d_Console, d_MapEditor);

        // setting up the scenario
        d_GE.loadMap("bigeurope.map");
        InputValidator.CURRENT_PHASE = InputValidator.Phase.STARTUP;
        d_GE.addPlayer("p1");
        d_GE.addPlayer("p2");
        d_GE.assignCountries();
        InputValidator.CURRENT_PHASE = InputValidator.Phase.GAMEPLAY;
        d_GE.setD_CurrentPlayer(d_GE.getD_PlayerList().get(0)); // p1 turn
    }

    /**
     * Tries to deploy more armies than currently in possession of p1
     * p1 has 20 armies, but tries to deploy 100
     * Deploy should fail and the country should contain 0 armies
     */
    @Test
    public void deployTest() {
        try {
            d_GE.deploy("s1", 100);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        int l_ArmiesInCountry = d_GE.getD_LoadedMap().findCountry("s1").getD_NumberOfArmies();
        org.junit.Assert.assertEquals("Armies deployed - deployed > possession", 0, l_ArmiesInCountry);
    }
}