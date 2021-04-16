package team14.warzone.GameEngine.Strategy;

import team14.warzone.Console.Console;
import team14.warzone.GameEngine.Commands.Advance;
import team14.warzone.GameEngine.Commands.Airlift;
import team14.warzone.GameEngine.Commands.Bomb;
import team14.warzone.GameEngine.Commands.Deploy;
import team14.warzone.GameEngine.GameEngine;
import team14.warzone.GameEngine.Player;
import team14.warzone.MapModule.Country;

import java.util.Collections;
import java.util.List;

/**
 * This class implements the aggressive strategy
 * Aggressive computer player strategy focuses on centralization of forces and then attack
 *
 * @author Zeina
 * @version 1.0
 */
public class Aggressive implements Behavior {
    /**
     * Number of orders expected to be issued
     */
    int l_ExpectedNumberOfOrders = 3;

    /**
     * Issue order
     *
     * @param p_GE     Context game engine
     * @param p_Player Player obejct
     */
    @Override
    public void issueOrder(GameEngine p_GE, Player p_Player) {
        // check if already deployed all un-deployed armies
        int l_ArmiesLeftToDeploy = p_Player.getD_TotalNumberOfArmies() - p_Player.getD_ArmiesOrderedToBeDeployed();
        Country l_StrongestCountry = findStrongestCountry(p_Player.getD_CountriesOwned(), p_Player);
        //if the strongest country was used in previous turn then choose the second strongest country
        if (l_StrongestCountry != null && l_StrongestCountry.isD_UsedCountry() && p_Player.getD_CountriesOwned().size() > 1) {
            int l_NewStrongestCountryIndex = findSecondStrongestCountry(p_Player.getD_CountriesOwned(), l_StrongestCountry, false);
            l_StrongestCountry = p_Player.getD_CountriesOwned().get(l_NewStrongestCountryIndex);
        }
        //deploy on its strongest country
        if (l_ArmiesLeftToDeploy > 0 && l_StrongestCountry != null) {
            String l_StrongestCountryName = l_StrongestCountry.getD_CountryID();

            // instantiate deploy order
            Deploy l_Deploy = new Deploy(l_StrongestCountryName, l_ArmiesLeftToDeploy, p_GE);
            Console.displayMsg(p_Player.getD_Name() + " issued: deploy " + l_StrongestCountryName + " " + l_ArmiesLeftToDeploy);

            // add deploy to player's order list
            p_Player.getD_OrderList().add(l_Deploy);

            // write to log
            p_GE.getD_LogEntryBuffer().setD_log(p_Player.getD_Name() + " issued deploy command");
            p_GE.getD_LogEntryBuffer().notifyObservers(p_GE.getD_LogEntryBuffer());

            // check if player can issue more orders in current round
        } else if (p_Player.getD_OrderList().size() < l_ExpectedNumberOfOrders && p_Player.getD_CountriesOwned().size() > 0) {
            //first use bomb card if available
            List<Country> l_CountriesOwnedByPlayer = p_Player.getD_CountriesOwned();
            //bomb an enemy country
            if (p_Player.hasCard("bomb")) {// check if player has the Bomb card
                //select a country that belongs to one of his enemies to bomb, enemy should not be a diplomatic player
                Country l_CountryToAttack = null;
                for (Country l_Country : l_CountriesOwnedByPlayer) {
                    for (Country l_CountryNeighbor : l_Country.getD_Neighbours()) {
                        if (!l_CountryNeighbor.getD_CurrentOwner().equals(p_Player.getD_Name())
                                && !p_Player.isDiplomaticPlayer
                                (p_Player, p_GE.findPlayer(l_CountryNeighbor.getD_CurrentOwner()))) {
                            l_CountryToAttack = l_CountryNeighbor;
                            break;
                        }
                    }
                }
                Bomb l_BombOrder = new Bomb(l_CountryToAttack.getD_CountryID(), p_GE);
                p_Player.getD_OrderList().add(l_BombOrder);
                p_Player.setCardUsed("bomb");
                Console.displayMsg(p_Player.getD_Name() + " issued: bomb on " + l_CountryToAttack.getD_CountryID());
                p_GE.getD_LogEntryBuffer().setD_log(p_Player.getD_Name() + " issued bomb command");
                p_GE.getD_LogEntryBuffer().notifyObservers(p_GE.getD_LogEntryBuffer());
            } else {//attack with its strongest country
                if (l_StrongestCountry != null && l_StrongestCountry.getD_NumberOfArmies() != 0) {
                    //select one of neighbor enemies countries as a destination to attack, enemy should not be a
                    // diplomatic player
                    // looking for an adjacent country that is weaker than the player's strongest country
                    Country l_CountryToAttack = null;
                    for (Country l_CountryNeighbor : l_StrongestCountry.getD_Neighbours()) {
                        if (!l_CountryNeighbor.getD_CurrentOwner().equals(p_Player.getD_Name())
                                && !p_Player.isDiplomaticPlayer(p_Player,
                                p_GE.findPlayer(l_CountryNeighbor.getD_CurrentOwner()))
                                && l_StrongestCountry.getD_NumberOfArmies() >= l_CountryNeighbor.getD_NumberOfArmies()) {
                            l_CountryToAttack = l_CountryNeighbor;
                            break;
                        }
                    }
                    //create advance order
                    if (l_CountryToAttack != null && l_StrongestCountry != null) {
                        Country l_CountryAttackFrom = l_StrongestCountry;
                        int l_NumOfArmiesToAttackWith = l_CountryAttackFrom.getD_NumberOfArmies() - 1;
                        advanceArmies(p_Player, l_CountryAttackFrom.getD_CountryID(),
                                l_CountryToAttack.getD_CountryID(),
                                l_NumOfArmiesToAttackWith, p_GE, "(attack enemy) ");
                        //mark countries as used
                        l_CountryAttackFrom.setD_UsedCountry(true);
                    }
                    //if the aggressive player doesn't find a weaker country to attack then he need to moves its armies
                    //in order to maximize aggregation of forces in one country
                    //looking for a country that has the biggest number of armies after the strongest country
                    else {
                        int l_SecondStrongestCountryIndex = findSecondStrongestCountry(l_CountriesOwnedByPlayer, l_StrongestCountry, false);
                        Country l_SecondStrongestCountry = null;
                        if(l_SecondStrongestCountryIndex != -1)
                            l_SecondStrongestCountry = l_CountriesOwnedByPlayer.get(l_SecondStrongestCountryIndex);
                        if (l_StrongestCountry == null || l_SecondStrongestCountryIndex == -1 || l_SecondStrongestCountry == null || l_StrongestCountry.getD_NumberOfArmies() == 0) {
                            // pass
                            Console.displayMsg(p_Player.getD_Name() + ": pass");
                            p_GE.setD_PlayerPassed(true);
                        } else {
                            int l_NumOfArmiesToMove = l_SecondStrongestCountry.getD_NumberOfArmies() - 1;
                            //check if destination country and source country are not adjacent then use airlift card
                            // if available
                            if (!l_SecondStrongestCountry.getD_Neighbours().contains(l_StrongestCountry)) {
                                if (p_Player.hasCard("airlift")) {// check if player has the Airlift card
                                    Airlift l_AirliftOrder = new Airlift(l_SecondStrongestCountry.getD_CountryID(),
                                            l_StrongestCountry.getD_CountryID(),
                                            l_NumOfArmiesToMove, p_GE);
                                    p_Player.getD_OrderList().add(l_AirliftOrder);
                                    p_Player.setCardUsed("airlift");
                                    Console.displayMsg(p_Player.getD_Name() + " issued: airlift from " + l_SecondStrongestCountry.getD_CountryID() + " " +
                                            "to " + l_StrongestCountry.getD_CountryID());
                                    p_GE.getD_LogEntryBuffer().setD_log(p_Player.getD_Name() + " issued airlift " +
                                            "command");
                                    p_GE.getD_LogEntryBuffer().notifyObservers(p_GE.getD_LogEntryBuffer());
                                } else {//no airlift card available, so we need to look for an adjacent country with the strongest country
                                    l_SecondStrongestCountryIndex = findSecondStrongestCountry(l_CountriesOwnedByPlayer, l_StrongestCountry, true);
                                    if (l_StrongestCountry == null || l_SecondStrongestCountryIndex == -1 || l_StrongestCountry.getD_NumberOfArmies() == 0) {
                                        // pass
                                        Console.displayMsg(p_Player.getD_Name() + ": pass");
                                        p_GE.setD_PlayerPassed(true);
                                    }
                                    else{
                                        l_SecondStrongestCountry = l_CountriesOwnedByPlayer.get(l_SecondStrongestCountryIndex);
                                        l_NumOfArmiesToMove = l_SecondStrongestCountry.getD_NumberOfArmies() - 1;
                                        advanceArmies(p_Player, l_SecondStrongestCountry.getD_CountryID(), l_StrongestCountry.getD_CountryID(),
                                                l_NumOfArmiesToMove, p_GE, "(aggregate armies) ");
                                    }
                                }
                            } else {//destination country and source country are adjacent, create advance order
                                advanceArmies(p_Player, l_SecondStrongestCountry.getD_CountryID(),
                                        l_StrongestCountry.getD_CountryID(),
                                        l_NumOfArmiesToMove, p_GE, "(aggregate armies) ");
                            }
                        }
                    }
                } else {//player has no armies to move or attack
                    // pass
                    Console.displayMsg(p_Player.getD_Name() + ": pass");
                    p_GE.setD_PlayerPassed(true);
                }
            }
        } else { //if player has reached the maximum number of issued commands
            // pass
            Console.displayMsg(p_Player.getD_Name() + ": pass");
            p_GE.setD_PlayerPassed(true);
        }
    }

