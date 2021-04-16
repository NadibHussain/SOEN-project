package team14.warzone.GameEngine.Strategy;

import team14.warzone.Console.Console;
import team14.warzone.GameEngine.Card;
import team14.warzone.GameEngine.Commands.*;
import team14.warzone.GameEngine.GameEngine;
import team14.warzone.GameEngine.Player;
import team14.warzone.MapModule.Country;
import team14.warzone.Utils.Randomizer;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Objects;

/**
 * Utility methods for player behaviors
 */
public class BehaviorUtilities {

    /**
     * Issue advance order
     *
     * @param p_GE                 Game Engine object
     * @param p_Player             Player object
     * @param p_SourceCountry      Country to attack from
     * @param p_DestinationCountry Country to attack
     * @param p_NumOfArmies        Number of armies
     */
    public static void issueAdvance(GameEngine p_GE, Player p_Player, Country p_SourceCountry,
                                    Country p_DestinationCountry, int p_NumOfArmies) {
        Advance l_Advance = new Advance(p_SourceCountry.getD_CountryID(), p_DestinationCountry.getD_CountryID(),
                p_NumOfArmies, p_GE);
        // add order to order list
        p_Player.getD_OrderList().add(l_Advance);
        Console.displayMsg(p_Player.getD_Name() + " issued: advance " + p_SourceCountry.getD_CountryID() +
                " " + p_DestinationCountry.getD_CountryID() + " " + p_NumOfArmies);
        // write to log
        p_GE.getD_LogEntryBuffer().setD_log(p_Player.getD_Name() + " issued advance command");
        p_GE.getD_LogEntryBuffer().notifyObservers(p_GE.getD_LogEntryBuffer());
    }

    /**
     * Issue bomb order
     *
     * @param p_GE     Game Engine object
     * @param p_Player Player object
     * @param p_Card   Card
     */
    public static void issueBomb(GameEngine p_GE, Player p_Player, Card p_Card) {
        // find country to bomb
        Country l_DestinationCountry = findRandomEnemyNeighborWithArmy(p_Player);

        Bomb l_Advance = new Bomb(l_DestinationCountry.getD_CountryID(), p_GE);
        // add order to order list
        p_Player.getD_OrderList().add(l_Advance);
        Console.displayMsg(p_Player.getD_Name() + " issued: bomb on " + l_DestinationCountry.getD_CountryID());
        p_Player.setCardUsed("bomb");
        // write to log
        p_GE.getD_LogEntryBuffer().setD_log(p_Player.getD_Name() + " issued advance command");
        p_GE.getD_LogEntryBuffer().notifyObservers(p_GE.getD_LogEntryBuffer());
    }

    /**
     * Issue blockade order
     *
     * @param p_GE     Game Engine object
     * @param p_Player Player object
     * @param p_Card   Card
     */
    public static void issueBlockade(GameEngine p_GE, Player p_Player, Card p_Card) {
        Country l_DestinationCountry = findCountryAtRisk(p_Player);
        if (Objects.isNull(l_DestinationCountry)) {
            l_DestinationCountry = p_Player.getD_CountriesOwned().get(Randomizer.generateRandomNumber(0,
                    p_Player.getD_CountriesOwned().size() - 1));
        }
        Blockade l_Blockade = new Blockade(l_DestinationCountry.getD_CountryID(), p_GE);

        p_Player.getD_OrderList().add(l_Blockade);
        Console.displayMsg(p_Player.getD_Name() + " issued: blockade on " + l_DestinationCountry.getD_CountryID());
        p_Player.setCardUsed("blockade");
        // write to log
        p_GE.getD_LogEntryBuffer().setD_log(p_Player.getD_Name() + " issued blockade command on " + l_DestinationCountry.getD_CountryID());
        p_GE.getD_LogEntryBuffer().notifyObservers(p_GE.getD_LogEntryBuffer());
    }

