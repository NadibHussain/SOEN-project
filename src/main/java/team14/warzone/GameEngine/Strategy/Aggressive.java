package team14.warzone.GameEngine.Strategy;

import team14.warzone.Console.Console;
import team14.warzone.GameEngine.Commands.Advance;
import team14.warzone.GameEngine.Commands.Deploy;
import team14.warzone.GameEngine.GameEngine;
import team14.warzone.GameEngine.Player;
import team14.warzone.MapModule.Country;
import team14.warzone.Utils.Randomizer;

import java.util.List;

/**
 * This class implements the aggressive strategy
 * Aggressive computer player strategy focuses on centralization of forces and then attack
 *
 * @author Zeina
 * @version 1.0
 */
public class Aggressive implements Behavior {
    int l_ExpectedNumberOfOrders = Randomizer.generateRandomNumber(1, 5);
    
    /** 
     * @param p_GE
     * @param p_Player
     */
    @Override
    public void issueOrder(GameEngine p_GE, Player p_Player) {
        // check if already deployed all un-deployed armies
        int l_ArmiesLeftToDeploy = p_Player.getD_TotalNumberOfArmies() - p_Player.getD_ArmiesOrderedToBeDeployed();
        if (l_ArmiesLeftToDeploy > 0) {
            //deploy on its strongest country
            int l_StrongestCountryIndex = findStrongestCountry(p_Player.getD_CountriesOwned());
            if (l_StrongestCountryIndex != -1) {
                String l_Country = p_Player.getD_CountriesOwned().get(l_StrongestCountryIndex).getD_CountryID();

                // instantiate deploy order
                Deploy l_Deploy = new Deploy(l_Country, l_ArmiesLeftToDeploy, p_GE);
                Console.displayMsg(p_Player.getD_Name() + " issued: deploy " + l_Country + " " + l_ArmiesLeftToDeploy);

                // add deploy to player's order list
                p_Player.getD_OrderList().add(l_Deploy);

                // write to log
                p_GE.getD_LogEntryBuffer().setD_log(p_Player.getD_Name() + " issued deploy command");
                p_GE.getD_LogEntryBuffer().notifyObservers(p_GE.getD_LogEntryBuffer());
            }
        } else if (p_Player.getD_OrderList().size() < l_ExpectedNumberOfOrders) {
            //first use cards if available
            //    public String TYPES[] = {d_Bomb, d_Blockade, d_Airlift, d_Diplomacy};
            //Bomb the strongest country of his neighbors
            //Blockade country ?
            //AirLift move armies to his strongest country if it is not adjacent
            //Diplomacy with the owner of the strongest neighbor
            List<Country> l_CountriesOwnedByPlayer = p_Player.getD_CountriesOwned();
            //always attack with its strongest country
            int l_StrongestCountryIndex = findStrongestCountry(l_CountriesOwnedByPlayer);
            Country l_StrongestCountry = null;
            if (l_StrongestCountryIndex != -1)
                l_StrongestCountry = l_CountriesOwnedByPlayer.get(l_StrongestCountryIndex);
            //select one of neighbor enemies countries as a destination to attack,
            // looking for a country that is weaker than the player's strongest country
            Country l_CountryToAttack = null;
            for (Country l_Country : l_CountriesOwnedByPlayer) {
                for (Country l_CountryNeighbor : l_Country.getD_Neighbours()) {
                    if (!l_CountryNeighbor.getD_CurrentOwner().equals(p_Player.getD_Name())
                            && l_StrongestCountry.getD_NumberOfArmies() >= l_CountryNeighbor.getD_NumberOfArmies()) {
                        l_CountryToAttack = l_CountryNeighbor;
                        break;
                    }
                }
            }
            //create advance order
            if (l_CountryToAttack != null && l_StrongestCountry != null) {
                Country l_CountryAttackFrom = l_StrongestCountry;
                int l_NumOfArmiesToAttackWith = l_CountryToAttack.getD_NumberOfArmies() - 1;
                Advance l_Advance = new Advance(l_CountryToAttack.getD_CountryID(), l_CountryAttackFrom.getD_CountryID(),
                        l_NumOfArmiesToAttackWith, p_GE);
                // add order to order list
                p_Player.getD_OrderList().add(l_Advance);
                Console.displayMsg(p_Player.getD_Name() + " issued: advance (attack enemy) " + l_CountryAttackFrom.getD_CountryID() + " " + l_CountryToAttack.getD_CountryID() + " " + l_NumOfArmiesToAttackWith);
                // write to log
                p_GE.getD_LogEntryBuffer().setD_log(p_Player.getD_Name() + " issued advance command");
                p_GE.getD_LogEntryBuffer().notifyObservers(p_GE.getD_LogEntryBuffer());
            }
            //moves its armies in order to maximize aggregation of forces in one country
            //looking for a country that has the biggest number of armies after the strongest country
            else {
                int l_SecondStrongestCountryIndex = findSecondStrongestCountry(l_CountriesOwnedByPlayer, l_StrongestCountryIndex);
                Country l_SecondStrongestCountry = l_CountriesOwnedByPlayer.get(l_StrongestCountryIndex);
                int l_NumOfArmiesToAttackWith = l_SecondStrongestCountry.getD_NumberOfArmies() - 1;
                Advance l_Advance = new Advance(l_SecondStrongestCountry.getD_CountryID(), l_StrongestCountry.getD_CountryID(),
                        l_NumOfArmiesToAttackWith, p_GE);
                // add order to order list
                p_Player.getD_OrderList().add(l_Advance);
                Console.displayMsg(p_Player.getD_Name() + " issued: advance (attack enemy) " + l_SecondStrongestCountry.getD_CountryID() + " " + l_StrongestCountry.getD_CountryID() + " " + l_NumOfArmiesToAttackWith);
                // write to log
                p_GE.getD_LogEntryBuffer().setD_log(p_Player.getD_Name() + " issued advance command");
                p_GE.getD_LogEntryBuffer().notifyObservers(p_GE.getD_LogEntryBuffer());
            }
        } else {//if player has reached the maximum number of issued commands
            // pass
            Console.displayMsg(p_Player.getD_Name() + ": pass");
            p_GE.setD_PlayerPassed(true);
        }
    }

