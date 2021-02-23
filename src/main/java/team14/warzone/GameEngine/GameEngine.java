package team14.warzone.GameEngine;

import team14.warzone.Console.Command;
import team14.warzone.Console.Console;
import team14.warzone.Console.InputValidator;
import team14.warzone.MapModule.Country;
import team14.warzone.MapModule.Map;
import team14.warzone.MapModule.MapEditor;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

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
    private List<Player> d_PlayerList;

    private Console d_Console;
    private MapEditor d_MapEditor;

    public GameEngine() {
    }

    public GameEngine(Console p_Console) {
        d_Console = p_Console;
    }

    public GameEngine(Console p_Console, MapEditor p_MapEditor) {
        d_Console = p_Console;
        d_MapEditor = p_MapEditor;
    }

    public void loadMap(String p_FileName) {
        d_MapEditor.loadMap(p_FileName);
        this.d_LoadedMap = d_MapEditor.getD_loadedMap();
        InputValidator.CURRENT_PHASE = InputValidator.Phase.STARTUP;
    }

    public void showMap() {
        d_LoadedMap.showMap();
    }

    public void assignCountries() {
        ArrayList<Country> l_Countries = d_LoadedMap.getD_countries();
        if (d_PlayerList.size() >= 2) {
            for (int l_I = 0; l_I < l_Countries.size(); l_I++) {
                for (int l_J = 0; l_J < d_PlayerList.size() && l_I < l_Countries.size(); l_J++) {
                    // add country to player's country-list
                    d_PlayerList.get(l_J).addCountryOwned(l_Countries.get(l_I));
                    // set country's current owner to player
                    l_Countries.get(l_I).setD_CurrentOwner(d_PlayerList.get(l_J).getD_Name());
                    l_I++;
                }
            }
            Console.displayMsg("Success: countries assigned");
        } else {
            Console.displayMsg("Failed: 2-5 players required");
        }
        // change phase to game play
        InputValidator.CURRENT_PHASE = InputValidator.Phase.GAMEPLAY;
    }

    public int generateRandomNumber(int p_Min, int p_Max) {
        return p_Min + (int) (Math.random() * ((p_Max - p_Min) + 1));
    }

    public void addPlayer(String p_PlayerName) {
        Player l_LocalPlayer = new Player(p_PlayerName);
//        l_LocalPlayer.setD_Name(p_PlayerName);
//        l_LocalPlayer.setD_TotalNumberOfArmies(20); //at game start assign 20 armies for each player
        d_PlayerList.add(l_LocalPlayer);
        Console.displayMsg("Player added: " + p_PlayerName);
    }

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
        /*
        // reinforcement
        for(Player l_Player: d_PlayerList){
            //1. # of territories owned divided by 3
            int l_PlayerEnforcement = l_Player.getD_CountriesOwned().size() / 3;
            //2. if the player owns all the territories of an entire continent the player is given
            // a control bonus value
            int l_ControlValueEnforcement = 0;
            for (Continent l_Continent: d_LoadedMap.getD_continents()) {
                if (l_Player.getD_CountriesOwned().containsAll(l_Continent.getD_Countries()))
                    l_ControlValueEnforcement += l_Continent.getD_ControlValue();
            }
            //3.the minimal number of reinforcement armies for any player is 3 + control values
            // of continents he owns
            l_PlayerEnforcement = Math.max(l_PlayerEnforcement, 3) + l_ControlValueEnforcement;
            //give reinforcement to the player
            l_Player.setD_TotalNumberOfArmies(l_PlayerEnforcement);
        }
        */

        //deploy orders
        boolean[] l_Status = new boolean[d_PlayerList.size()];//An array to store players status
        //keep looping through the players list until all of them finished issuing their orders
        while (Arrays.asList(l_Status).contains(false)) {
            for (int i = 0; i < d_PlayerList.size(); i++) {
                if (l_Status[i] == false) {
                    Console.displayMsg("Enter Command for player " + d_PlayerList.get(i).getD_Name());
                    d_Console.readInput();
                    d_Console.filterCommand(this, d_MapEditor);
                }
            }
        }
        Arrays.fill(l_Status, false);
        //execute all the commands until all players orders lists are empty
        while (Arrays.asList(l_Status).contains(false)) {
            for (int i = 0; i < d_PlayerList.size(); i++) {
                d_PlayerList.get(i).nextOrder();
                if (d_PlayerList.get(i).getD_OrderList().isEmpty())
                    l_Status[i] = true;
            }
        }
    }

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
        l_CountryToDeployIn.setNumberOfArmies(l_CountryToDeployIn.getNumberOfArmies() + p_NumberOfArmies);
        // decrease army from player
        d_CurrentPlayer.setD_TotalNumberOfArmies(d_CurrentPlayer.getD_TotalNumberOfArmies() - p_NumberOfArmies);

        Console.displayMsg("Success: " + d_CurrentPlayer.getD_Name() + " deployed " + p_NumberOfArmies + " armies in "
                + p_CountryName);
    }

    public void setD_Console(Console p_Console) {
        d_Console = p_Console;
    }

    public Map getD_LoadedMap() {
        return d_LoadedMap;
    }
}
