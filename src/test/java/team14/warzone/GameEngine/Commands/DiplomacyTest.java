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

/**
 * This class tests the negotiate order
 */
public class DiplomacyTest {
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
        d_GE.getD_CurrentPhase().loadMap("bigeurope.map");
        InputValidator.CURRENT_PHASE = InputValidator.Phase.STARTUP;
        d_GE.getD_CurrentPhase().addPlayer("p1", "human");
        d_GE.getD_CurrentPhase().addPlayer("p2", "human");
        d_GE.getD_CurrentPhase().assignCountries();
        InputValidator.CURRENT_PHASE = InputValidator.Phase.GAMEPLAY;
        Player l_P1 = d_GE.getD_PlayerList().get(0);
        d_GE.setD_CurrentPlayer(l_P1); // p1 turn
        //assign airlift card to p1
        l_P1.addCard(new Card("diplomacy"));
    }

    /**
     * Tries to bomb a country that Player p1 doesn't own (enemy country), as a result the number of armies
     * will be decreased by half
     */
    @Test
    @DisplayName("Testing negotiate order")
    public void executeTest() {
        try {
            Diplomacy l_Diplomacy = new Diplomacy("p2", d_GE);
            d_GE.allotCard(d_GE.getD_CurrentPlayer());
            boolean l_HasCard = d_GE.getD_CurrentPlayer().hasCard(new Card("diplomacy"));
            assert l_HasCard == true;
            System.out.println(d_GE.getD_CurrentPlayer().getCardList().get(0).getD_CardType());
            l_Diplomacy.execute();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        org.junit.Assert.assertTrue(d_GE.getD_CurrentPlayer().isDiplomaticPlayer(d_GE.getD_CurrentPlayer(), d_GE.findPlayer("p2")));
    }
}