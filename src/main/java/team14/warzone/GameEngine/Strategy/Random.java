package team14.warzone.GameEngine.Strategy;

import team14.warzone.Console.Console;
import team14.warzone.GameEngine.Commands.Advance;
import team14.warzone.GameEngine.Commands.Deploy;
import team14.warzone.GameEngine.GameEngine;
import team14.warzone.GameEngine.Player;
import team14.warzone.MapModule.Country;
import team14.warzone.Utils.Randomizer;

import java.util.ArrayList;

public class Random implements Behavior {
    @Override
    public void issueOrder(GameEngine p_GE, Player p_Player) {
        // check if already deployed all undeployed armies
        int l_ArmiesLeftToDeploy = p_Player.getD_TotalNumberOfArmies() - p_Player.getD_ArmiesOrderedToBeDeployed();
        if (l_ArmiesLeftToDeploy != 0) {
            // deploy to random country
            // select random country from countries owned list
            int l_RandomCountryIndex = Randomizer.generateRandomNumber(0, p_Player.getD_CountriesOwned().size());
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

        // attack random neighbor or move (minimum one of each)
        int l_ExpectedNumberOfOrders = Randomizer.generateRandomNumber(3, 7);
        if (p_Player.getD_OrderList().size() < l_ExpectedNumberOfOrders) {
            // randomly select either attack enemy or move army between owned country
            switch (Randomizer.generateRandomNumber(0, 2)) {
                case 0:
                    attackNeighbor(p_GE, p_Player);
                    break;
                case 1:
                    moveArmy(p_GE, p_Player);
                    break;
            }
        }
    }

    private void attackNeighbor(GameEngine p_GE, Player p_Player) {
        // randomly keep selecting country until enemy neighbor found
        ArrayList<Country> l_CountriesOwned = p_Player.getD_CountriesOwned();
        Country l_AttackFrom = null;
        Country l_NeighborToAttack = null;
        boolean l_Flag = false;
        while (!l_Flag) {
            int l_RandomCountryIndex = Randomizer.generateRandomNumber(0, p_Player.getD_CountriesOwned().size());
            ArrayList<Country> l_NeighborList = l_CountriesOwned.get(l_RandomCountryIndex).getD_Neighbours();
            for (Country l_Country : l_NeighborList) {
                if (!l_Country.getD_CurrentOwner().equals(p_Player.getD_Name())) {
                    l_NeighborToAttack = l_Country;
                    l_AttackFrom = l_CountriesOwned.get(l_RandomCountryIndex);
                    l_Flag = true;
                    break;
                }
            }
        }

        // issue advance order
        if (l_AttackFrom != null && l_NeighborToAttack != null) {
            int l_NumOfArmiesToAttackWith = l_AttackFrom.getD_NumberOfArmies() - 1;
            Advance l_Advance = new Advance(l_AttackFrom.getD_CountryID(), l_NeighborToAttack.getD_CountryID(),
                    l_NumOfArmiesToAttackWith, p_GE);
            // add order to order list
            p_Player.getD_OrderList().add(l_Advance);
            Console.displayMsg(p_Player.getD_Name() + " issued: advance (attack enemy) " + l_AttackFrom.getD_CountryID() + " " + l_NeighborToAttack.getD_CountryID() + " " + l_NumOfArmiesToAttackWith);
            // write to log
            p_GE.getD_LogEntryBuffer().setD_log(p_Player.getD_Name() + " issued advance command");
            p_GE.getD_LogEntryBuffer().notifyObservers(p_GE.getD_LogEntryBuffer());
        }
    }

    private void moveArmy(GameEngine p_GE, Player p_Player) {

    }
}
