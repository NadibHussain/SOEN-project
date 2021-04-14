package team14.warzone.GameEngine;

import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.DisplayName;
import team14.warzone.Console.Console;
import team14.warzone.Console.InputValidator;
import team14.warzone.GameEngine.Commands.Advance;
import team14.warzone.MapModule.AdapterMapEditor;
import team14.warzone.MapModule.Map;
import team14.warzone.MapModule.MapEditorConquest;

import static org.junit.Assert.assertEquals;

/**
 * This class tests the Card allot feature
 */
public class CardTest {
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
        d_GE.getD_LoadedMap().findCountry("b6").setD_NumberOfArmies(30);
        d_GE.getD_LoadedMap().findCountry("b7").setD_NumberOfArmies(5);
        Player l_P1 = d_GE.getD_PlayerList().get(0);
        d_GE.setD_CurrentPlayer(l_P1);
    }
        /**
         * A successful attack is simulated, p1 attacks country b7 and conquers it. p1 should now receive a random card
         * and the size of player p1 cardlist should be 1
         */
        @Test
        @DisplayName("Testing Card Allotment")
        public void executeTest() {
            Map l_Map = d_GE.getD_LoadedMap();

            try {
                Advance l_Adv = new Advance("b6", "b7", 18, d_GE);
                l_Adv.execute();

            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
            d_GE.allotCard(d_GE.getD_CurrentPlayer());
            assertEquals(1, d_GE.getD_CurrentPlayer().getCardList().size());
        }


    }



