package team14.warzone.GameEngine;

import org.junit.Before;
import team14.warzone.Console.Console;
import team14.warzone.Console.InputValidator;
import team14.warzone.MapModule.MapEditor;

/**
 * Test the deploy command
 * Verify that a player cannot deploy more armies than what they have in their pool
 */
public class GameEngineTest {
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

//    /**
//     * Check "reinforcement" number of armies given to each player at the beginning of each turn
//     */
//    @Test
//    @DisplayName("Testing Armies Reinforcement")
//    public void testReinforcement() {
//        //check if the player is given 20 armies after initialization
//        assertEquals(20, d_GE.getD_PlayerList().get(0).getD_TotalNumberOfArmies());
//        d_GE.reInforcement();
//        //check if the reinforcement of the player equals to the expected value
//        assertEquals(50, d_GE.getD_PlayerList().get(0).getD_TotalNumberOfArmies());
//    }

}