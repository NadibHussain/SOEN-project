package team14.warzone.GameEngine;

import team14.warzone.Console.Command;
import team14.warzone.MapModule.Map;

import java.util.List;

public class GameEngine {

    private Player d_CurrentPlayer;
    private Map d_LoadedMap;
    private List<Player> d_PlayerList;

    public GameEngine() {
    }

    public void loadMap(Map p_Map) {
        d_LoadedMap = p_Map;
    }

    public void showMap() {
//        d_LoadedMap.showMap();
    }

    public void assignCountries() {
        // Assign countries to players
    }

    public void removePlayer(Player p_Player) {
        d_PlayerList.remove(p_Player);
    }

    public void receiveCommand(Command p_Command) {
        // store received command in the current players order list
    }

}