    /**
     * A method to find the index of country with the biggest number of armies (the strongest country)
     *
     * @param p_CountriesList a list of countries
     * @param p_Player        Player object
     * @return index of country with the biggest number of armies
     */
    public Country findStrongestCountry(List<Country> p_CountriesList, Player p_Player) {
        Collections.sort(p_CountriesList, (Country p_C1, Country p_C2) -> p_C1.getD_NumberOfArmies() - p_C2.getD_NumberOfArmies());
        Collections.sort(p_CountriesList,
                (Country p_C1, Country p_C2) -> p_C1.getD_NumberOfArmies() - p_C2.getD_NumberOfArmies());
        Country l_StrongestCountry = null;
        for (int l_Index = p_CountriesList.size() - 1; l_Index >= 0; l_Index--) {
            if (!p_Player.getD_CountriesOwned().containsAll(p_CountriesList.get(l_Index).getD_Neighbours())) {
                l_StrongestCountry = p_CountriesList.get(l_Index);
                break;
            }
        }
        return l_StrongestCountry;
    }

    /**
     * A method to find the index of country with the second biggest number of armies (after the strongest country)
     * used for armies aggregation before attack
     *
     * @param p_CountriesList    a list of countries
     * @param p_StrongestCountry the strongest country
     * @param p_CountriesList    a list of countries
     * @param p_StrongestCountry the strongest country
     * @param adjacentFlag       adjacent flag
     * @return index of country with the second biggest number of armies
     */
    public int findSecondStrongestCountry(List<Country> p_CountriesList, Country p_StrongestCountry,
                                          boolean adjacentFlag) {
        int l_Max = -1;
        int l_MaxIndex = -1;
        for (int l_Index = 0; l_Index < p_CountriesList.size(); l_Index++) {
            if (adjacentFlag) {
                if (p_CountriesList.get(l_Index).getD_NumberOfArmies() > l_Max && !p_CountriesList.get(l_Index).getD_CountryID().equals(p_StrongestCountry.getD_CountryID())
                        && p_CountriesList.get(l_Index).getD_Neighbours().contains(p_StrongestCountry)) {
                    l_Max = p_CountriesList.get(l_Index).getD_NumberOfArmies();
                    l_MaxIndex = l_Index;
                }
            } else if (p_CountriesList.get(l_Index).getD_NumberOfArmies() > l_Max && !p_CountriesList.get(l_Index).getD_CountryID().equals(p_StrongestCountry.getD_CountryID())) {
                l_Max = p_CountriesList.get(l_Index).getD_NumberOfArmies();
                l_MaxIndex = l_Index;
            }
        }
        return l_MaxIndex;
    }

    /**
     * A method to move armies from a country to another using the advance order
     *
     * @param p_Player      who owns the countries
     * @param p_SrcCountry  source of armies
     * @param p_DestCountry destination of armies
     * @param p_NumOfArmies number of armies to move
     * @param p_GE          an object of the GameEngine
     * @param p_Msg         msg
     */
    public void advanceArmies(Player p_Player, String p_SrcCountry, String p_DestCountry, int p_NumOfArmies,
                              GameEngine p_GE, String p_Msg) {
        Advance l_Advance = new Advance(p_SrcCountry, p_DestCountry, p_NumOfArmies, p_GE);
        // add order to order list
        p_Player.getD_OrderList().add(l_Advance);
        Console.displayMsg(p_Player.getD_Name() + " issued: advance " + p_Msg + p_SrcCountry + " " + p_DestCountry +
                " " + p_NumOfArmies);
        // write to log
        p_GE.getD_LogEntryBuffer().setD_log(p_Player.getD_Name() + " issued advance command");
        p_GE.getD_LogEntryBuffer().notifyObservers(p_GE.getD_LogEntryBuffer());
    }

    /**
     * Convert to string
     *
     * @return name of behavior
     */
    @Override
    public String toString() {
        return "aggressive";
    }
}
