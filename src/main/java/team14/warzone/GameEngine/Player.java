package team14.warzone.GameEngine;

import team14.warzone.Console.Console;
import team14.warzone.GameEngine.Commands.*;
import team14.warzone.GameEngine.Strategy.*;
import team14.warzone.MapModule.Country;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Class implements the player model
 *
 * @author Anagh
 * @version 1.0
 */
public class Player implements Serializable {
    /**
     * name of player
     */
    private String d_Name;
    /**
     * number of armies in players possession
     */
    private int d_TotalNumberOfArmies;
    /**
     * track how many armies have been ordered to be deployed
     */
    private int d_ArmiesOrderedToBeDeployed;
    /**
     * list of countries owned by player
     */
    private ArrayList<Country> d_CountriesOwned;
    /**
     * list of orders issued by player that has not been executed
     */
    private ArrayList<Order> d_OrderList;
    /**
     * list of cards the player is holding
     */
    private ArrayList<Card> d_CardList;
    /**
     * Reference to the GameEngine object
     */
    private GameEngine d_GE;
    /**
     * list of diplomatic players
     */
    private ArrayList<Player> d_DiplomaticPlayers = new ArrayList<>();
    /**
     * Flag to keep track whether player has received card during turn
     */
    private boolean d_CardReceived;
    /**
     * Depending on the player strategy holds the suitable implementation of issue order
     */
    public Behavior d_IssueOrderBehavior;
    /**
     * Keeps track of whether player has already received reinforcement
     */
    private boolean d_ReceivedReinforcement;

    /**
     * Default constructor that takes no params
     */
    public Player() {
    }

    /**
     * Constructor that takes all attributes as params
     *
     * @param d_Name                name of the player
     * @param p_PlayerType          player behavior type
     * @param d_TotalNumberOfArmies total number of armies
     * @param d_CountriesOwned      list of countries (Country objects) owned by the player
     * @param d_OrderList           list of orders the player has issued but has not executed yet
     * @param p_GE                  game engine
     */
    public Player(String d_Name, String p_PlayerType, int d_TotalNumberOfArmies, ArrayList<Country> d_CountriesOwned,
                  ArrayList<Order> d_OrderList, GameEngine p_GE) {
        this.d_Name = d_Name;
        this.d_TotalNumberOfArmies = d_TotalNumberOfArmies;
        this.d_CountriesOwned = d_CountriesOwned;
        this.d_OrderList = d_OrderList;
        d_GE = p_GE;
        d_ArmiesOrderedToBeDeployed = 0;
        d_CardList = new ArrayList<>();
        d_CardReceived = false;
        d_ReceivedReinforcement = false;

        switch (p_PlayerType) {
            case "human":
            case "Human":
                d_IssueOrderBehavior = new Human();
                break;
            case "aggressive":
            case "Aggressive":
                d_IssueOrderBehavior = new Aggressive();
                break;
            case "benevolent":
            case "Benevolent":
                d_IssueOrderBehavior = new Benevolent();
                break;
            case "random":
            case "Random":
                d_IssueOrderBehavior = new Random();
                break;
            case "cheater":
            case "Cheater":
                d_IssueOrderBehavior = new Cheater();
        }
    }

    /**
     * Constructor that accepts playername and sets the other attributes with default values
     *
     * @param p_Name name of the player
     * @param p_GE   gameengine
     */
    public Player(String p_Name, GameEngine p_GE) {
        this(p_Name, "human", 20, new ArrayList<Country>(Collections.emptyList()),
                new ArrayList<Order>(Collections.emptyList()), p_GE);
    }

    /**
     * Constructor that accepts playername, playertype and sets the other attributes with default values
     *
     * @param p_Name       name of the player
     * @param p_PlayerType player behavior type
     * @param p_GE         gameengine
     */
    public Player(String p_Name, String p_PlayerType, GameEngine p_GE) {
        this(p_Name, p_PlayerType, 20, new ArrayList<Country>(Collections.emptyList()),
                new ArrayList<Order>(Collections.emptyList()), p_GE);
    }

    /**
     * This method adds order to the list of orders
     */
    public void issueOrder() {
        d_IssueOrderBehavior.issueOrder(d_GE, this);
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
            try {
                d_OrderList.get(0).execute();//execute first order from the order list
            } catch (Exception e) {
                Console.displayMsg(e.getMessage());
            }
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
        if (d_GE.getD_CurrentPhase().equals(d_GE.getD_ExecuteOrdersPhase()) && !d_CardReceived) {
            d_GE.allotCard(this);
            d_CardReceived = true;
        }
    }

    /**
     * This method removes a country from the list of countries owned by the player
     *
     * @param p_Country country that is to be removed
     */
    public void removeCountryOwned(Country p_Country) {
        if (!this.d_CountriesOwned.isEmpty() && this.d_CountriesOwned.contains(p_Country))
            this.d_CountriesOwned.remove(p_Country);
    }

    /**
     * Method to check player cards
     *
     * @param p_Card card
     * @return true or false
     */
    public boolean hasCard(Card p_Card) {
        for (Card l_Card : d_CardList) {
            if (l_Card.getD_CardType().equals(p_Card.getD_CardType()))
                return true;
        }
        return false;
    }

