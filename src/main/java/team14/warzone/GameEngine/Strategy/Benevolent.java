package team14.warzone.GameEngine.Strategy;

import team14.warzone.Console.Console;
import team14.warzone.GameEngine.Commands.Advance;
import team14.warzone.GameEngine.Commands.Deploy;
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
        int l_ExpectedNumberOfOrders = 2;
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
        l_CountryList.sort(Comparator.comparing(Country::getD_NumberOfArmies));
        l_WeakestCountry = l_CountryList.get(0);
        return l_WeakestCountry;
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
}
