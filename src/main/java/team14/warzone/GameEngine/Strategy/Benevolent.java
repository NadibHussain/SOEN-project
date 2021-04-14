package team14.warzone.GameEngine.Strategy;

import team14.warzone.Console.Console;
import team14.warzone.GameEngine.Card;
import team14.warzone.GameEngine.Commands.*;
import team14.warzone.GameEngine.GameEngine;
import team14.warzone.GameEngine.Player;
import team14.warzone.MapModule.Country;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Objects;

public class Benevolent implements Behavior {
    private ArrayList<Country> d_WeakCountryList = new ArrayList<>();

    @Override
    public void issueOrder(GameEngine p_GE, Player p_Player) {
        int l_ExpectedNumberOfOrders = 3;
        int l_ArmiesLeftToDeploy = p_Player.getD_TotalNumberOfArmies() - p_Player.getD_ArmiesOrderedToBeDeployed();

        // deploy to weakest
        if (l_ArmiesLeftToDeploy > 0) {
            // find weakest country
            Country l_Country = findWeakestCountry(p_Player);
            // instantiate deploy order
            Deploy l_Deploy = new Deploy(l_Country.getD_CountryID(), l_ArmiesLeftToDeploy
                    , p_GE);
            Console.displayMsg(p_Player.getD_Name() + " issued: deploy " + l_Country.getD_CountryID() + " " + l_ArmiesLeftToDeploy);
            // add deploy to player's order list
            p_Player.getD_OrderList().add(l_Deploy);
            // write to log
            p_GE.getD_LogEntryBuffer().setD_log(p_Player.getD_Name() + " issued deploy command");
            p_GE.getD_LogEntryBuffer().notifyObservers(p_GE.getD_LogEntryBuffer());
        }

        // play cards if any
        else if (!p_Player.getCardList().isEmpty() && !p_Player.getCardList().get(0).isD_Used()) {
            Card l_Card = p_Player.getCardList().get(0);
            switch (l_Card.getD_CardType()) {
                case "blockade":
                    issueBlockade(p_GE, p_Player, l_Card);
                    break;
                case "airlift":
                    issueAirlift(p_GE, p_Player, l_Card);
                    break;
                case "diplomacy":
                    issueDiplomacy(p_GE, p_Player, l_Card);
                    break;

                default:
                    p_Player.removeCard(l_Card);
                    break;
            }
        }

        // advance from strong to weak (self)
        else if (p_Player.getD_OrderList().size() < l_ExpectedNumberOfOrders) {
            // find if any weak country has strong neighbor
            ArrayList<Country> l_WeakCountries = findWeakerCountriesWithStrongNeighbor(p_Player);
            if (l_WeakCountries.size() > 0) {
                Country l_DestinationCountry = l_WeakCountries.get(0);
                Country l_SourceCountry = findStrongNeighbor(l_DestinationCountry);
                if (Objects.nonNull(l_SourceCountry)) {
                    int l_NumOfArmiesToAttackWith = (l_SourceCountry.getD_NumberOfArmies() / 2) + 1;
                    // generate advance order
                    Advance l_Advance = new Advance(l_SourceCountry.getD_CountryID(),
                            l_DestinationCountry.getD_CountryID(),
                            l_NumOfArmiesToAttackWith, p_GE);
                    p_Player.getD_OrderList().add(l_Advance);
                    Console.displayMsg(p_Player.getD_Name() + " issued: advance (relocate army) " + l_SourceCountry.getD_CountryID() +
                            " " + l_DestinationCountry.getD_CountryID() + " " + l_NumOfArmiesToAttackWith);
                    // write to log
                    p_GE.getD_LogEntryBuffer().setD_log(p_Player.getD_Name() + " issued advance command");
                    p_GE.getD_LogEntryBuffer().notifyObservers(p_GE.getD_LogEntryBuffer());
                } else {
                    Console.displayMsg(p_Player.getD_Name() + ": pass");
                    p_GE.setD_PlayerPassed(true);
                }
            } else {
                Console.displayMsg(p_Player.getD_Name() + ": pass");
                p_GE.setD_PlayerPassed(true);
            }
        }

        // pass
        else {
            Console.displayMsg(p_Player.getD_Name() + ": pass");
            p_GE.setD_PlayerPassed(true);
        }
    }

    // find weakest country
    private Country findWeakestCountry(Player p_Player) {
        ArrayList<Country> l_CountryList = p_Player.getD_CountriesOwned();
        Country l_WeakestCountry = null;
        if (l_CountryList.size() != 0) {
            l_CountryList.sort(Comparator.comparing(Country::getD_NumberOfArmies));
            l_WeakestCountry = l_CountryList.get(0);
        }
        return l_WeakestCountry;
    }

    private Country findStrongestCountry(Player p_Player) {
        ArrayList<Country> l_CountryList = p_Player.getD_CountriesOwned();
        Country l_StrongestCountry = null;
        l_CountryList.sort(Comparator.comparing(Country::getD_NumberOfArmies));
        l_StrongestCountry = l_CountryList.get(l_CountryList.size() - 1);
        return l_StrongestCountry;
    }

    // reinforce weaker country - find weaker countries
    private ArrayList<Country> findWeakerCountriesWithStrongNeighbor(Player p_Player) {
        findWeakestCountry(p_Player);
        ArrayList<Country> l_CountryList = p_Player.getD_CountriesOwned();
        // add weak countries which have strong neighbors
        for (Country l_Country : l_CountryList) {
            // check if neighbor owned by self and neighbor is stronger (by 5)
            for (Country l_Neighbor : l_Country.getD_Neighbours()) {
                if (l_Neighbor.getD_CurrentOwner().equals(p_Player.getD_Name()) &&
                        l_Neighbor.getD_NumberOfArmies() > l_Country.getD_NumberOfArmies() + 5) {
                    d_WeakCountryList.add(l_Country);
                }
            }
        }
        return d_WeakCountryList;
    }

    // find strong neighbor of weak country
    private Country findStrongNeighbor(Country p_Country) {
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

    private Country findCountryAtRisk(Player p_Player) {
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

    private void issueBlockade(GameEngine p_GE, Player p_Player, Card p_Card) {
        Country l_DestinationCountry = findCountryAtRisk(p_Player);
        Blockade l_Blockade = new Blockade(l_DestinationCountry.getD_CountryID(), p_GE);

        p_Player.getD_OrderList().add(l_Blockade);
        Console.displayMsg(p_Player.getD_Name() + " issued: blockade on " + l_DestinationCountry.getD_CountryID());
        p_Player.setCardUsed("blockade");
        // write to log
        p_GE.getD_LogEntryBuffer().setD_log(p_Player.getD_Name() + " issued blockade command on " + l_DestinationCountry.getD_CountryID());
        p_GE.getD_LogEntryBuffer().notifyObservers(p_GE.getD_LogEntryBuffer());
    }

    private void issueAirlift(GameEngine p_GE, Player p_Player, Card p_Card) {
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

    private void issueDiplomacy(GameEngine p_GE, Player p_Player, Card p_Card) {
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
     * @return name of behavior
     */
    @Override
    public String toString() {
        return "benevolent";
    }
}