    /**
     * Issue airlift order
     *
     * @param p_GE     Game Engine
     * @param p_Player Player
     * @param p_Card   Player
     */
    public static void issueAirlift(GameEngine p_GE, Player p_Player, Card p_Card) {
        // find strongest country and weakest country
        Country l_StrongestCountry = findStrongestCountry(p_Player);
        Country l_WeakestCountry = findWeakestCountry(p_Player);
        int l_NumOfArmies = l_StrongestCountry.getD_NumberOfArmies() / 2;

        // airlift from strongest to weakest
        Airlift l_Airlift = new Airlift(l_StrongestCountry.getD_CountryID(), l_WeakestCountry.getD_CountryID(),
                l_NumOfArmies, p_GE);

        p_Player.getD_OrderList().add(l_Airlift);
        Console.displayMsg(p_Player.getD_Name() + " issued: airlift from " + l_StrongestCountry.getD_CountryID() + " " +
                "to " + l_WeakestCountry.getD_CountryID());
        p_Player.setCardUsed("airlift");
        // write to log
        p_GE.getD_LogEntryBuffer().setD_log(p_Player.getD_Name() + " issued airlift command from " + l_StrongestCountry.getD_CountryID() + " " +
                "to " + l_WeakestCountry.getD_CountryID());
        p_GE.getD_LogEntryBuffer().notifyObservers(p_GE.getD_LogEntryBuffer());
    }

    /**
     * Issue diplomacy order
     *
     * @param p_GE     Game Engine
     * @param p_Player Player
     * @param p_Card   Card
     */
    public static void issueDiplomacy(GameEngine p_GE, Player p_Player, Card p_Card) {
        // find target player
        Player l_TargetPlayer = null;
        for (Player l_Player : p_GE.getD_PlayerList()) {
            if (!l_Player.equals(p_Player)) {
                l_TargetPlayer = l_Player;
                break;
            }
        }

        // issue diplomacy order
        Diplomacy l_Diplomacy = new Diplomacy(l_TargetPlayer.getD_Name(), p_GE);

        p_Player.getD_OrderList().add(l_Diplomacy);
        Console.displayMsg(p_Player.getD_Name() + " issued: diplomacy with player " + l_TargetPlayer.getD_Name());
        p_Player.setCardUsed("diplomacy");
        // write to log
        p_GE.getD_LogEntryBuffer().setD_log(p_Player.getD_Name() + " issued: diplomacy with player " + l_TargetPlayer.getD_Name());
        p_GE.getD_LogEntryBuffer().notifyObservers(p_GE.getD_LogEntryBuffer());
    }


    /**
     * Find the country most at risk
     *
     * @param p_Player Player
     * @return Country object
     */
    public static Country findCountryAtRisk(Player p_Player) {
        int l_Risk = 0;
        Country l_CountryAtRisk = null;
        ArrayList<Country> l_CountriesOwned = p_Player.getD_CountriesOwned();

        // loop and try to find country which has enemy neighbor with highest number of armies
        for (Country l_Country : l_CountriesOwned) {
            for (Country l_Neighbor : l_Country.getD_Neighbours()) {
                // country has armies
                if (l_Country.getD_NumberOfArmies() > 0) {
                    // check neighbor is enemy and enemy army greater than current risk
                    if (!l_Neighbor.getD_CurrentOwner().equals(l_Country.getD_CurrentOwner()) && l_Neighbor.getD_NumberOfArmies() > l_Risk) {
                        l_Risk = l_Neighbor.getD_NumberOfArmies();
                        l_CountryAtRisk = l_Country;
                    }
                }
            }
        }

        // if no country with army+enemy neighbor found play blockade at any country with army
        if (Objects.isNull(l_CountryAtRisk)) {
            for (Country l_Country : l_CountriesOwned) {
                if (l_Country.getD_NumberOfArmies() > 0) {
                    l_CountryAtRisk = l_Country;
                    break;
                }
            }
        }

        return l_CountryAtRisk;
    }

