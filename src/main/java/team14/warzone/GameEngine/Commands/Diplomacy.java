package team14.warzone.GameEngine.Commands;

import team14.warzone.Console.Console;
import team14.warzone.GameEngine.Card;
import team14.warzone.GameEngine.GameEngine;
import team14.warzone.GameEngine.Player;

/**
 * This class is used to create diplomacy/negotiate order
 *
 * @author tanzia-ahmed
 */
public class Diplomacy extends Order {
    /**
     * Player ID field
     */
    private String d_PlayerId;
    /**
     * Game engine field
     */
    private GameEngine d_GameEngine;
    /**
     * Previous owner of a country
     */
    private Player d_PreviousOwner;

    /**
     * Constructor of Diplomacy
     *
     * @param p_PlayerId player id who wants to use diplomacy card
     * @param p_GameEngine gameengine parameter
     */
    public Diplomacy(String p_PlayerId, GameEngine p_GameEngine) {

        this.d_PlayerId = p_PlayerId;
        this.d_GameEngine = p_GameEngine;

    }

    /**
     * Executes Diplomacy command
     */
    public void execute() throws Exception {
        Player l_CurrentPlayer = d_GameEngine.getD_CurrentPlayer();
        if (!l_CurrentPlayer.hasCard(new Card("diplomacy"))) {// check if player has the Diplomacy card
            throw new Exception("Player does not have this card.");
        }
        // check if player exists and diplomat is not the current player itself
        if (!d_GameEngine.getD_PlayerList().contains(d_GameEngine.findPlayer((d_PlayerId))) & !l_CurrentPlayer.getD_Name().equals(d_PlayerId)) {
            throw new Exception("Diplomacy failed: playerID does not exist or player is trying to be his own diplomatic ally");
        } else {
            l_CurrentPlayer.addDiplomaticPlayer(d_GameEngine.findPlayer((d_PlayerId)));
            l_CurrentPlayer.removeCard(new Card("diplomacy"));
            d_PreviousOwner = l_CurrentPlayer;
            Console.displayMsg("Success : " + l_CurrentPlayer.getD_Name() + " and " + d_PlayerId + " are allies for this turn");
            d_GameEngine.appendToOrderBuffer(this);
            d_GameEngine.getD_LogEntryBuffer().setD_log("Success : " + l_CurrentPlayer.getD_Name() + " and " + d_PlayerId + " are allies for this turn");
            d_GameEngine.getD_LogEntryBuffer().notifyObservers(d_GameEngine.getD_LogEntryBuffer());
        }
    }

    /**
     * resets the diplomatic relationship with playerID
     * once the turn is finished.
     */
    public void reset() {
        d_PreviousOwner.removeDiplomaticPlayer(d_GameEngine.findPlayer((d_PlayerId)));
    }

}