package team14.warzone.GameEngine;

import team14.warzone.Console.Command;
import team14.warzone.Console.Console;
import team14.warzone.Console.InputValidator;
import team14.warzone.MapModule.Country;
import team14.warzone.MapModule.Map;
import team14.warzone.MapModule.MapEditor;

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
        //if number of players bigger than or equal to 2, assign countries to players randomly
        List<Country> l_Countries = d_LoadedMap.getD_countries();
//        if (d_PlayerList.size() >= 2) {
//            for (int l_I = 0; l_I < l_Countries.size(); l_I++) {
//                for (int l_J = 0; l_J < d_PlayerList.size() && l_I < l_Countries.size(); l_J++) {
//                    d_PlayerList.get(l_J).addCountryOwned(l_Countries.get(l_I));
//                    l_I++;
//                }
//            }
//        }
        if (d_PlayerList.size() >= 2) {
            while (!l_Countries.isEmpty()) {
                for (Player l_Player : d_PlayerList) {
                    // get random country and remove from list
                    Country l_RandomCountry = l_Countries.remove(generateRandomNumber(0, l_Countries.size() - 1));
                    // assign a random country to player
                    l_Player.addCountryOwned(l_RandomCountry);
                    // set country's owner to l_player
                    l_RandomCountry.setD_CurrentOwner(l_Player.getD_Name());
                }
            }
        }
        //change phase to game play
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
    }

    public void removePlayer(String p_PlayerName) {
        for (Player l_Player : d_PlayerList) {
            if (l_Player.getD_Name().equals(p_PlayerName))
                d_PlayerList.remove(l_Player);
        }
    }

    /**
     * A method to loop the players list in a RR fashion, to give their order
     * Has two stages:
     * 1. Loop through all the players until everyone is done giving orders
     * 2. Loops through the order list of each player and execute their orders
     */
    public void gameLoop() {
        // reinforcement
        boolean[] pass = new boolean[d_PlayerList.size()];
        while (Arrays.asList(pass).contains(false)) {
            for (int i = 0; i < d_PlayerList.size(); i++) {
                d_CurrentPlayer = d_PlayerList.get(i);
                if (pass[i] == false) {
                    Console.displayMsg("Enter Command for player " + d_CurrentPlayer.getD_Name());
                    d_Console.readInput();
                    d_Console.filterCommand(this, d_MapEditor);
                }
            }
        }
        Arrays.fill(pass, false);
        // execute all the commands
        // loop through players
        // check if command list is empty
        // execute & remove command
    }

    public void receiveCommand(Command p_Command) {
        // store received command in the current players order list
        d_CurrentPlayer.issueOrder(p_Command); //store order in current player orders list
        switch (p_Command.getD_Keyword()) {
            case "deploy": //decrease number of armies for the current player
                int l_ArmiesOwned = d_CurrentPlayer.getD_TotalNumberOfArmies();
                int l_ArmiesToDeploy = Integer.parseInt(p_Command.getD_Options().getD_Arguments().get(1));
                d_CurrentPlayer.setD_TotalNumberOfArmies(l_ArmiesOwned - l_ArmiesToDeploy);
                break;
            default:
        }
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
    }

    public void setD_Console(Console p_Console) {
        d_Console = p_Console;
    }

    public Map getD_LoadedMap() {
        return d_LoadedMap;
    }
}
