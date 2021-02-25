package team14.warzone.GameEngine;

import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.jupiter.api.DisplayName;
import team14.warzone.Console.Console;
import team14.warzone.MapModule.MapEditor;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Test reinforcement in GameEngine class
 */
public class GameEngineReinforcementTest {
    public static GameEngine d_GE; //game engine instance
    public static Console d_Console;//console used by game engine
    public static MapEditor d_MapEditor;//map editor used by game engine

    /**
     * A method to initialize variables to be used during test
     */
    @BeforeClass
    public static void initialize() {
        d_Console = new Console();
        d_MapEditor = new MapEditor();
        d_GE = new GameEngine(d_Console, d_MapEditor);
        d_GE.loadMap("bigeurope.map");
        d_GE.addPlayer("Player1");
        d_GE.addPlayer("Player2");
        d_GE.assignCountries();
    }

    /**
     * A method to check "reinforcement" number of armies given to each player at the beginning of each turn
     */
    @Test
    @DisplayName("Testing Reinforcement")
    public void testReinforcement() {
        //check if the player is given 20 armies after initialization
        assertEquals(20, d_GE.getD_PlayerList().get(0).getD_TotalNumberOfArmies());
        d_GE.reInforcement();
        //check if the reinforcement of the player equals to the expected value
        assertEquals(50, d_GE.getD_PlayerList().get(0).getD_TotalNumberOfArmies());
    }
}