    /**
     * A method to find the index of country with the biggest number of armies (the strongest country)
     *
     * @param p_CountriesList a list of countries
     * @return index of country with the biggest number of armies
     */
    public int findStrongestCountry(List<Country> p_CountriesList) {
        int l_Max = -1;
        int l_MaxIndex = -1;
        for (int l_Index = 0; l_Index < p_CountriesList.size(); l_Index++) {
            if (p_CountriesList.get(l_Index).getD_NumberOfArmies() > l_Max) {
                l_Max = p_CountriesList.get(l_Index).getD_NumberOfArmies();
                l_MaxIndex = l_Index;
            }
        }
        return l_MaxIndex;
    }

    /**
     * A method to find the index of country with the second biggest number of armies (after the strongest country)
     * used for armies aggregation before attack
     *
     * @param p_CountriesList         a list of countries
     * @param p_StrongestCountryIndex index of the strongest country
     * @return index of country with the second biggest number of armies
     */
    public int findSecondStrongestCountry(List<Country> p_CountriesList, int p_StrongestCountryIndex) {
        int l_Max = -1;
        int l_MaxIndex = -1;
        for (int l_Index = 0; l_Index < p_CountriesList.size(); l_Index++) {
            if (p_CountriesList.get(l_Index).getD_NumberOfArmies() > l_Max && l_Index != p_StrongestCountryIndex) {
                l_Max = p_CountriesList.get(l_Index).getD_NumberOfArmies();
                l_MaxIndex = l_Index;
            }
        }
        return l_MaxIndex;
    }
}
