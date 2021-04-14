package team14.warzone.GameEngine.Commands;

import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.DisplayName;
import team14.warzone.Console.Console;
import team14.warzone.Console.InputValidator;
import team14.warzone.GameEngine.GameEngine;
import team14.warzone.MapModule.AdapterMapEditor;
import team14.warzone.MapModule.MapEditorConquest;

/**
 * This class tests the deploy order
 */
public class DeployTest {
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
     * Method initializes the context under which test should run
     * Loads a map, adds two players, sets the turn to p1 and changes phase to gameplay
     */
    @Before
    public void setup() {
        // object instantiation
        d_Console = new Console();
        d_MapEditor = new AdapterMapEditor(new MapEditorConquest());
        d_GE = new GameEngine(d_Console, d_MapEditor);

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
     * Tries to deploy more armies than currently in possession of p1
     * p1 has 20 armies, but tries to deploy 100
     * Deploy should fail and the country should contain 0 armies
     * Then try to deploy 20 armies in a country
     *  Deploy should succeed, because p1 has enough armies
     */
    @Test
    @DisplayName("Testing Armies deployment")
    public void deployTest() {
        try {
            Deploy l_Dep = new Deploy("s1", 100, d_GE);
            l_Dep.execute();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        int l_ArmiesInCountry = d_GE.getD_LoadedMap().findCountry("s1").getD_NumberOfArmies();
        org.junit.Assert.assertEquals("Armies deployed - deployed > possession", 0, l_ArmiesInCountry);

        try {
            Deploy l_Dep2 = new Deploy("s3", 20, d_GE);
            l_Dep2.execute();
        } catch (Exception e){
            System.out.println(e.getMessage());
        }
        int l_ArmiesInCountry2 = d_GE.getD_LoadedMap().findCountry("s3").getD_NumberOfArmies();
        org.junit.Assert.assertEquals("Armies deployed - deployed > possession", 20, l_ArmiesInCountry2);
    }

}