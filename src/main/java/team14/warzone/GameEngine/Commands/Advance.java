package team14.warzone.GameEngine.Commands;

import team14.warzone.Console.Console;
import team14.warzone.GameEngine.GameEngine;
import team14.warzone.GameEngine.Player;
import team14.warzone.MapModule.Country;
import team14.warzone.MapModule.Map;

public class Advance extends Order {
    private String d_CountryNameFrom;
    private String d_CountryNameTo;
    private int d_NumberOfArmies;

    public Advance(String p_CountryNameFrom, String p_CountryNameTo, int p_NumberOfArmies, GameEngine p_GameEngine) {
        this.d_CountryNameFrom = p_CountryNameFrom;
        this.d_CountryNameTo = p_CountryNameTo;
        this.d_NumberOfArmies = p_NumberOfArmies;
        this.d_GameEngine = p_GameEngine;
    }

    /**
     * Method to execute the command
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
                    l_CountryFrom.getD_CountryID());
        }
        // check if destination country exists
        Country l_CountryTo = l_LoadedMap.findCountry(d_CountryNameTo);
        if (l_CountryTo == null) {
            throw new Exception("Advance failed: destination country does not exist");
        }

        //check if source and destination countries are adjacent
        if(!l_CountryFrom.getD_Neighbours().contains(l_CountryTo))
            throw new Exception("Advance failed: source and destination countries are not adjacent!");
        else{
            // check if destination country is owned by the player, then move armies to the destination country
            if (l_CurrentPlayer.getD_CountriesOwned().contains(l_CountryTo)) {
                // increase armies in source country
                l_CountryTo.setD_NumberOfArmies(l_CountryTo.getD_NumberOfArmies() - d_NumberOfArmies);
                // decrease armies in destination country
                l_CountryFrom.setD_NumberOfArmies(l_CountryFrom.getD_NumberOfArmies() + d_NumberOfArmies);
                Console.displayMsg("Success: " + l_CurrentPlayer.getD_Name() + " advanced " + d_NumberOfArmies + " armies" +
                        " from " + d_CountryNameFrom + " to " + d_CountryNameTo);
            }
            // perform battle
            // An attack is simulated by the following battle simulation mechanism:
            // First, the attacking player decides how many armies are involved.
            // Then, each attacking army unit involved has 60% chances of killing one defending army. if my random number < 60% => killing succeeds
            // At the same time, each defending army unit has 70% chances of killing one attacking army unit.
            // If all the defender's armies are eliminated, the attacker captures the territory.
            // The attacking army units that survived the battle then occupy the conquered territory
            // int r = (int) (Math.random() * (upper - lower)) + lower;
            else{
                int l_AttackingArmies = 0;
                int l_DefendingArmies = 0;
                for(int l_Index = 0; l_Index < d_NumberOfArmies; l_Index++){
                    int l_Random = (int) (Math.random() * 100);
                    if(l_Random <= 60)
                        l_AttackingArmies++;
                }
                for(int l_Index = 0; l_Index < l_CountryTo.getD_NumberOfArmies(); l_Index++){
                    int l_Random = (int) (Math.random() * 100);
                    if(l_Random <= 70)
                        l_DefendingArmies++;
                }
                if(l_DefendingArmies == 0 && l_AttackingArmies != 0){
                    l_CountryTo.setD_NumberOfArmies(l_AttackingArmies);
                    Console.displayMsg("Success: " + l_CurrentPlayer.getD_Name() + " has conquered " + d_CountryNameTo
                            + ", moving " + l_AttackingArmies + " to " + d_CountryNameTo);
                }
                else{
                    l_CountryTo.setD_NumberOfArmies(l_DefendingArmies);
                    l_CountryFrom.setD_NumberOfArmies(l_CountryFrom.getD_NumberOfArmies() - d_NumberOfArmies + l_AttackingArmies);
                    Console.displayMsg("Success: " + l_CurrentPlayer.getD_Name() + " has lost the battle, " +
                            l_AttackingArmies + " : " + l_DefendingArmies);
                }
            }
        }
    }
}
