package team14.warzone.GameEngine.Strategy;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import team14.warzone.Console.Console;
import team14.warzone.GameEngine.GameEngine;
import team14.warzone.GameEngine.Player;
import team14.warzone.MapModule.Country;
import team14.warzone.MapModule.Map;
import team14.warzone.MapModule.AdapterMapEditor;
import team14.warzone.MapModule.MapEditorConquest;

import java.util.ArrayList;
import java.util.List;

public class CheaterTest {
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
     * Setup context for test case
     */
    @Before
    public void init() {
        d_Console = new Console();
        d_MapEditor = new AdapterMapEditor(new MapEditorConquest());
        d_GE = new GameEngine(d_Console, d_MapEditor);
        // map editor phase
        d_GE.getD_CurrentPhase().loadMap("bigeurope.map");
        // startup phase
        d_GE.getD_CurrentPhase().addPlayer("p1", "cheater");
        d_GE.getD_CurrentPhase().addPlayer("p2", "benevolent");
        d_GE.getD_CurrentPhase().addPlayer("p3", "aggressive");
        d_GE.getD_CurrentPhase().addPlayer("p4", "random");
        d_GE.getD_CurrentPhase().addPlayer("p5", "random");

        d_GE.getD_CurrentPhase().assignCountries();
    }

    /**
     * Check if cheater will take ownership of all its neighbours at the beginning of the turn
     */
    @Test
    public void cheaterTest() {
        //conquer all the immediate neighboring enemy countries
        Player l_Player1 = d_GE.getD_PlayerList().get(0);
        List<Country> l_CountriesOwned1 = l_Player1.getD_CountriesOwned();
        List<Country> l_ConqueredCountries = new ArrayList<>();
        for (Country l_Country : l_CountriesOwned1) {
            List<Country> l_CountryNeighbors = l_Country.getD_Neighbours();
            for (Country l_NeighborCountry : l_CountryNeighbors) {
                if (l_NeighborCountry.getD_CurrentOwner() != d_GE.getD_PlayerList().get(0).getD_Name()) {
                    l_ConqueredCountries.add(l_NeighborCountry);
                }
            }
        }

        //choose an a country that has enemy neighbours and set a specific number of armies for testing
        Map l_LoadedMap = d_GE.getD_LoadedMap();
        Country l_CountryWithEnemyNeigbour = l_LoadedMap.findCountry("s2");
        l_CountryWithEnemyNeigbour.setD_NumberOfArmies(20);

        d_GE.getD_CurrentPhase().reinforce();
        d_GE.getD_PlayerList().get(0).issueOrder();
        d_GE.setD_CurrentPhase(d_GE.getD_ExecuteOrdersPhase());
        //check if number of armies in the strongest country increased by 50 (initial number of armies owned by
        // player at game start)
        d_GE.getD_CurrentPhase().run();
        Assert.assertTrue(l_Player1.getD_CountriesOwned().containsAll(l_ConqueredCountries));
        //check if country armies are doubled by cheater
        Assert.assertEquals(40, l_CountryWithEnemyNeigbour.getD_NumberOfArmies());
    }
}