    /**
     * Method to check player cards
     *
     * @param p_CardType string
     * @return true or false
     */
    public boolean hasCard(String p_CardType) {
        for (Card l_Card : d_CardList) {
            if (l_Card.getD_CardType().equals(p_CardType) && !l_Card.isD_Used())
                return true;
        }
        return false;
    }

    /**
     * Setter for list of used cards
     *
     * @param p_CardType type of card
     */
    public void setCardUsed(String p_CardType) {
        for (Card l_Card : d_CardList) {
            if (l_Card.getD_CardType().equals(p_CardType) && !l_Card.isD_Used())
                l_Card.setD_Used(true);
        }
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
    public ArrayList<Order> getD_OrderList() {
        return d_OrderList;
    }

    /**
     * Setter method for the list of orders
     *
     * @param d_OrderList list or orders
     */
    public void setD_OrderList(ArrayList<Order> d_OrderList) {
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

    /**
     * The method to add card to players' card list
     *
     * @param card card to be added
     */
    public void addCard(Card card) {
        d_CardList.add(card);
    }

    /**
     * Remove card method
     *
     * @param p_Card card
     */
    public void removeCard(Card p_Card) {
        for (Card l_Card : getCardList()) {
            if (l_Card.getD_CardType().equals(p_Card.getD_CardType())) {
                getCardList().remove(l_Card);
                break;
            }
        }
    }

    /**
     * The method to get cards list of this player
     *
     * @return cards list owned by this player
     */
    public List<Card> getCardList() {
        return d_CardList;
    }

    /**
     * Getter for the number of armies ordered to be deployed field
     *
     * @return returns ArmiesOrderedToBeDeployed
     */
    public int getD_ArmiesOrderedToBeDeployed() {
        return d_ArmiesOrderedToBeDeployed;
    }

    /**
     * Increase the number of armies ordered to be deployed field
     *
     * @param p_ArmiesOrderedToBeDeploy parameter ArmiesOrderedToBeDeployed
     */
    public void increaseArmiesOrderedToBeDeployed(int p_ArmiesOrderedToBeDeploy) {
        if (d_ArmiesOrderedToBeDeployed + p_ArmiesOrderedToBeDeploy > d_TotalNumberOfArmies)
            d_ArmiesOrderedToBeDeployed = d_TotalNumberOfArmies;
        else
            d_ArmiesOrderedToBeDeployed += p_ArmiesOrderedToBeDeploy;
    }

    /**
     * Decrease the number of armies ordered to be deployed field
     *
     * @param p_ArmiesOrderedToBeDeploy parameter ArmiesOrderedToBeDeployed
     */
    public void decreaseArmiesOrderedToBeDeployed(int p_ArmiesOrderedToBeDeploy) {
        d_ArmiesOrderedToBeDeployed -= p_ArmiesOrderedToBeDeploy;
    }

    /**
     * Add diplomatic player in list
     *
     * @param p_Player player to add in the list
     */
    public void addDiplomaticPlayer(Player p_Player) {
        this.d_DiplomaticPlayers.add(p_Player);

    }

    /**
     * Remove diplomatic player from list
     *
     * @param p_Player player which has to be removed
     */
    public void removeDiplomaticPlayer(Player p_Player) {
        this.d_DiplomaticPlayers.remove(p_Player);
    }

    /**
     * Get the list of diplomatic players list
     *
     * @return ArrayList of diplomatic allies
     */
    public ArrayList<Player> getD_DiplomaticPlayerList() {
        return this.d_DiplomaticPlayers;
    }

    /**
     * Checks if the players are in diplomatic relation
     *
     * @param p_CurrentPlayer the current player
     * @param p_TargetPlayer  the target player
     * @return true if either of them have used diplomacy card on each other
     */
    public boolean isDiplomaticPlayer(Player p_CurrentPlayer, Player p_TargetPlayer) {
        if (p_CurrentPlayer.getD_DiplomaticPlayerList().contains(p_TargetPlayer) | p_TargetPlayer.getD_DiplomaticPlayerList().contains(p_CurrentPlayer)) {
            return true;
        }
        return false;
    }

    /**
     * Method resets the card received flag to false at end of turn
     */
    public void resetCardReceivedFlag() {
        d_CardReceived = false;
    }

    /**
     * Getter for order behavior
     *
     * @return order behavior obj
     */
    public Behavior getD_IssueOrderBehavior() {
        return d_IssueOrderBehavior;
    }

    /**
     * Set the player's order behavior
     *
     * @param p_IssueOrderBehavior order behavior based on player type
     */
    public void setD_IssueOrderBehavior(Behavior p_IssueOrderBehavior) {
        d_IssueOrderBehavior = p_IssueOrderBehavior;
    }

    /**
     * Reset all game progress for player
     */
    public void resetPlayer() {
        d_TotalNumberOfArmies = 20;
        d_ArmiesOrderedToBeDeployed = 0;
        d_CountriesOwned.clear();
        d_OrderList.clear();
        d_CardList.clear();
        d_DiplomaticPlayers.clear();
    }

    /**
     * Getter
     *
     * @return whether reinforcement received this turn
     */
    public boolean getD_ReceivedReinforcement() {
        return d_ReceivedReinforcement;
    }

    /**
     * Reset reinforcement flag (set to false)
     */
    public void resetReceivedReinforcement() {
        d_ReceivedReinforcement = false;
    }

    /**
     * Set reinforcement flag to true
     */
    public void hasReceivedReinforcement() {
        d_ReceivedReinforcement = true;
    }
}
