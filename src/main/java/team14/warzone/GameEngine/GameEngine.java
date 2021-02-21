package team14.warzone.GameEngine;

import team14.warzone.Console.Command;
import team14.warzone.Console.Console;
import team14.warzone.Console.InputValidator;
import team14.warzone.MapModule.Country;
import team14.warzone.MapModule.Map;

import java.util.Arrays;
import java.util.List;

public class GameEngine {

    private Player d_CurrentPlayer;
    private Map d_LoadedMap;
    private List<Player> d_PlayerList;

    public GameEngine() {
    }

    public void setD_LoadedMap(Map p_Map) {
//        MapEditor l_MapEditor = new MapEditor();
        d_LoadedMap = p_Map;
        InputValidator.CURRENT_PHASE = InputValidator.Phase.STARTUP;
        // change gamephase to "STARTUP"
    }

    public void showMap() {
//        d_LoadedMap.showMap();
    }

    public void assignCountries() {
        //if number of players bigger than or equal to 2, assign countries to players randomly
        List<Country> l_Countries = d_LoadedMap.getD_countries();
        if (d_PlayerList.size() >= 2) {
            for (int l_I = 0; l_I < l_Countries.size(); l_I++) {
                for (int l_J = 0; l_J < d_PlayerList.size() && l_I < l_Countries.size(); l_J++) {
                    d_PlayerList.get(l_J).addCountryOwned(l_Countries.get(l_I));
                    l_I++;
                }
            }
        }
        //change phase to game play
        InputValidator.setCurrentPhase("GAMEPLAY");
    }


    public void addPlayer(String p_PlayerName) {
        Player l_LocalPlayer = new Player();
        l_LocalPlayer.setD_Name(p_PlayerName);
        l_LocalPlayer.setD_TotalNumberOfArmies(20); //at game start assign 20 armies for each player
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
     */
    public void gameLoop() {
        // reinforcement
        boolean[] pass = new boolean[d_PlayerList.size()];
        while (Arrays.asList(pass).contains(false)) {
            for (int i = 0; i < d_PlayerList.size(); i++) {
                d_CurrentPlayer = d_PlayerList.get(i);
                if (pass[i] == false) {
                    Console.displayMsg("Enter Command for player " + d_CurrentPlayer.getD_Name());
//                    Console.readInput();
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

}
