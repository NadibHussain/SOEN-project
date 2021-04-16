package team14.warzone.GameEngine.Strategy;

import team14.warzone.Console.Console;
import team14.warzone.GameEngine.Card;
import team14.warzone.GameEngine.Commands.Advance;
import team14.warzone.GameEngine.Commands.Deploy;
import team14.warzone.GameEngine.Commands.Order;
import team14.warzone.GameEngine.GameEngine;
import team14.warzone.GameEngine.Player;
import team14.warzone.MapModule.Country;
import team14.warzone.Utils.Randomizer;

import java.util.ArrayList;
import java.util.Objects;

/**
 * Class implements behavior for random players
 */
public class Random implements Behavior {

    /**
     * @param p_GE     Game Engine
     * @param p_Player Player
     */
    @Override
    public void issueOrder(GameEngine p_GE, Player p_Player) {
//        int l_ExpectedNumberOfOrders = Randomizer.generateRandomNumber(1, 4);
        int l_ExpectedNumberOfOrders = 3;
        // check if already deployed all undeployed armies
        int l_ArmiesLeftToDeploy = p_Player.getD_TotalNumberOfArmies() - p_Player.getD_ArmiesOrderedToBeDeployed();
        // check if already advance order issued
        boolean l_AlreadyAdvanced = false;
        for (Order l_Order : p_Player.getD_OrderList()) {
            if (l_Order instanceof Advance) {
                l_AlreadyAdvanced = true;
                break;
            }
        }
        if (l_ArmiesLeftToDeploy > 0 && p_Player.getD_CountriesOwned().size() > 0) {
            // deploy to random country
            // select random country from countries owned list
            int l_RandomCountryIndex = Randomizer.generateRandomNumber(0, p_Player.getD_CountriesOwned().size() - 1);
            String l_Country = p_Player.getD_CountriesOwned().get(l_RandomCountryIndex).getD_CountryID();

            // instantiate deploy order
            Deploy l_Deploy = new Deploy(l_Country, l_ArmiesLeftToDeploy
                    , p_GE);
            Console.displayMsg(p_Player.getD_Name() + " issued: deploy " + l_Country + " " + l_ArmiesLeftToDeploy);

            // add deploy to player's order list
            p_Player.getD_OrderList().add(l_Deploy);

            // write to log
            p_GE.getD_LogEntryBuffer().setD_log(p_Player.getD_Name() + " issued deploy command");
            p_GE.getD_LogEntryBuffer().notifyObservers(p_GE.getD_LogEntryBuffer());
        }

        // play card if any
        else if (!p_Player.getCardList().isEmpty() && !p_Player.getCardList().get(p_Player.getCardList().size() - 1).isD_Used() && p_Player.getD_CountriesOwned().size() > 0) {
            Card l_Card = p_Player.getCardList().get(p_Player.getCardList().size() - 1);
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
                case "bomb":
                    BehaviorUtilities.issueBomb(p_GE, p_Player, l_Card);
                    break;

                default:
                    p_Player.removeCard(l_Card);
                    break;
            }
        }

