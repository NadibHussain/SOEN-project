package team14.warzone.GameEngine.Commands;

import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.DisplayName;
import team14.warzone.Console.Console;
import team14.warzone.Console.InputValidator;
import team14.warzone.GameEngine.Card;
import team14.warzone.GameEngine.GameEngine;
import team14.warzone.GameEngine.Player;
import team14.warzone.MapModule.MapEditor;

import static org.junit.Assert.*;

public class BlockadeTest {
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
        Player l_P1 = d_GE.getD_PlayerList().get(0);
        d_GE.setD_CurrentPlayer(l_P1); // p1 turn
        //assign airlift card to p1
        l_P1.addCard(new Card("blockade"));
        //add armies to some countries in the map
        d_GE.getD_LoadedMap().findCountry("s1").setD_NumberOfArmies(15);
    }

    /**
     * Tries to blockade a country that Player p1 own, as a result the number of armies will be tripled
     * and the ownership of destination country will ne changed to neutral player
     */
    @Test
    @DisplayName("Testing blockade order")
    public void executeTest() {
        int l_ArmiesDestCountryBefore = d_GE.getD_LoadedMap().findCountry("s1").getD_NumberOfArmies();
        try {
            Blockade l_Blockade = new Blockade("s1", d_GE);
            l_Blockade.execute();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        int l_ArmiesDestCountryAfter = d_GE.getD_LoadedMap().findCountry("s1").getD_NumberOfArmies();
        org.junit.Assert.assertEquals((l_ArmiesDestCountryBefore * 3), l_ArmiesDestCountryAfter);
        org.junit.Assert.assertTrue(d_GE.getD_LoadedMap().findCountry("s1").getD_CurrentOwner().equals("Neutral"));
    }
}