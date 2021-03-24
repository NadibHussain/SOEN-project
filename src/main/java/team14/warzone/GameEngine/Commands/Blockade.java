package team14.warzone.GameEngine.Commands;

import team14.warzone.Console.Console;
import team14.warzone.GameEngine.Card;
import team14.warzone.GameEngine.GameEngine;
import team14.warzone.GameEngine.NeutralPlayer;
import team14.warzone.GameEngine.Player;
import team14.warzone.MapModule.Country;
import team14.warzone.MapModule.Map;

public class Blockade extends Order {

    private String d_CountryNameTo;
    private GameEngine d_GameEngine;
    private Country d_CountryTo;

    /**
     * Constructor of Blockade
     *
     * @param p_CountryNameTo
     * @param p_GameEngine
     * @author tanzia-ahmed
     */
    public Blockade(String p_CountryNameTo, GameEngine p_GameEngine) {

        this.d_CountryNameTo = p_CountryNameTo;
        this.d_GameEngine = p_GameEngine;

    }

    /**
     * Executes Blockade command
     */
    public void execute() throws Exception {
        Map l_LoadedMap = d_GameEngine.getD_LoadedMap();

        Player l_CurrentPlayer = d_GameEngine.getD_CurrentPlayer();
        Card l_CardBlockade = new Card("blockade");
        if (!l_CurrentPlayer.hasCard(l_CardBlockade)) {// check if player has the Blockade card
            throw new Exception("Player does not have blockade card.");
        }

        // check if destination country exists
        Country l_CountryTo = l_LoadedMap.findCountry(d_CountryNameTo);
        if (l_CountryTo == null) {
            throw new Exception("Blockade failed: destination country does not exist");
        }
        // check if destination country is owned by enemy player
        if (!l_CurrentPlayer.getD_CountriesOwned().contains(l_CountryTo)) {
            throw new Exception(
                    "Blockade failed: " + l_CurrentPlayer.getD_Name() + " owns " + l_CountryTo.getD_CountryID());
        } else {
            //execution
            d_CountryTo = l_CountryTo;
            d_CountryTo.setD_NumberOfArmies(3 * d_CountryTo.getD_NumberOfArmies());
            d_CountryTo.setD_CurrentOwner("Neutral");
            l_CurrentPlayer.removeCountryOwned(l_CountryTo);
            Console.displayMsg("Success: Blockade country " + d_CountryNameTo);
            NeutralPlayer l_Neutral = (NeutralPlayer) d_GameEngine.findPlayer("Neutral");
            l_Neutral.addCountryOwned(l_CountryTo);
        }
    }
}