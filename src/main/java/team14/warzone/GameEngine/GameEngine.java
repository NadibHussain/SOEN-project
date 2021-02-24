package team14.warzone.GameEngine;

import team14.warzone.Console.Command;
import team14.warzone.Console.Console;
import team14.warzone.Console.InputValidator;
import team14.warzone.MapModule.Continent;
import team14.warzone.MapModule.Country;
import team14.warzone.MapModule.Map;
import team14.warzone.MapModule.MapEditor;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

/**
 * This class implements the functionalities of the game-play phase
 *
 * @author Anagh
 * @author Zeina
 * @version 1.0
 */
public class GameEngine {

    private Player d_CurrentPlayer;
    private Map d_LoadedMap;
    private ArrayList<Player> d_PlayerList;

    private Console d_Console;
    private MapEditor d_MapEditor;

    /**
     * public method of GameEngine
     */
    public GameEngine() {
    }

    /**
     * @param p_Console Console parameter
     */
    public GameEngine(Console p_Console) {
        d_Console = p_Console;
        d_PlayerList = new ArrayList<Player>();
    }

    /**
     * @param p_Console   Console parameter
     * @param p_MapEditor MapEditor parameter
     */
    public GameEngine(Console p_Console, MapEditor p_MapEditor) {
        d_Console = p_Console;
        d_MapEditor = p_MapEditor;
        d_PlayerList = new ArrayList<Player>();
    }

    /**
     * LoadMap method
     *
     * @param p_FileName String FileName as parameter
     */
    public void loadMap(String p_FileName) {
        d_MapEditor.loadMap(p_FileName);
        this.d_LoadedMap = d_MapEditor.getD_LoadedMap();
        InputValidator.CURRENT_PHASE = InputValidator.Phase.STARTUP;
    }

    /**
     * Showmap method
     */
    public void showMap() {
        d_LoadedMap.showMap();
    }

    /**
     * Assign Countries method
     */
    public void assignCountries() {
        ArrayList<Country> l_Countries = d_LoadedMap.getD_Countries();
        //if number of players between 2 and 5, assign countries to players randomly
        if (d_PlayerList.size() >= 2 && d_PlayerList.size() <= 5) {
            int l_CountryCounter = 0;
            while (l_CountryCounter < l_Countries.size()) {
                for (int l_PlayerIterator = 0; l_PlayerIterator < d_PlayerList.size() && l_CountryCounter < l_Countries.size(); l_PlayerIterator++) {
                    // add country to player's country-list
                    d_PlayerList.get(l_PlayerIterator).addCountryOwned(l_Countries.get(l_CountryCounter));
                    // set country's current owner to player
                    l_Countries.get(l_CountryCounter).setD_CurrentOwner(d_PlayerList.get(l_PlayerIterator).getD_Name());
                    l_CountryCounter++;
                }
            }
            Console.displayMsg("Success: countries assigned");
        } else {
            Console.displayMsg("Failed: 2-5 players required");
        }
        // change phase to game play
        InputValidator.CURRENT_PHASE = InputValidator.Phase.GAMEPLAY;
    }

    /**
     * Add player method
     *
     * @param p_PlayerName String PlayerName as parameter
     */
    public void addPlayer(String p_PlayerName) {
        Player l_LocalPlayer = new Player(p_PlayerName);
//        l_LocalPlayer.setD_Name(p_PlayerName);
//        l_LocalPlayer.setD_TotalNumberOfArmies(20); //at game start assign 20 armies for each player
        d_PlayerList.add(l_LocalPlayer);
        Console.displayMsg("Player added: " + p_PlayerName);
    }

    /**
     * Remove Player
     *
     * @param p_PlayerName String PlayerName as parameter
     */
    public void removePlayer(String p_PlayerName) {
        for (Player l_Player : d_PlayerList) {
            if (l_Player.getD_Name().equals(p_PlayerName))
                d_PlayerList.remove(l_Player);
        }
        Console.displayMsg("Player removed: " + p_PlayerName);
    }

