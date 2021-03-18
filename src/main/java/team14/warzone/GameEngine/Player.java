package team14.warzone.GameEngine;

import team14.warzone.GameEngine.Commands.Command;
import team14.warzone.MapModule.Country;

import java.util.ArrayList;
import java.util.Collections;

/**
 * Class implements the player model
 *
 * @author Anagh
 * @version 1.0
 */
public class Player implements Subject {
    /**
     * name of player
     */
    private String d_Name;
    /**
     * number of armies in players possession
     */
    private int d_TotalNumberOfArmies;
    /**
     * list of countries owned by player
     */
    private ArrayList<Country> d_CountriesOwned;
    /**
     * list of orders issued by player that has not been executed
     */
    private ArrayList<Command> d_OrderList;
    private ArrayList<Card> d_CardList;
    private ArrayList<Observer> d_ObserverList;

    /**
     * Default constructor that takes no params
     */
    public Player() {
        this.d_ObserverList = new ArrayList<Observer>();
    }

    /**
     * Constructor that takes all attributes as params
     *
     * @param d_Name                name of the player
     * @param d_TotalNumberOfArmies total number of armies
     * @param d_CountriesOwned      list of countries (Country objects) owned by the player
     * @param d_OrderList           list of orders the player has issued but has not executed yet
     */
    public Player(String d_Name, int d_TotalNumberOfArmies, ArrayList<Country> d_CountriesOwned,
                  ArrayList<Command> d_OrderList) {
        this();
        this.d_Name = d_Name;
        this.d_TotalNumberOfArmies = d_TotalNumberOfArmies;
        this.d_CountriesOwned = d_CountriesOwned;
        this.d_OrderList = d_OrderList;
        d_CardList = new ArrayList<>();
    }

    /**
     * Constructor that accepts playername and sets the other attributes with default values
     *
     * @param p_Name name of the player
     */
    public Player(String p_Name) {
        this(p_Name, 20, new ArrayList<Country>(Collections.emptyList()),
                new ArrayList<Command>(Collections.emptyList()));
    }

    /**
     * This method adds order to the list of orders
     *
     * @param p_Command command object to be stored
     */
    public void issueOrder(Command p_Command) {
        d_OrderList.add(p_Command);
    }

    /**
     * This method executes the first order in the order list
     * <ul>
     *     <li>Order list is maintained in a FIFO fasion</li>
     *     <li>Check if order list is empty</li>
     *     <li>Execute the order that was issued first</li>
     *     <li>After execution resize the list</li>
     * </ul>
     */
    public void nextOrder() {
        if (!d_OrderList.isEmpty()) {
            if (!d_OrderList.get(0).getD_Keyword().equals("pass"))
                d_OrderList.get(0).execute();//execute first order from the order list
            // remove the command after execution
            d_OrderList.remove(0);// remove first order from the order list
        }
    }

    /**
     * This method adds a country to the list of countries owned by the player
     *
     * @param p_Country country that is to be added
     */
    public void addCountryOwned(Country p_Country) {
        this.d_CountriesOwned.add(p_Country);
    }

    public void countryConquered (Country p_Country) {
        addCountryOwned(p_Country);
        notifyObserver();
    }

    /**
     * Getter method for name of the player
     *
     * @return name of the player
     */
    public String getD_Name() {
        return d_Name;
    }

    /**
     * Getter method for the total number of armies owned by the player
     *
     * @return total number of armies currently in players possession
     */
    public int getD_TotalNumberOfArmies() {
        return d_TotalNumberOfArmies;
    }

    /**
     * Getter method for the list of countries owned by the player
     *
     * @return list of countries owned
     */
    public ArrayList<Country> getD_CountriesOwned() {
        return d_CountriesOwned;
    }

    /**
     * Getter method for the list of orders issued by the player
     *
     * @return list of orders issued and not yet executed
     */
    public ArrayList<Command> getD_OrderList() {
        return d_OrderList;
    }

    /**
     * Setter method for the list of orders
     *
     * @param d_OrderList list or orders
     */
    public void setD_OrderList(ArrayList<Command> d_OrderList) {
        this.d_OrderList = d_OrderList;
    }

    /**
     * Setter method for the list of countries owned
     *
     * @param d_CountriesOwned list of countries owned
     */
    public void setD_CountriesOwned(ArrayList<Country> d_CountriesOwned) {
        this.d_CountriesOwned = d_CountriesOwned;
    }

    /**
     * Setter method for the number of armies
     *
     * @param d_TotalNumberOfArmies number of armies
     */
    public void setD_TotalNumberOfArmies(int d_TotalNumberOfArmies) {
        this.d_TotalNumberOfArmies = d_TotalNumberOfArmies;
    }

    /**
     * Setter method for the name of player
     *
     * @param d_Name name of player
     */
    public void setD_Name(String d_Name) {
        this.d_Name = d_Name;
    }



    public ArrayList<Observer> getObservers() {
        return d_ObserverList;
    }

    @Override
    public void register(Observer observingplayer) {
        d_ObserverList.add(observingplayer);
    }

    @Override
    public void unregister(Observer observingplayer) {
        d_ObserverList.remove(observingplayer);
    }

    public ArrayList<Card> getD_CardList() {
        return d_CardList;
    }
    public void setD_CardList(ArrayList<Card> d_CardList) {
        this.d_CardList = d_CardList;
    }

    public void addCard(Card card) {
        d_CardList.add(card);
    }

    @Override
    public void notifyObserver() {
        System.out.println("A player has conquered a new country");
        for (Observer observer: getObservers()) {
            observer.update(d_CountriesOwned);
        }
    }
}
