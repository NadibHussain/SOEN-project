package team14.warzone.GameEngine.Strategy;

import team14.warzone.Console.Console;
import team14.warzone.GameEngine.GameEngine;
import team14.warzone.GameEngine.Player;
import team14.warzone.MapModule.Country;

import java.util.ArrayList;
import java.util.List;

/**
 * This class implements the cheater strategy
 * A cheater computer player strategy whose issueOrder() method conquers all the immediate neighboring enemy countries,
 * and then doubles the number of armies on its countries that have enemy neighbors.
 *
 * @author Zeina
 * @version 1.0
 */
public class Cheater implements Behavior {
    
    /** 
     * @param p_GE GameEngine
     * @param p_Player Player
     */
    @Override
    public void issueOrder(GameEngine p_GE, Player p_Player) {
        //conquer all the immediate neighboring enemy countries
        List<Country> l_CountriesOwned = p_Player.getD_CountriesOwned();
        List<Country> l_ConqueredCountries = new ArrayList<>();
        for (Country l_Country : l_CountriesOwned){
            List<Country> l_CountryNeighbors = l_Country.getD_Neighbours();
            for (Country l_NeighborCountry : l_CountryNeighbors){
                if (l_NeighborCountry.getD_CurrentOwner() != p_Player.getD_Name()){
                    //change the owner of the neighbor country, add it to current player countries list
                    // and remove it from the old owner list
                    p_GE.findPlayer(l_NeighborCountry.getD_CurrentOwner()).removeCountryOwned(l_NeighborCountry);
                    l_NeighborCountry.setD_CurrentOwner(p_Player.getD_Name());
                    l_ConqueredCountries.add(l_NeighborCountry);
                }
            }
        }
        //add conquered countries to player countries owned list
        for (Country l_Country : l_ConqueredCountries)
            p_Player.addCountryOwned(l_Country);
        
        //double the number of armies on its countries that have enemy neighbors
        l_CountriesOwned = p_Player.getD_CountriesOwned();//update after changes made in previous step
        //find countries he owned that have enemy neighbors
        for (Country l_Country : l_CountriesOwned){
            List<Country> l_CountryNeighbors = l_Country.getD_Neighbours();
            for (Country l_NeighborCountry : l_CountryNeighbors){
                //check if this country has an enemy neighbor
                if (l_NeighborCountry.getD_CurrentOwner() != p_Player.getD_Name()){
                    l_Country.setD_NumberOfArmies(l_Country.getD_NumberOfArmies() * 2); // double armies
                    break;
                }
            }
        }
        Console.displayMsg(p_Player.getD_Name() + ": pass");
        p_GE.setD_PlayerPassed(true);
    }
    /**
     * returns the name of behavior
     */
    @Override
    public String toString(){
        return "cheater";
    }
}