    /**
     * A method to loop the players list in a RR fashion, to give their order
     * Has two stages:
     * 1. Loop through all the players until everyone is done giving orders
     * 2. Loops through the order list of each player and execute their orders
     */
    public void gameLoop() {
        // reinforcement
        for (Player l_Player : d_PlayerList) {
            //1. # of territories owned divided by 3
            int l_PlayerEnforcement = l_Player.getD_CountriesOwned().size() / 3;
            //2. if the player owns all the territories of an entire continent the player is given
            // a control bonus value
            int l_ControlValueEnforcement = 0;
            for (Continent l_Continent : d_LoadedMap.getD_Continents()) {
                //check if all countries belong to the l_Continent are owned by l_Player
                if (l_Player.getD_CountriesOwned().containsAll(d_LoadedMap.getCountryListOfContinent(l_Continent.getD_ContinentID())))
                    l_ControlValueEnforcement += l_Continent.getD_ControlValue();
            }
            //3.the minimal number of reinforcement armies for any player is 3 + control values
            // of continents he owns
            l_PlayerEnforcement = Math.max(l_PlayerEnforcement, 3) + l_ControlValueEnforcement;
            //give reinforcement to the player
            l_Player.setD_TotalNumberOfArmies(l_Player.getD_TotalNumberOfArmies() + l_PlayerEnforcement);
        }

        // Take and queue orders
        ArrayList<Boolean> l_Flag = new ArrayList<Boolean>(Arrays.asList(new Boolean[d_PlayerList.size()]));
        Collections.fill(l_Flag, Boolean.FALSE);
        //keep looping through the players list until all of them finished issuing their orders
        while (l_Flag.contains(Boolean.FALSE)) {
            int l_Counter = 0;
            while (l_Counter < d_PlayerList.size()) {
                if (!l_Flag.get(l_Counter)) {
                    d_CurrentPlayer = d_PlayerList.get(l_Counter);
                    Console.displayMsg("Enter Command for player " + d_PlayerList.get(l_Counter).getD_Name());
                    d_Console.readInput();
                    if (d_Console.getD_CommandBuffer().getD_Keyword().equals("pass"))
                        l_Flag.set(l_Counter, Boolean.TRUE);
                    else
                        d_Console.filterCommand(this, d_MapEditor);
                    // move to next player only if current player issued valid game-play command or passed his turn
                }
                if (InputValidator.VALID_GAMEPLAY_COMMANDS.contains(d_Console.getD_CommandBuffer().getD_Keyword()) || d_Console.getD_CommandBuffer().getD_Keyword().equals("pass"))
                    l_Counter++;
            }
        }

        //execute all the commands until all players orders lists are empty
        Collections.fill(l_Flag, Boolean.FALSE);
        while (l_Flag.contains(false)) {
            for (int i = 0; i < d_PlayerList.size(); i++) {
                d_CurrentPlayer = d_PlayerList.get(i);
                d_PlayerList.get(i).nextOrder();
                if (d_PlayerList.get(i).getD_OrderList().isEmpty())
                    l_Flag.set(i, Boolean.TRUE);
            }
        }
    }

    /**
     * Receive Command method
     *
     * @param p_Command command type as parameter
     */
    public void receiveCommand(Command p_Command) {
        // store received command in the current players order list
        d_CurrentPlayer.issueOrder(p_Command); // store order in current player orders list
    }

    /**
     * This method implements the deploy command
     * Increases the number of armies in the country to be deployed to
     * Decreases total number of armies of the player that issued this command
     *
     * @param p_CountryName    name of the country where armies are to be deployed
     * @param p_NumberOfArmies number of armies to deploy
     */
    public void deploy(String p_CountryName, int p_NumberOfArmies) {
        // increase armies in country
        Country l_CountryToDeployIn = d_LoadedMap.findCountry(p_CountryName);
        l_CountryToDeployIn.setD_NumberOfArmies(l_CountryToDeployIn.getD_NumberOfArmies() + p_NumberOfArmies);
        // decrease army from player
        d_CurrentPlayer.setD_TotalNumberOfArmies(d_CurrentPlayer.getD_TotalNumberOfArmies() - p_NumberOfArmies);

        Console.displayMsg("Success: " + d_CurrentPlayer.getD_Name() + " deployed " + p_NumberOfArmies + " armies in "
                + p_CountryName);
    }

    /**
     * Set console
     *
     * @param p_Console
     */
    public void setD_Console(Console p_Console) {
        d_Console = p_Console;
    }

    /**
     * Get loaded map
     *
     * @return returns a loaded map
     */
    public Map getD_LoadedMap() {
        return d_LoadedMap;
    }
}
