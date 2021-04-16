package team14.warzone.GameEngine.Strategy;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import team14.warzone.Console.Console;
import team14.warzone.GameEngine.Card;
import team14.warzone.GameEngine.Commands.*;
import team14.warzone.GameEngine.GameEngine;
import team14.warzone.GameEngine.Player;
import team14.warzone.MapModule.AdapterMapEditor;
import team14.warzone.MapModule.Country;
import team14.warzone.MapModule.Map;
import team14.warzone.MapModule.MapEditorConquest;

import java.util.ArrayList;

public class BehaviorUtilitiesTest {
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
     * list of players
     */
    private ArrayList<Player> d_Players;
    /**
     * map game is being played on
     */
    private Map d_Map;

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
        d_Map = d_GE.getD_LoadedMap();
        // startup phase
        d_GE.getD_CurrentPhase().addPlayer("p1", "aggressive");
        d_GE.getD_CurrentPhase().addPlayer("p2", "benevolent");
        d_GE.getD_CurrentPhase().assignCountries();
        d_Players = d_GE.getD_PlayerList();
        // deploy armies in two countries
        d_GE.getD_LoadedMap().findCountry("s1").setD_NumberOfArmies(50);
        d_GE.getD_LoadedMap().findCountry("s2").setD_NumberOfArmies(50);
        d_GE.getD_LoadedMap().findCountry("b9").setD_NumberOfArmies(90);
        d_GE.getD_LoadedMap().findCountry("b8").setD_NumberOfArmies(30);
    }

    /**
     * Test issueAdvance method
     */
    @Test
    public void issueAdvance() {
        Player l_Player = d_Players.get(0);
        BehaviorUtilities.issueAdvance(d_GE,
                l_Player,
                d_Map.findCountry("s1"),
                d_Map.findCountry("s3"),
                30);
        Assert.assertTrue(l_Player.getD_OrderList().get(0) instanceof Advance);
    }

    /**
     * Attempts to use the bomb card
     */
    @Test
    public void issueBomb() {
        Player l_Player = d_Players.get(0);
        BehaviorUtilities.issueBomb(d_GE,
                l_Player,
                new Card("bomb"));
        Assert.assertTrue(l_Player.getD_OrderList().get(0) instanceof Bomb);
    }

    /**
     * Attempts to use blockade card
     */
    @Test
    public void issueBlockade() {
        Player l_Player = d_Players.get(0);
        BehaviorUtilities.issueBlockade(d_GE,
                l_Player,
                new Card("blockade"));
        Assert.assertTrue(l_Player.getD_OrderList().get(0) instanceof Blockade);
    }

    /**
     * Attempts to use airlift card
     */
    @Test
    public void issueAirlift() {
        Player l_Player = d_Players.get(0);
        BehaviorUtilities.issueAirlift(d_GE,
                l_Player,
                new Card("airlift"));
        Assert.assertTrue(l_Player.getD_OrderList().get(0) instanceof Airlift);
    }

    /**
     * Attempts to issue diplomacy order
     */
    @Test
    public void issueDiplomacy() {
        Player l_Player = d_Players.get(0);
        BehaviorUtilities.issueDiplomacy(d_GE,
                l_Player,
                new Card("diplomacy"));
        Assert.assertTrue(l_Player.getD_OrderList().get(0) instanceof Diplomacy);
    }

    /**
     * Verify that the country having strongest enemy neighbor is the country at risk
     */
    @Test
    public void findCountryAtRisk() {
        Country l_CountryAtRisk = BehaviorUtilities.findCountryAtRisk(d_Players.get(0));
        Assert.assertEquals(d_Map.findCountry("b8"), l_CountryAtRisk);
    }

    /**
     * Verify method returns strong friendly neighboring country
     */
    @Test
    public void findStrongNeighbor() {
        Country l_StrongNeighbor = BehaviorUtilities.findStrongNeighbor(d_Map.findCountry("b11"));
        Assert.assertEquals(d_Map.findCountry("b9"), l_StrongNeighbor);
    }

    /**
     * Verify method returns strongest country owned by current player
     */
    @Test
    public void findStrongestCountry() {
        Country l_StrongestCountry = BehaviorUtilities.findStrongestCountry(d_Players.get(1));
        Assert.assertEquals(d_Map.findCountry("b9"), l_StrongestCountry);
    }

    /**
     * Verify that the list of countries returned are the ones at risk (have strong enemy neighbors)
     */
    @Test
    public void findWeakerCountriesWithStrongNeighbor() {
        ArrayList<Country> l_WeakCountriesWithStrongNeighbors =
                BehaviorUtilities.findWeakerCountriesWithStrongNeighbor(d_Players.get(1));
        Assert.assertEquals(d_Map.findCountry("s8"), l_WeakCountriesWithStrongNeighbors.get(0));
        Assert.assertEquals(d_Map.findCountry("b3"), l_WeakCountriesWithStrongNeighbors.get(1));
        Assert.assertEquals(d_Map.findCountry("b11"), l_WeakCountriesWithStrongNeighbors.get(2));
    }

    /**
     * Verify method returns passed player's weakest country. Set all owned country to higher value than one specific
     * country to check.
     */
    @Test
    public void findWeakestCountry() {
        ArrayList<Country> l_CountriesOwned = d_Players.get(0).getD_CountriesOwned();
        for (Country l_Country : l_CountriesOwned) {
            l_Country.setD_NumberOfArmies(10);
        }
        d_Map.findCountry("s1").setD_NumberOfArmies(1);
        Country l_WeakestCountry = BehaviorUtilities.findWeakestCountry(d_Players.get(0));
        Assert.assertEquals(d_Map.findCountry("s1"), l_WeakestCountry);
    }

    /**
     * Verify method returns a random enemy neighbor that has armies deployed in it
     */
    @Test
    public void findRandomEnemyNeighborWithArmy() {
        d_GE.getD_LoadedMap().findCountry("b9").setD_NumberOfArmies(0);
        Country l_Country = BehaviorUtilities.findRandomEnemyNeighborWithArmy(d_Players.get(0));
        Assert.assertTrue(l_Country instanceof Country);
        Assert.assertEquals(d_Map.findCountry("s2"), l_Country);
    }
}