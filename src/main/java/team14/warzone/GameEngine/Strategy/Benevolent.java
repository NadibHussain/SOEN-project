package team14.warzone.GameEngine.Strategy;

import team14.warzone.Console.Console;
import team14.warzone.GameEngine.Card;
import team14.warzone.GameEngine.Commands.Advance;
import team14.warzone.GameEngine.Commands.Deploy;
import team14.warzone.GameEngine.Commands.Order;
import team14.warzone.GameEngine.GameEngine;
import team14.warzone.GameEngine.Player;
import team14.warzone.MapModule.Country;

import java.util.ArrayList;
import java.util.Objects;

/**
 * Class implements behavior for benevolent player
 */
public class Benevolent implements Behavior {
    /**
     * Holds a list of weak countries for current player
     */
    private ArrayList<Country> d_WeakCountryList = new ArrayList<>();

    /**
     * Order issuing strategy for benevolent player
     *
     * @param p_GE     Game Engine object
     * @param p_Player Player object
     */
    @Override
    public void issueOrder(GameEngine p_GE, Player p_Player) {
        int l_ExpectedNumberOfOrders = 3;
        int l_ArmiesLeftToDeploy = p_Player.getD_TotalNumberOfArmies() - p_Player.getD_ArmiesOrderedToBeDeployed();
        // check if already advance order issued
        boolean l_AlreadyAdvanced = false;
        for (Order l_Order : p_Player.getD_OrderList()) {
            if (l_Order instanceof Advance) {
                l_AlreadyAdvanced = true;
                break;
            }
        }

        // deploy to weakest
        if (l_ArmiesLeftToDeploy > 0 && p_Player.getD_CountriesOwned().size() > 0) {
            // find weakest country
            Country l_Country = BehaviorUtilities.findWeakestCountry(p_Player);
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
        else if (!p_Player.getCardList().isEmpty() && !p_Player.getCardList().get(0).isD_Used() && p_Player.getD_CountriesOwned().size() != 0) {
            Card l_Card = p_Player.getCardList().get(0);
            switch (l_Card.getD_CardType()) {
                case "blockade":
                    BehaviorUtilities.issueBlockade(p_GE, p_Player, l_Card);
                    break;
                case "airlift":
                    BehaviorUtilities.issueAirlift(p_GE, p_Player, l_Card);
                    break;
                case "diplomacy":
                    BehaviorUtilities.issueDiplomacy(p_GE, p_Player, l_Card);
                    break;

                default:
                    p_Player.removeCard(l_Card);
                    break;
            }
        }

        // advance from strong to weak (self)
        else if (p_Player.getD_OrderList().size() < l_ExpectedNumberOfOrders && !l_AlreadyAdvanced && p_Player.getD_CountriesOwned().size() > 0) {
            // find if any weak country has strong neighbor
            ArrayList<Country> l_WeakCountries = BehaviorUtilities.findWeakerCountriesWithStrongNeighbor(p_Player);
            if (l_WeakCountries.size() > 0) {
                Country l_DestinationCountry = l_WeakCountries.get(0);
                Country l_SourceCountry = BehaviorUtilities.findStrongNeighbor(l_DestinationCountry);
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

    /**
     * @return name of behavior
     */
    @Override
    public String toString() {
        return "benevolent";
    }
}
