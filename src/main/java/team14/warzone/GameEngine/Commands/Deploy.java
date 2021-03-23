package team14.warzone.GameEngine.Commands;

import team14.warzone.Console.Console;
import team14.warzone.GameEngine.GameEngine;
import team14.warzone.GameEngine.Player;
import team14.warzone.MapModule.Country;
import team14.warzone.MapModule.Map;

public class Deploy extends Order {
    private String d_TargetCountry;
    private int d_NumberOfArmies;

    public Deploy(String p_TargetCountry, int p_NumberOfArmies, GameEngine p_GameEngine) {
        this.d_TargetCountry = p_TargetCountry;
        this.d_NumberOfArmies = p_NumberOfArmies;
        this.d_GameEngine = p_GameEngine;
        p_GameEngine.getD_CurrentPlayer().increaseArmiesOrderedToBeDeployed(p_NumberOfArmies);
    }

    /**
     * Method to execute the command
     */
    @Override
    public void execute() throws Exception {
        // check if numberOfArmies is more than what he has
        Player l_CurrentPlayer = d_GameEngine.getD_CurrentPlayer();
        Map l_LoadedMap = d_GameEngine.getD_LoadedMap();
        if (l_CurrentPlayer.getD_TotalNumberOfArmies() < d_NumberOfArmies)
            throw new Exception("Deploy failed: " + l_CurrentPlayer.getD_Name() + " has " +
                    l_CurrentPlayer.getD_TotalNumberOfArmies() + " < " + d_NumberOfArmies);
        // check if country is owned by the player
        Country l_CountryToDeployIn = l_LoadedMap.findCountry(d_TargetCountry);
        if (l_CountryToDeployIn == null) {
            throw new Exception("Deploy failed: country does not exist");
        }
        if (!l_CurrentPlayer.getD_CountriesOwned().contains(l_CountryToDeployIn)) {
            throw new Exception("Deploy failed: " + l_CurrentPlayer.getD_Name() + " does not own " +
                    l_CountryToDeployIn.getD_CountryID());
        }

        // increase armies in country
        l_CountryToDeployIn.setD_NumberOfArmies(l_CountryToDeployIn.getD_NumberOfArmies() + d_NumberOfArmies);
        // decrease army from player
        l_CurrentPlayer.setD_TotalNumberOfArmies(l_CurrentPlayer.getD_TotalNumberOfArmies() - d_NumberOfArmies);
        Console.displayMsg("Success: " + l_CurrentPlayer.getD_Name() + " deployed " + d_NumberOfArmies + " armies" +
                " in " + d_TargetCountry);
        // decrease armies ordered to be deployed count in player
        l_CurrentPlayer.decreaseArmiesOrderedToBeDeployed(d_NumberOfArmies);
    }
}
