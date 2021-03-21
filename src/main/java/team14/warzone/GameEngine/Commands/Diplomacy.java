package team14.warzone.GameEngine.Commands;

import java.util.ArrayList;

import team14.warzone.GameEngine.GameEngine;
import team14.warzone.GameEngine.Player;
import team14.warzone.Console.Option;
import team14.warzone.MapModule.Country;
import team14.warzone.MapModule.Map;

public class Diplomacy {

    private String d_PlayerId;
    private GameEngine d_GameEngine;

    /**
     * Constructor of Diplomacy
     * 
     * @author tanzia-ahmed
     * @param p_PlayerId
     * @param p_GameEngine
     */
    public Diplomacy(String p_PlayerId, GameEngine p_GameEngine) {
        
        this.d_PlayerId = p_PlayerId;
        this.d_GameEngine = p_GameEngine;

    }

    /**
     * Executes Diplomacy command
     */
    public void execute() {
        Map l_LoadedMap = d_GameEngine.getD_LoadedMap();

        Player l_CurrentPlayer = d_GameEngine.getD_CurrentPlayer();
        if (!l_CurrentPlayer.hasCard(new Card("Diplomacy"))) {// check if player has the Diplomacy card
            throw new Exception("Player does not have this card.");
        }
        // check if player exists
        if (!d_GameEngine.getD_PlayerList().contains(d_PlayerId)) {
            throw new Exception("Diplomacy failed: playerID does not exist");
        }
         else {
            
            //wait till the turn is finished.
        }
    }

}