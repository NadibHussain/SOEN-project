package team14.warzone.GameEngine;

import team14.warzone.Console.Command;
import team14.warzone.MapModule.Country;

import java.util.ArrayList;

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

    public Player() {
    }

    public Player(String d_Name, int d_TotalNumberOfArmies, ArrayList<Country> d_CountriesOwned,
                  ArrayList<Command> d_OrderList) {
        this.d_Name = d_Name;
        this.d_TotalNumberOfArmies = d_TotalNumberOfArmies;
        this.d_CountriesOwned = d_CountriesOwned;
        this.d_OrderList = d_OrderList;
    }

    public void issueOrder(Command p_Command) {
        d_OrderList.add(p_Command);
    }

    public void nextOrder() {
        d_OrderList.get(d_OrderList.size() - 1).execute();
        // remove the command after execution
    }

    public void setD_Name(String d_Name) {
        this.d_Name = d_Name;
    }

    public void setD_TotalNumberOfArmies(int d_TotalNumberOfArmies) {
        this.d_TotalNumberOfArmies = d_TotalNumberOfArmies;
    }

    public void setD_CountriesOwned(ArrayList<Country> d_CountriesOwned) {
        this.d_CountriesOwned = d_CountriesOwned;
    }

    public void setD_OrderList(ArrayList<Command> d_OrderList) {
        this.d_OrderList = d_OrderList;
    }

    public void addCountryOwned(Country p_Country){
        this.d_CountriesOwned.add(p_Country);
    }

    public String getD_Name() {
        return d_Name;
    }

    public int getD_TotalNumberOfArmies() {
        return d_TotalNumberOfArmies;
    }

    public ArrayList<Country> getD_CountriesOwned() {
        return d_CountriesOwned;
    }

    public ArrayList<Command> getD_OrderList() {
        return d_OrderList;
    }
}