        // attack random neighbor or move (minimum one of each)
        else if (p_Player.getD_OrderList().size() < l_ExpectedNumberOfOrders && !l_AlreadyAdvanced && p_Player.getD_CountriesOwned().size() > 0) {
            // randomly select either attack enemy or move army between owned country
            switch (Randomizer.generateRandomNumber(0, 1)) {
                case 0:
                    attackNeighbor(p_GE, p_Player);
                    break;
                case 1:
                    moveArmy(p_GE, p_Player);
                    break;
            }
        } else {
            // pass
            Console.displayMsg(p_Player.getD_Name() + ": pass");
            p_GE.setD_PlayerPassed(true);
        }
    }


    /**
     * Issue advance order on enemy neighbor
     *
     * @param p_GE     Game Engine
     * @param p_Player Player
     */
    private void attackNeighbor(GameEngine p_GE, Player p_Player) {
        // randomly keep selecting country until enemy neighbor found
        ArrayList<Country> l_CountriesOwned = p_Player.getD_CountriesOwned();
        Country l_AttackFrom = null;
        Country l_NeighborToAttack = null;
        boolean l_Flag = false;
        int l_Counter = 0;
        // check if any country has army
        for (Country l_Country : l_CountriesOwned) {
            if (l_Country.getD_NumberOfArmies() > 1) {
                l_Flag = true;
                break;
            }
        }
        while (l_Flag && l_Counter < 300) {
            int l_RandomCountryIndex = Randomizer.generateRandomNumber(0, p_Player.getD_CountriesOwned().size() - 1);
            ArrayList<Country> l_NeighborList = new ArrayList<>();
            if (l_CountriesOwned.size() > 0) {
                l_NeighborList = l_CountriesOwned.get(l_RandomCountryIndex).getD_Neighbours();
            }
            for (Country l_Country : l_NeighborList) {
                if (!l_Country.getD_CurrentOwner().equals(p_Player.getD_Name()) && l_CountriesOwned.get(l_RandomCountryIndex).getD_NumberOfArmies() > 1) {
                    l_NeighborToAttack = l_Country;
                    l_AttackFrom = l_CountriesOwned.get(l_RandomCountryIndex);
                    l_Flag = false;
                    break;
                }
            }
            l_Counter++;
        }

        // issue advance order
        if (Objects.nonNull(l_AttackFrom) && Objects.nonNull(l_NeighborToAttack) && l_AttackFrom.getD_NumberOfArmies() > 1) {
            int l_NumOfArmiesToAttackWith = l_AttackFrom.getD_NumberOfArmies() - 1;
            BehaviorUtilities.issueAdvance(p_GE, p_Player, l_AttackFrom, l_NeighborToAttack, l_NumOfArmiesToAttackWith);
        } else {
            Console.displayMsg(p_Player.getD_Name() + ": pass");
            p_GE.setD_PlayerPassed(true);
        }
    }


    /**
     * Move army between countries owned by self
     *
     * @param p_GE     Game Engine
     * @param p_Player Player
     */
    private void moveArmy(GameEngine p_GE, Player p_Player) {
        // randomly select country and neighbor owned by self
        ArrayList<Country> l_CountriesOwned = p_Player.getD_CountriesOwned();
        Country l_AttackFrom = null;
        Country l_NeighborCountry = null;
        boolean l_Flag = false;
        int l_Counter = 0;
        // check if any country has army
        for (Country l_Country : l_CountriesOwned) {
            if (l_Country.getD_NumberOfArmies() > 1) {
                l_Flag = true;
                break;
            }
        }
        // keep looping until a source country with armies and friendly neighbor is found
        while (l_Flag && l_Counter < 300) {
            int l_RandomCountryIndex = Randomizer.generateRandomNumber(0, p_Player.getD_CountriesOwned().size() - 1);
            ArrayList<Country> l_NeighborList = new ArrayList<>();
            if (l_CountriesOwned.size() != 0) {
                l_NeighborList = l_CountriesOwned.get(l_RandomCountryIndex).getD_Neighbours();
            }
            for (Country l_Country : l_NeighborList) {
                if (l_Country.getD_CurrentOwner().equals(p_Player.getD_Name()) && l_CountriesOwned.get(l_RandomCountryIndex).getD_NumberOfArmies() > 1) {
                    l_NeighborCountry = l_Country;
                    l_AttackFrom = l_CountriesOwned.get(l_RandomCountryIndex);
                    l_Flag = false;
                    break;
                }
            }
            l_Counter++;
        }
        // issue advance order
        if (Objects.nonNull(l_AttackFrom) && Objects.nonNull(l_NeighborCountry) && l_AttackFrom.getD_NumberOfArmies() > 1) {
            int l_NumOfArmiesToAttackWith = Randomizer.generateRandomNumber(1, l_AttackFrom.getD_NumberOfArmies() - 1);
            BehaviorUtilities.issueAdvance(p_GE, p_Player, l_AttackFrom, l_NeighborCountry, l_NumOfArmiesToAttackWith);
        } else {
            Console.displayMsg(p_Player.getD_Name() + ": pass");
            p_GE.setD_PlayerPassed(true);
        }
    }

    /**
     * Convert to string
     *
     * @return name of behavior
     */
    @Override
    public String toString() {
        return "random";
    }
}
