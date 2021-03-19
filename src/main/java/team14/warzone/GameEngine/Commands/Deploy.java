package team14.warzone.GameEngine.Commands;

import team14.warzone.MapModule.Country;

public class Deploy implements IOrder {
    private Country d_TargetCountry;
    private int d_NumberOfArmies;

    public Deploy(Country p_TargetCountry, int p_NumberOfArmies) {
        this.d_TargetCountry = p_TargetCountry;
        this.d_NumberOfArmies = p_NumberOfArmies;
    }

    /**
     * Method to execute the command
     */
    @Override
    public void execute() {

    }
}
