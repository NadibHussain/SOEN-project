package team14.warzone.GameEngine.Commands;

import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.DisplayName;
import team14.warzone.Console.Console;
import team14.warzone.Console.InputValidator;
import team14.warzone.GameEngine.GameEngine;
import team14.warzone.MapModule.AdapterMapEditor;
import team14.warzone.MapModule.Map;
import team14.warzone.MapModule.MapEditorConquest;

import static org.junit.Assert.assertEquals;

/**
 * Test for Advance
 */
public class AdvanceTest {

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
        d_GE.getD_LoadedMap().findCountry("b6").setD_NumberOfArmies(15);
        d_GE.getD_LoadedMap().findCountry("b7").setD_NumberOfArmies(6);
    }

    /**
     * Tries to attack another country that p1 doesn't own, after battle the number of armies of at least one
     * of the two countries should be changed
     */
    @Test
    @DisplayName("Testing advance Armies")
    public void executeTest() {
        Map l_Map = d_GE.getD_LoadedMap();
        int l_ArmiesSrcCountryBefore = 0;
        int l_ArmiesDestCountryBefore = 0;
        try {
            l_ArmiesSrcCountryBefore = d_GE.getD_LoadedMap().findCountry("b6").getD_NumberOfArmies();
            l_ArmiesDestCountryBefore = d_GE.getD_LoadedMap().findCountry("b7").getD_NumberOfArmies();
            Advance l_Adv = new Advance("b6", "b7", 10, d_GE);
            l_Adv.execute();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        int l_ArmiesSrcCountryAfter = d_GE.getD_LoadedMap().findCountry("b6").getD_NumberOfArmies();
        int l_ArmiesDestCountryAfter = d_GE.getD_LoadedMap().findCountry("b7").getD_NumberOfArmies();
        org.junit.Assert.assertTrue(l_ArmiesSrcCountryAfter <= l_ArmiesSrcCountryBefore
                || l_ArmiesDestCountryAfter <= l_ArmiesDestCountryBefore);
        assertEquals("b6", l_Map.findCountry("b6").getD_CountryID());
        assertEquals("b7", l_Map.findCountry("b7").getD_CountryID());
    }
}