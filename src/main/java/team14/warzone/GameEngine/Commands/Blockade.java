package team14.warzone.GameEngine.Commands;

import java.util.ArrayList;

import team14.warzone.GameEngine.GameEngine;
import team14.warzone.GameEngine.Player;
import team14.warzone.Console.Option;
import team14.warzone.MapModule.Country;
import team14.warzone.MapModule.Map;

public class Blockade {

    private String d_CountryNameTo;
    private int d_NumberOfArmies;
    private GameEngine d_GameEngine;

    /**
     * Constructor of Blockade
     * 
     * @author tanzia-ahmed
     * @param p_CountryNameTo
     * @param p_NumberOfArmies
     * @param p_GameEngine
     */
    public Blockade(String p_CountryNameTo, int p_NumberOfArmies, GameEngine p_GameEngine) {
        
        this.d_CountryNameTo = p_CountryNameTo;
        this.d_NumberOfArmies = p_NumberOfArmies;
        this.d_GameEngine = p_GameEngine;

    }

    /**
     * Executes Blockade command
     */
    public void execute() {
        Map l_LoadedMap = d_GameEngine.getD_LoadedMap();

        Player l_CurrentPlayer = d_GameEngine.getD_CurrentPlayer();
        if (!l_CurrentPlayer.hasCard(new Card("Blockade"))) {// check if player has the Bomb card
            throw new Exception("Player does not have this card.");
        }
        
        // check if destination country exists
        Country l_CountryTo = l_LoadedMap.findCountry(d_CountryNameTo);
        if (l_CountryTo == null) {
            throw new Exception("Blockade failed: destination country does not exist");
        }
        // check if destination country is owned by enemy player
        if (l_CurrentPlayer.getD_CountriesOwned().contains(l_CountryTo) | l_CountryTo.getD_CurrentOwner() == "Neutral") {
            throw new Exception(
                    "Blockade failed: " + l_CurrentPlayer.getD_Name() + " owns " + l_CountryTo.getD_CountryID() + " or it belongs to a neutral player.");
        } else {
            l_CountryTo.setD_NumberOfArmies(3*d_NumberOfArmies);
            l_CountryTo.setD_CurrentOwner("Neutral");

            //change back the country owner once the turn is finished.
        }
    }

}