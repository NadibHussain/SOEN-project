package team14.warzone.GameEngine.Commands;

import team14.warzone.Console.Console;
import team14.warzone.GameEngine.Card;
import team14.warzone.GameEngine.GameEngine;
import team14.warzone.GameEngine.Player;
import team14.warzone.MapModule.Country;
import team14.warzone.MapModule.Map;

/**
 * This class is used to create bomb order
 *
 * @author tanzia-ahmed
 */
public class Bomb extends Order {

    /**
     * Destination country
     */
    private String d_CountryNameTo;
    /**
     * GameEngine instance
     */
    private GameEngine d_GameEngine;

    /**
     * Constructor of Bomb class
     *
     * @param p_CountryNameTo destination country
     * @param p_GameEngine    instance of Game Engine class
     */
    public Bomb(String p_CountryNameTo, GameEngine p_GameEngine) {
        this.d_CountryNameTo = p_CountryNameTo;
        this.d_GameEngine = p_GameEngine;

    }

    /**
     * Executes bomb command
     */
    public void execute() throws Exception {
        Map l_LoadedMap = d_GameEngine.getD_LoadedMap();
        Player l_CurrentPlayer = d_GameEngine.getD_CurrentPlayer();
        Card l_CardBomb = new Card("bomb");
        if (!l_CurrentPlayer.hasCard(l_CardBomb)) {// check if player has the Bomb card
            throw new Exception("Player does not have this card.");
        }

        // check if destination country exists
        Country l_CountryTo = l_LoadedMap.findCountry(d_CountryNameTo);
        if (l_CountryTo == null) {
            throw new Exception("Bomb failed: destination country does not exist");
        }
        //check if destination country is owned by enemy player
        if (l_CurrentPlayer.getD_CountriesOwned().contains(l_CountryTo)) {
            throw new Exception("Bomb failed: " + l_CurrentPlayer.getD_Name() + " owns " +
                    l_CountryTo.getD_CountryID());
        }
        //check if the enemy is a diplomatic ally or not
        if (l_CurrentPlayer.isDiplomaticPlayer(l_CurrentPlayer, d_GameEngine.findPlayer(l_CountryTo.getD_CurrentOwner()))) {
            throw new Exception("Cannot bomb on diplomatic ally's country");
        } else {
            //execution
            int l_TotalNumOfArmies = d_GameEngine.findPlayer(l_CountryTo.getD_CurrentOwner()).getD_TotalNumberOfArmies();
            int l_RemainingArmies = l_CountryTo.getD_NumberOfArmies() / 2;
            d_GameEngine.getD_LogEntryBuffer().setD_log(l_CurrentPlayer.getD_Name() + " has bombed" + d_CountryNameTo);
            d_GameEngine.getD_LogEntryBuffer().notifyObservers(d_GameEngine.getD_LogEntryBuffer());
            l_CountryTo.setD_NumberOfArmies(l_RemainingArmies); //bombed
            d_GameEngine.findPlayer(l_CountryTo.getD_CurrentOwner()).setD_TotalNumberOfArmies(l_TotalNumOfArmies - l_RemainingArmies);
            Console.displayMsg("Success: " + l_CurrentPlayer.getD_Name() + " bombed country " + d_CountryNameTo);
            l_CurrentPlayer.removeCard(new Card("bomb"));
        }

    }

    @Override
    public void reset() {

    }

}
