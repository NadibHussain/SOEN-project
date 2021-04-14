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

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

/**
 * This class tests the bomb order
 */
public class BombTest {
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
        l_P1.addCard(new Card("bomb"));
        //add armies to some countries in the map
        d_GE.getD_LoadedMap().findCountry("s2").setD_NumberOfArmies(6);
    }

    /**
     * Tries to bomb a country that Player p1 doesn't own (enemy country), as a result the number of armies
     * will be decreased by half
     */
    @Test
    @DisplayName("Testing bomb order")
    public void executeTest() {
        Map l_Map = d_GE.getD_LoadedMap();
        int l_ArmiesDestCountryBefore = d_GE.getD_LoadedMap().findCountry("s2").getD_NumberOfArmies();
        ;
        try {
            Bomb l_Bomb = new Bomb("s2", d_GE);
            d_GE.allotCard(d_GE.getD_CurrentPlayer());
            boolean l_HasCard = d_GE.getD_CurrentPlayer().hasCard(new Card("bomb"));
            assert l_HasCard == true;
            System.out.println(d_GE.getD_CurrentPlayer().getCardList().get(0).getD_CardType());
            assertEquals("s2", l_Map.findCountry("s2").getD_CountryID());
            System.out.println(l_Map.getD_Countries().get(1).getD_CountryID());
            assertNotEquals(l_Map.findCountry("s2").getD_CurrentOwner(), d_GE.getD_CurrentPlayer().getD_Name());
            l_Bomb.execute();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        int l_ArmiesDestCountryAfter = d_GE.getD_LoadedMap().findCountry("s2").getD_NumberOfArmies();
        org.junit.Assert.assertEquals((l_ArmiesDestCountryBefore / 2), l_ArmiesDestCountryAfter);
    }
}