package team14.warzone.MapModule;

import team14.warzone.GameEngine.Player;

import java.util.ArrayList;

/**
 * This class consists the information about the continent
 * @author razashaik
 * @version 1.0
*/

public class Continent {
    /**
     * The name of the continent
     */
    private String  NAME;
    /**
     * The unique ID of continent
     */
    private int ID;
    /**
     * Control Value of continent
     */
    private int ControlValue;
    /**
     * Arraylist containing a record of owners
     */
    private ArrayList<Player> d_currentOwners;

    /**
     * Constructor for Continent
     * @param NAME name
     * @param ID unique ID
     * @param ControlValue control value
     */
    public Continent(String NAME, int ID, int ControlValue){
        this.NAME = NAME;
        this.ID = ID;
        this.ControlValue = ControlValue;
    }

    /**
     * Returns the name
     * @return A string with name
     */
    public String getName() {
        return NAME;
    }

    /**
     * Sets the name
     * @param ContinentName string with name
     */
    public void setName(String ContinentName) {
        this.NAME = ContinentName;
    }

    /**
     * Returns the ID
     * @return An int with ID
     */
    public int getID() {
        return ID;
    }

    /**
     * Sets the ID
     * @param ContinentID int with ID
     */
    public void setID(int ContinentID) {
        this.ID = ContinentID;
    }

    /**
     * Returns the control value
     * @return An int with control value
     */
    public int getControlValue() {
        return ControlValue;
    }

    /**
     * Sets the control value
     * @param p_ControlValue int with control value
     */
    public void setControlValue(int p_ControlValue) {
        this.ControlValue = p_ControlValue;
    }

    /**
     * Print continent
     */
    public String printContinent() {
        return String.format("%s %d %d", this.NAME, this.ID, this.ControlValue);
    }

}
