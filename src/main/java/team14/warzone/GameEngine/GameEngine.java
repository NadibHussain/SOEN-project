package team14.warzone.GameEngine;

import team14.warzone.Console.Command;
import team14.warzone.Console.InputValidator;
import team14.warzone.MapModule.Country;
import team14.warzone.MapModule.Map;
import team14.warzone.MapModule.MapEditor;

import java.util.List;

public class GameEngine {

    private Player d_CurrentPlayer;
    private Map d_LoadedMap;
    private List<Player> d_PlayerList;

    public GameEngine() {
    }

    public void loadMap(Map p_Map) {
        MapEditor l_MapEditor = new MapEditor();
        d_LoadedMap = l_MapEditor.getD_loadedMap();
    }

    public void showMap() {
//        d_LoadedMap.showMap();
    }

    public void assignCountries() {
        //if number of players bigger than or equal to 2, assign countries to players randomly
        List<Country> l_Countries = d_LoadedMap.getD_countries();
        if(d_PlayerList.size() >= 2){
            for (int l_I = 0; l_I < l_Countries.size(); l_I++){
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
        for (Player l_Player: d_PlayerList) {
            if(l_Player.getD_Name().equals(p_PlayerName))
                d_PlayerList.remove(l_Player);
        }
    }

    public void receiveCommand(Command p_Command) {
        // store received command in the current players order list
        d_CurrentPlayer.issueOrder(p_Command);
    }

}
