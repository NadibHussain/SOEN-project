package team14.warzone.GameEngine;

import team14.warzone.Console.Command;
import team14.warzone.MapModule.Country;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * Class implements the player model
 *
 * @author Anagh
 * @version 1.0
 */
public class Player {
    private String d_Name;
    private int d_TotalNumberOfArmies;
    private ArrayList<Country> d_CountriesOwned;
    private ArrayList<Command> d_OrderList;

    /**
     * public player method
     */
    public Player() {
    }

    /**
     * player method
     * @param d_Name String type Name
     * @param d_TotalNumberOfArmies int type Total Number of Armies
     * @param d_CountriesOwned ArrayList of countries owned
     * @param d_OrderList ArrayList of commands
     */
    public Player(String d_Name, int d_TotalNumberOfArmies, ArrayList<Country> d_CountriesOwned,
                  ArrayList<Command> d_OrderList) {
        this.d_Name = d_Name;
        this.d_TotalNumberOfArmies = d_TotalNumberOfArmies;
        this.d_CountriesOwned = d_CountriesOwned;
        this.d_OrderList = d_OrderList;
    }

    /**
     * Player method
     * @param p_Name String type name
     */
    public Player(String p_Name) {
        this(p_Name, 20, new ArrayList<Country>(Arrays.asList()), new ArrayList<Command>(Arrays.asList()));
    }

    /**
     * Issue order method
     * @param p_Command Command type parameter
     */
    public void issueOrder(Command p_Command) {
        d_OrderList.add(p_Command);
    }

    /**
     * Next order method
     */
    public void nextOrder() {
        d_OrderList.get(d_OrderList.size() - 1).execute();
        // remove the command after execution
    }

    /**
     * Set Name
     * @param d_Name String type Name
     */
    public void setD_Name(String d_Name) {
        this.d_Name = d_Name;
    }

    /**
     * Set number of armies
     * @param d_TotalNumberOfArmies int type number of armies
     */
    public void setD_TotalNumberOfArmies(int d_TotalNumberOfArmies) {
        this.d_TotalNumberOfArmies = d_TotalNumberOfArmies;
    }

    /**
     * Set Countries Owned
     * @param d_CountriesOwned ArrayList of Countries
     */
    public void setD_CountriesOwned(ArrayList<Country> d_CountriesOwned) {
        this.d_CountriesOwned = d_CountriesOwned;
    }

    /**
     * Set order list
     * @param d_OrderList ArrayList of commands
     */
    public void setD_OrderList(ArrayList<Command> d_OrderList) {
        this.d_OrderList = d_OrderList;
    }

    /**
     * Adding owned country
     * @param p_Country add a country to countries owned
     */
    public void addCountryOwned(Country p_Country) {
        this.d_CountriesOwned.add(p_Country);
    }

    /**
     * get name
     * @return String type name
     */
    public String getD_Name() {
        return d_Name;
    }

    /**
     * get number of armies
     * @return int type number of armies
     */
    public int getD_TotalNumberOfArmies() {
        return d_TotalNumberOfArmies;
    }

    /**
     * ArrayList of countries
     * @return array of owned countries
     */
    public ArrayList<Country> getD_CountriesOwned() {
        return d_CountriesOwned;
    }

    /**
     * ArrayList of Commands
     * @return array of commands given
     */
    public ArrayList<Command> getD_OrderList() {
        return d_OrderList;
    }
}
