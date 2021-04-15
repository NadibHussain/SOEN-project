package team14.warzone.GameEngine.Commands;

import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.DisplayName;
import team14.warzone.Console.Console;
import team14.warzone.Console.InputValidator;
import team14.warzone.GameEngine.Card;
import team14.warzone.GameEngine.GameEngine;
import team14.warzone.GameEngine.Player;
import team14.warzone.MapModule.AdapterMapEditor;
import team14.warzone.MapModule.Map;
import team14.warzone.MapModule.MapEditorConquest;

import static org.junit.Assert.assertEquals;

/**
 * Test for Airlift
 */
public class AirliftTest {
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
        Player l_P1 = d_GE.getD_PlayerList().get(0);
        d_GE.setD_CurrentPlayer(l_P1); // p1 turn
        //assign airlift card to p1
        l_P1.addCard(new Card("airlift"));
        //add armies to some countries in the map
        d_GE.getD_LoadedMap().findCountry("s1").setD_NumberOfArmies(15);
        d_GE.getD_LoadedMap().findCountry("s2").setD_NumberOfArmies(6);
        d_GE.getD_LoadedMap().findCountry("s3").setD_NumberOfArmies(10);
    }

    /**
     * Tries to move armies to a country that Player p1 doesn't own (airlift should fail), then move it to a country
     * he owns (airlift should succeed)
     */
    @Test
    @DisplayName("Testing airlift Armies")
    public void executeTest() {
        Map l_Map = d_GE.getD_LoadedMap();
        int l_ArmiesSrcCountryBefore = d_GE.getD_LoadedMap().findCountry("s1").getD_NumberOfArmies();
        ;
        try {
            Airlift l_AirLift = new Airlift("s1", "s2", 10, d_GE);
            l_AirLift.execute();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        assertEquals("s1", l_Map.findCountry("s1").getD_CountryID());
        assertEquals("s2", l_Map.findCountry("s2").getD_CountryID());
        int l_ArmiesSrcCountryAfter = d_GE.getD_LoadedMap().findCountry("s1").getD_NumberOfArmies();
        org.junit.Assert.assertTrue(l_ArmiesSrcCountryAfter == l_ArmiesSrcCountryBefore);

        int l_ArmiesDestCountryBefore = d_GE.getD_LoadedMap().findCountry("s3").getD_NumberOfArmies();
        try {
            Airlift l_AirLift = new Airlift("s1", "s3", 10, d_GE);
            l_AirLift.execute();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        l_ArmiesSrcCountryAfter = d_GE.getD_LoadedMap().findCountry("s1").getD_NumberOfArmies();
        int l_ArmiesDestCountryAfter = d_GE.getD_LoadedMap().findCountry("s3").getD_NumberOfArmies();
        org.junit.Assert.assertTrue(l_ArmiesSrcCountryAfter == (l_ArmiesSrcCountryBefore - 10));
        org.junit.Assert.assertTrue(l_ArmiesDestCountryAfter == (l_ArmiesDestCountryBefore + 10));
    }

}