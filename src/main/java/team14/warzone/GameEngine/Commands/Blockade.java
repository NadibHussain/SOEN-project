package team14.warzone.GameEngine.Commands;

import team14.warzone.GameEngine.Card;
import team14.warzone.GameEngine.GameEngine;
import team14.warzone.GameEngine.Player;
import team14.warzone.MapModule.Country;
import team14.warzone.MapModule.Map;

public class Blockade extends Order{

    private String d_CountryNameTo;
    private int d_NumberOfArmies;
    private GameEngine d_GameEngine;
    private Player d_PreviousOwner;
    private Country d_CountryTo;

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
    public void execute() throws Exception {
        Map l_LoadedMap = d_GameEngine.getD_LoadedMap();

        Player l_CurrentPlayer = d_GameEngine.getD_CurrentPlayer();
        if (!l_CurrentPlayer.hasCard(new Card("Blockade"))) {// check if player has the Blockade card
            throw new Exception("Player does not have this card.");
        }
        
        // check if destination country exists
        Country l_CountryTo = l_LoadedMap.findCountry(d_CountryNameTo);
        if (l_CountryTo == null) {
            throw new Exception("Blockade failed: destination country does not exist");
        }
        // check if destination country is owned by enemy player
        if (!l_CurrentPlayer.getD_CountriesOwned().contains(l_CountryTo) | l_CountryTo.getD_CurrentOwner() == "Neutral") {
            throw new Exception(
                    "Blockade failed: " + l_CurrentPlayer.getD_Name() + " owns " + l_CountryTo.getD_CountryID() + " or it belongs to a neutral player.");
        } else {
            //execution
            d_PreviousOwner = l_CurrentPlayer;
            d_CountryTo = l_CountryTo;
            d_CountryTo.setD_NumberOfArmies(3*d_NumberOfArmies);
            d_CountryTo.setD_CurrentOwner("Neutral");
            //remove ownership from Player object
        }
    }
    

}