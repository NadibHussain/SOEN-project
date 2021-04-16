package team14.warzone.GameEngine.State;

import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.DisplayName;
import team14.warzone.Console.Console;
import team14.warzone.Console.InputValidator;
import team14.warzone.GameEngine.GameEngine;
import team14.warzone.GameEngine.Player;
import team14.warzone.MapModule.AdapterMapEditor;
import team14.warzone.MapModule.MapEditorConquest;

import static org.junit.Assert.assertEquals;

/**
 * This class tests reinforcement in IssueOrderPhase class
 */
public class IssueOrdersPhaseTest {
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
     * issue order field
     */
    IssueOrdersPhase d_IssueOrderPh;

    /**
     * Method initializes the context under which test should run
     * Loads a map, adds two players, sets the turn to p1 and changes phase to gameplay
     */
    @Before
    public void setup() {
        // object instantiation
        d_Console = new Console();
        d_MapEditor = new AdapterMapEditor(new MapEditorConquest());
        d_GE = new GameEngine(d_Console, d_MapEditor);
        d_IssueOrderPh = new IssueOrdersPhase(d_GE);

        // setting up the scenario
        d_GE.getD_CurrentPhase().loadMap("bigeurope.map");
        InputValidator.CURRENT_PHASE = InputValidator.Phase.STARTUP;
        d_GE.getD_CurrentPhase().addPlayer("p1", "human");
        d_GE.getD_CurrentPhase().addPlayer("p2", "human");
        d_GE.getD_CurrentPhase().assignCountries();
        InputValidator.CURRENT_PHASE = InputValidator.Phase.GAMEPLAY;
        d_GE.setD_CurrentPlayer(d_GE.getD_PlayerList().get(0)); // p1 turn
    }

    /**
     * Check "reinforcement" number of armies given to each player at the beginning of each turn
     */
    @Test
    @DisplayName("Testing Armies Reinforcement")
    public void testReinforcement() {
        //check if the player is given 20 armies after initialization
        assertEquals(20, d_GE.getD_PlayerList().get(0).getD_TotalNumberOfArmies());
        d_IssueOrderPh.reinforce();
        //check if the reinforcement of the player equals to the expected value (20_previously owned + 30_reinforcement)
        assertEquals(50, d_GE.getD_PlayerList().get(0).getD_TotalNumberOfArmies());
        //assign por2 and por4 countries to p1, so he will own Portugal continent
        d_GE.getD_PlayerList().get(1).removeCountryOwned(d_GE.getD_LoadedMap().findCountry("por2"));
        d_GE.getD_PlayerList().get(1).removeCountryOwned(d_GE.getD_LoadedMap().findCountry("por4"));
        d_GE.getD_PlayerList().get(0).addCountryOwned(d_GE.getD_LoadedMap().findCountry("por2"));
        d_GE.getD_PlayerList().get(0).addCountryOwned(d_GE.getD_LoadedMap().findCountry("por4"));
        for (Player l_Player : d_GE.getD_PlayerList()) {
            l_Player.resetReceivedReinforcement();
        }
        d_IssueOrderPh.reinforce();
        //check if the reinforcement of the player should equal (50_previously owned + 30_reinforcement + 5_Control
        // value of Portugal)
        assertEquals(85, d_GE.getD_PlayerList().get(0).getD_TotalNumberOfArmies());
    }

}