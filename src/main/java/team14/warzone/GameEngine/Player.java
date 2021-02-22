package team14.warzone.GameEngine;
import team14.warzone.Console.Command;
import team14.warzone.MapModule.Country;

import java.util.ArrayList;

public class Player {
    private String d_Name;
    private int d_TotalNumberOfArmies;
    private ArrayList<Country> d_CountriesOwned;
    private ArrayList<Command> d_OrderList;

    public Player(String d_Name, int d_TotalNumberOfArmies, ArrayList<Country> d_CountriesOwned, ArrayList<Command> d_OrderList) {
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
