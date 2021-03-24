package team14.warzone.GameEngine.Commands;

import team14.warzone.GameEngine.Card;
import team14.warzone.GameEngine.GameEngine;
import team14.warzone.GameEngine.Player;
import team14.warzone.MapModule.Country;
import team14.warzone.MapModule.Map;

public class Bomb extends Order{

    private String d_CountryNameTo;
    private int d_NumberOfArmies;
    private GameEngine d_GameEngine;

    /**
     * Constructor of Bomb
     * 
     * @author tanzia-ahmed
     * @param p_CountryNameTo
     * @param p_NumberOfArmies
     * @param p_GameEngine
     */
    public Bomb(String p_CountryNameTo, int p_NumberOfArmies, GameEngine p_GameEngine) {
        this.d_CountryNameTo = p_CountryNameTo;
        this.d_NumberOfArmies = p_NumberOfArmies;
        this.d_GameEngine = p_GameEngine;

    }

    /**
     * Executes bomb command
     */
    public void execute() throws Exception {
        Map l_LoadedMap = d_GameEngine.getD_LoadedMap();

        Player l_CurrentPlayer = d_GameEngine.getD_CurrentPlayer();
        if (!l_CurrentPlayer.hasCard(new Card("Bomb"))) {// check if player has the Bomb card
            throw new Exception("Player does not have this card.");
        }
        
        // check if destination country exists
        Country l_CountryTo = l_LoadedMap.findCountry(d_CountryNameTo);
        if (l_CountryTo == null) {
            throw new Exception("Bomb failed: destination country does not exist");
        }
        //check if destination country is owned by enemy player
        if(l_CurrentPlayer.getD_CountriesOwned().contains(l_CountryTo) | l_CountryTo.getD_CurrentOwner() == "Neutral") {
            throw new Exception("Bomb failed: " + l_CurrentPlayer.getD_Name() + " owns " +
                    l_CountryTo.getD_CountryID());
        }
        else{
            int l_TotalNumOfArmies = l_CurrentPlayer.getD_TotalNumberOfArmies();
            int l_RemainingArmies = d_NumberOfArmies / 2;
            d_GameEngine.getD_LogEntryBuffer().setD_log(l_CurrentPlayer.getD_Name()+ " has bombed"+d_CountryNameTo);
            d_GameEngine.getD_LogEntryBuffer().notifyObservers(d_GameEngine.getD_LogEntryBuffer());
            l_CountryTo.setD_NumberOfArmies(l_RemainingArmies); //bombed
            l_CurrentPlayer.setD_TotalNumberOfArmies(l_TotalNumOfArmies - l_RemainingArmies);
        }

    }

}
