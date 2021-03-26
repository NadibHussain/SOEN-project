package team14.warzone.GameEngine.Commands;

import team14.warzone.Console.Console;
import team14.warzone.GameEngine.Card;
import team14.warzone.GameEngine.GameEngine;
import team14.warzone.GameEngine.Player;
import team14.warzone.MapModule.Country;
import team14.warzone.MapModule.Map;

/**
 * This class is used to create airlift order
 *
 * @author zeina
 */
public class Airlift extends Order {
    /**
     * A field to store source country name
     */
    private String d_CountryNameFrom;
    /**
     * A field to store destination country name
     */
    private String d_CountryNameTo;
    /**
     * A field to store number of armies to be moved
     */
    private int d_NumberOfArmies;

    /**
     * Constructor of Airlift order
     *
     * @param p_CountryNameFrom source country name
     * @param p_CountryNameTo   destination country name
     * @param p_NumberOfArmies  armies to be moved
     * @param p_GameEngine      instance of GameEngine class
     */
    public Airlift(String p_CountryNameFrom, String p_CountryNameTo, int p_NumberOfArmies, GameEngine p_GameEngine) {
        this.d_CountryNameFrom = p_CountryNameFrom;
        this.d_CountryNameTo = p_CountryNameTo;
        this.d_NumberOfArmies = p_NumberOfArmies;
        this.d_GameEngine = p_GameEngine;
    }

    /**
     * A method to execute the Airlift order
     */
    public void execute() throws Exception {
        Player l_CurrentPlayer = d_GameEngine.getD_CurrentPlayer();
        //check if player has the Airlift card
        Card l_CardAirlift = new Card("airlift");
        if (l_CurrentPlayer.hasCard(l_CardAirlift)) {
            Map l_LoadedMap = d_GameEngine.getD_LoadedMap();

            // check if source country exists
            Country l_CountryFrom = l_LoadedMap.findCountry(d_CountryNameFrom);
            if (l_CountryFrom == null) {
                throw new Exception("Airlift failed: source country does not exist");
            }
            // check if source country is owned by the player
            if (!l_CurrentPlayer.getD_CountriesOwned().contains(l_CountryFrom)) {
                throw new Exception("Airlift failed: " + l_CurrentPlayer.getD_Name() + " does not own " +
                        l_CountryFrom.getD_CountryID());
            }
            // check if destination country exists
            Country l_CountryTo = l_LoadedMap.findCountry(d_CountryNameTo);
            if (l_CountryTo == null) {
                throw new Exception("Airlift failed: destination country does not exist");
            }
            // check if destination country is owned by the player
            if (!l_CurrentPlayer.getD_CountriesOwned().contains(l_CountryTo)) {
                //player will lose armies if airlifted to a country he doesn't own
                l_CurrentPlayer.setD_TotalNumberOfArmies(l_CurrentPlayer.getD_TotalNumberOfArmies() - d_NumberOfArmies);
                throw new Exception("Airlift failed: " + l_CurrentPlayer.getD_Name() + " does not own " +
                        l_CountryTo.getD_CountryID());
            }
            // check if destination country is owned by the player, then move armies to the destination country
            else {
                // increase armies in destination country
                l_CountryTo.setD_NumberOfArmies(l_CountryTo.getD_NumberOfArmies() + d_NumberOfArmies);
                // decrease armies in source country
                l_CountryFrom.setD_NumberOfArmies(l_CountryFrom.getD_NumberOfArmies() - d_NumberOfArmies);
                l_CurrentPlayer.removeCard(new Card("airlift"));
                Console.displayMsg("Success: " + l_CurrentPlayer.getD_Name() + " airlifted " + d_NumberOfArmies + " armies" +
                        " from " + d_CountryNameFrom + " to " + d_CountryNameTo);
                d_GameEngine.getD_LogEntryBuffer().setD_log("Success: " + l_CurrentPlayer.getD_Name() + " airlifted " + d_NumberOfArmies + " armies" +
                        " from " + d_CountryNameFrom + " to " + d_CountryNameTo);
                d_GameEngine.getD_LogEntryBuffer().notifyObservers(d_GameEngine.getD_LogEntryBuffer());
            }
        } else {
            Console.displayMsg("Player " + l_CurrentPlayer.getD_Name() + " can not perform airlifting, " +
                    "since he does not own an Airlift card");

        }
    }

    @Override
    public void reset() {

    }
}