    /**
     * Find strong neighbor of weak country
     *
     * @param p_Country Country
     * @return Country object
     */
    public static Country findStrongNeighbor(Country p_Country) {
        for (Country l_Neighbor : p_Country.getD_Neighbours()) {
            if (
                    l_Neighbor.getD_CurrentOwner().equals(p_Country.getD_CurrentOwner())
                            && l_Neighbor.getD_NumberOfArmies() > p_Country.getD_NumberOfArmies() + 5
            ) {
                return l_Neighbor;
            }
        }
        return null;
    }

    /**
     * Find the current players strongest country
     *
     * @param p_Player Player
     * @return Country object
     */
    public static Country findStrongestCountry(Player p_Player) {
        ArrayList<Country> l_CountryList = p_Player.getD_CountriesOwned();
        Country l_StrongestCountry = null;
        l_CountryList.sort(Comparator.comparing(Country::getD_NumberOfArmies));
        l_StrongestCountry = l_CountryList.get(l_CountryList.size() - 1);
        return l_StrongestCountry;
    }

    /**
     * Reinforce weaker country - find weaker countries
     *
     * @param p_Player Player
     * @return List of countries
     */
    public static ArrayList<Country> findWeakerCountriesWithStrongNeighbor(Player p_Player) {
        findWeakestCountry(p_Player);
        ArrayList<Country> l_CountryList = p_Player.getD_CountriesOwned();
        ArrayList<Country> l_WeakCountryList = new ArrayList<>();
        // add weak countries which have strong neighbors
        for (Country l_Country : l_CountryList) {
            // check if neighbor owned by self and neighbor is stronger (by 5)
            for (Country l_Neighbor : l_Country.getD_Neighbours()) {
                if (l_Neighbor.getD_CurrentOwner().equals(p_Player.getD_Name()) &&
                        l_Neighbor.getD_NumberOfArmies() > l_Country.getD_NumberOfArmies() + 5) {
                    l_WeakCountryList.add(l_Country);
                }
            }
        }
        return l_WeakCountryList;
    }

    /**
     * Find weakest country
     *
     * @param p_Player Player
     * @return Country
     */
    public static Country findWeakestCountry(Player p_Player) {
        ArrayList<Country> l_CountryList = p_Player.getD_CountriesOwned();
        Country l_WeakestCountry = null;
        if (l_CountryList.size() != 0) {
            l_CountryList.sort(Comparator.comparing(Country::getD_NumberOfArmies));
            l_WeakestCountry = l_CountryList.get(0);
        }
        return l_WeakestCountry;
    }

    /**
     * Find a neighboring enemy country that has armies
     *
     * @param p_Player Player object
     * @return Country object
     */
    public static Country findRandomEnemyNeighborWithArmy(Player p_Player) {
        ArrayList<Country> l_CountryList = p_Player.getD_CountriesOwned();
        ArrayList<Country> l_EnemyNeighborsWithArmies = new ArrayList<>();
        ArrayList<Country> l_EnemyNeighbors = new ArrayList<>();
        // find enemy neighbors with armies
        for (Country l_Country : l_CountryList) {
            ArrayList<Country> l_Neighbors = l_Country.getD_Neighbours();
            for (Country l_Neighbor : l_Neighbors) {
                if (!l_Neighbor.getD_CurrentOwner().equals(l_Country.getD_CurrentOwner()) && l_Neighbor.getD_NumberOfArmies() > 0) {
                    l_EnemyNeighborsWithArmies.add(l_Neighbor);
                    l_EnemyNeighbors.add(l_Neighbor);
                } else if (!l_Neighbor.getD_CurrentOwner().equals(l_Country.getD_CurrentOwner())) {
                    l_EnemyNeighbors.add(l_Neighbor);
                }
            }
        }
        // select one of the enemy neighbors randomly
        if (!l_EnemyNeighborsWithArmies.isEmpty()) {
            return l_EnemyNeighborsWithArmies.get(Randomizer.generateRandomNumber(0,
                    l_EnemyNeighborsWithArmies.size() - 1));
        } else if (!l_EnemyNeighbors.isEmpty()) {
            return l_EnemyNeighbors.get(Randomizer.generateRandomNumber(0,
                    l_EnemyNeighbors.size() - 1));
        } else {
            return null;
        }
    }
}
