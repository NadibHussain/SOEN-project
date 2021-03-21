package team14.warzone.GameEngine.Commands;

import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.DisplayName;
import team14.warzone.Console.Console;
import team14.warzone.Console.InputValidator;
import team14.warzone.GameEngine.GameEngine;
import team14.warzone.MapModule.MapEditor;

public class AdvanceTest {

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
        d_GE.getD_LoadedMap().findCountry("b6").setD_NumberOfArmies(15);
        d_GE.getD_LoadedMap().findCountry("b7").setD_NumberOfArmies(6);
    }

     /**
     * Tries to deploy more armies than currently in possession of p1
     * p1 has 20 armies, but tries to deploy 100
     * Deploy should fail and the country should contain 0 armies
     */
    @Test
    @DisplayName("Testing advance Armies")
    public void executeTest() {
        int l_ArmiesSrcCountryBefore = 0;
        int l_ArmiesDestCountryBefore = 0;
        try {
            l_ArmiesSrcCountryBefore = d_GE.getD_LoadedMap().findCountry("b6").getD_NumberOfArmies();
            l_ArmiesDestCountryBefore = d_GE.getD_LoadedMap().findCountry("b7").getD_NumberOfArmies();
            Advance l_Adv = new Advance("b6","b7",10,d_GE);
            l_Adv.execute();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        int l_ArmiesSrcCountryAfter = d_GE.getD_LoadedMap().findCountry("b6").getD_NumberOfArmies();
        int l_ArmiesDestCountryAfter = d_GE.getD_LoadedMap().findCountry("b7").getD_NumberOfArmies();
        org.junit.Assert.assertEquals("Armies in source countries ", l_ArmiesSrcCountryBefore - 10, l_ArmiesSrcCountryAfter);
    }
}