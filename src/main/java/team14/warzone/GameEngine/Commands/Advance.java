package team14.warzone.GameEngine.Commands;

import team14.warzone.Console.Console;
import team14.warzone.GameEngine.GameEngine;
import team14.warzone.GameEngine.Player;
import team14.warzone.MapModule.Country;
import team14.warzone.MapModule.Map;

/**
 * This class is used to create advance order
 *
 * @author zeina
 */
public class Advance extends Order {
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
     * Constructor of Advance order
     *
     * @param p_CountryNameFrom source country name
     * @param p_CountryNameTo   destination country name
     * @param p_NumberOfArmies  armies to be moved
     * @param p_GameEngine      instance of GameEngine class
     */
    public Advance(String p_CountryNameFrom, String p_CountryNameTo, int p_NumberOfArmies, GameEngine p_GameEngine) {
        this.d_CountryNameFrom = p_CountryNameFrom;
        this.d_CountryNameTo = p_CountryNameTo;
        this.d_NumberOfArmies = p_NumberOfArmies;
        this.d_GameEngine = p_GameEngine;
    }

    /**
     * A method to execute the Advance order
     */
    @Override
    public void execute() throws Exception {
        Player l_CurrentPlayer = d_GameEngine.getD_CurrentPlayer();
        Map l_LoadedMap = d_GameEngine.getD_LoadedMap();

        // check if source country exists
        Country l_CountryFrom = l_LoadedMap.findCountry(d_CountryNameFrom);
        if (l_CountryFrom == null) {
            throw new Exception("Advance failed: source country does not exist");
        }
        // check if source country is owned by the player
        if (!l_CurrentPlayer.getD_CountriesOwned().contains(l_CountryFrom)) {
            throw new Exception("Advance failed: " + l_CurrentPlayer.getD_Name() + " does not own " +
                    d_CountryNameFrom);
        }
        // check if destination country exists
        Country l_CountryTo = l_LoadedMap.findCountry(d_CountryNameTo);
        if (l_CountryTo == null) {
            throw new Exception("Advance failed: destination country does not exist");
        }
        //check if source country has enough armies as specified in the player order
        if (l_CountryFrom.getD_NumberOfArmies() < d_NumberOfArmies) {
            throw new Exception("Advance failed: " + d_CountryNameFrom + " does not have enough armies");
        }
        //check if player is not attacking with all armies in the source country
        if ((l_CountryFrom.getD_NumberOfArmies() - d_NumberOfArmies) < 1) {
            throw new Exception("Advance failed: " + d_CountryNameFrom + ",  one army must remain in source country");
        }
        //check if source and destination countries are adjacent
        if (!l_CountryFrom.getD_Neighbours().contains(l_CountryTo))
            throw new Exception("Advance failed: source and destination countries are not adjacent!");
        else {
            // check if destination country is owned by the current player, then move armies to the destination country
            if (l_CurrentPlayer.getD_CountriesOwned().contains(l_CountryTo)) {
                // increase armies in source country
                l_CountryTo.setD_NumberOfArmies(l_CountryTo.getD_NumberOfArmies() + d_NumberOfArmies);
                // decrease armies in destination country
                l_CountryFrom.setD_NumberOfArmies(l_CountryFrom.getD_NumberOfArmies() - d_NumberOfArmies);
                Console.displayMsg("Success: " + l_CurrentPlayer.getD_Name() + " advanced " + d_NumberOfArmies + " " +
                        "armies" +
                        " from " + d_CountryNameFrom + " to " + d_CountryNameTo);
                d_GameEngine.getD_LogEntryBuffer().setD_log("Success: " + l_CurrentPlayer.getD_Name() + " advanced " + d_NumberOfArmies + " armies" +
                        " from " + d_CountryNameFrom + " to " + d_CountryNameTo);
                d_GameEngine.getD_LogEntryBuffer().notifyObservers(d_GameEngine.getD_LogEntryBuffer());
            } else {
                //check if the enemy is a diplomatic ally then current player can not attack his ally
                if (l_CurrentPlayer.isDiplomaticPlayer(l_CurrentPlayer,
                        d_GameEngine.findPlayer(l_CountryTo.getD_CurrentOwner()))) {
                    throw new Exception("Cannot attack a diplomatic ally's country");
                }
                // perform battle
                else {
                    l_CountryFrom.setD_NumberOfArmies(l_CountryFrom.getD_NumberOfArmies() - d_NumberOfArmies);
                    int l_SuccessAttack = 0;
                    int l_SuccessDefend = 0;
                    //calculate number of attacks succeeded
                    for (int l_Index = 0; l_Index < d_NumberOfArmies; l_Index++) {
                        int l_Random = (int) (Math.random() * 100);
                        if (l_Random <= 60)
                            l_SuccessAttack++;
                    }
                    //calculate number of defends succeeded
                    for (int l_Index = 0; l_Index < l_CountryTo.getD_NumberOfArmies(); l_Index++) {
                        int l_Random = (int) (Math.random() * 100);
                        if (l_Random <= 70)
                            l_SuccessDefend++;
                    }
                    int l_DefenderArmiesSurvived = l_SuccessAttack >= l_CountryTo.getD_NumberOfArmies() ? 0 :
                            l_CountryTo.getD_NumberOfArmies() - l_SuccessAttack;
                    int l_AttackerArmiesSurvived = l_SuccessDefend >= d_NumberOfArmies ? 0 :
                            d_NumberOfArmies - l_SuccessDefend;

                    //check if attacker was able to kill all armies in destination country and that he has armies
                    // survived
                    // the battle
                    if (l_DefenderArmiesSurvived == 0 && l_AttackerArmiesSurvived != 0) {
                        l_CountryTo.setD_NumberOfArmies(l_AttackerArmiesSurvived);
                        Console.displayMsg("Success: " + l_CurrentPlayer.getD_Name() + " has conquered " + d_CountryNameTo
                                + ", moving " + l_AttackerArmiesSurvived + " to " + d_CountryNameTo +
                                "\nbattle result: attacker: " + l_AttackerArmiesSurvived + ", defender: " + l_DefenderArmiesSurvived);
                        //change the owner of the destination country, add the destination country to current player
                        // country list
                        // and remove it from the old owner list
                        d_GameEngine.findPlayer(l_CountryTo.getD_CurrentOwner()).removeCountryOwned(l_CountryTo);
                        l_CountryTo.setD_CurrentOwner(l_CurrentPlayer.getD_Name());
                        l_CurrentPlayer.addCountryOwned(l_CountryTo);
                        // log
                        d_GameEngine.getD_LogEntryBuffer().setD_log("Success: " + l_CurrentPlayer.getD_Name() + " has conquered " + d_CountryNameTo
                                + ", moving " + l_AttackerArmiesSurvived + " to " + d_CountryNameTo +
                                "\nbattle result: attacker: " + l_AttackerArmiesSurvived + ", defender: " + l_DefenderArmiesSurvived);
                        d_GameEngine.getD_LogEntryBuffer().notifyObservers(d_GameEngine.getD_LogEntryBuffer());
                    } else {
                        l_CountryTo.setD_NumberOfArmies(l_DefenderArmiesSurvived);
                        l_CountryFrom.setD_NumberOfArmies(l_CountryFrom.getD_NumberOfArmies() + l_AttackerArmiesSurvived);
                        Console.displayMsg("Success: " + l_CurrentPlayer.getD_Name() + " has lost the battle" +
                                "\nbattle result: attacker: " + l_AttackerArmiesSurvived + ", defender: " + l_DefenderArmiesSurvived);
                        // log
                        d_GameEngine.getD_LogEntryBuffer().setD_log("Success: " + l_CurrentPlayer.getD_Name() + " has" +
                                " lost the battle, " +
                                l_AttackerArmiesSurvived + " : " + l_DefenderArmiesSurvived);
                        d_GameEngine.getD_LogEntryBuffer().notifyObservers(d_GameEngine.getD_LogEntryBuffer());
                    }
                }
            }
        }
    }

    @Override
    public void reset() {

    }

}
