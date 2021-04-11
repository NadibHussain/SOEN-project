package team14.warzone.GameEngine.Commands;

import team14.warzone.Console.Console;
import team14.warzone.GameEngine.Card;
import team14.warzone.GameEngine.GameEngine;
import team14.warzone.GameEngine.Player;
import team14.warzone.MapModule.Country;
import team14.warzone.MapModule.Map;

/**
 * This class is used to create blockade order
 *
 * @author tanzia-ahmed
 */
public class Blockade extends Order {

    /**
     * Source country name
     */
    private String d_CountryNameTo;
    /**
     * Instance of GameEngine
     */
    private GameEngine d_GameEngine;
    /**
     * Destination country name
     */
    private Country d_CountryTo;

    /**
     * Constructor of Blockade class
     *
     * @param p_CountryNameTo destination country
     * @param p_GameEngine    instance of game engine class
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
            //remove ownership from Player object
            d_CountryTo.setD_CurrentOwner("Neutral");
            l_CurrentPlayer.removeCountryOwned(l_CountryTo);
            l_CurrentPlayer.removeCard(l_CardBlockade);
            Console.displayMsg("Success: Blockade country " + d_CountryNameTo);
            d_GameEngine.findPlayer("Neutral").addCountryOwned(l_CountryTo);
            d_GameEngine.getD_LogEntryBuffer().setD_log(l_CurrentPlayer.getD_Name() + " has used blockade in " + d_CountryNameTo);
            d_GameEngine.getD_LogEntryBuffer().notifyObservers(d_GameEngine.getD_LogEntryBuffer());
        }
    }

    @Override
    public void reset() {

    }
}