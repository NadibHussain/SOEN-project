package team14.warzone.GameEngine.Commands;

import team14.warzone.MapModule.Country;

public class Advance implements IOrder {
    //advance countrynamefrom countynameto numarmies
    private Country d_CountryNameFrom;
    private Country d_CountryNameTo;

    public Advance(Country d_CountryNameFrom, Country d_CountryNameTo, int d_NumOfArmies) {
        this.d_CountryNameFrom = d_CountryNameFrom;
        this.d_CountryNameTo = d_CountryNameTo;
        this.d_NumOfArmies = d_NumOfArmies;
    }

    private int d_NumOfArmies;
    /**
     * Method to execute the command
     */
    @Override
    public void execute() {

    }
}
