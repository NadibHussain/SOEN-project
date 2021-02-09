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
    private String NAME;
    /**
     * The unique ID of continent
     */
    private int ID;
    /**
     * Control Value of continent
     */
    private int CONTROLVALUE;
    /**
     * Arraylist containing a record of owners
     */
    private ArrayList<Player> d_currentOwners;

    /**
     * Constructor for Continent
     * @param NAME name
     * @param ID unique ID
     * @param CONTROLVALUE control value
     */
    public Continent(String NAME, int ID, int CONTROLVALUE){
        this.NAME = NAME;
        this.ID = ID;
        this.CONTROLVALUE = CONTROLVALUE;
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
     * @param p_ContinentName string with name
     */
    public void setName(String p_ContinentName) {
        this.NAME = p_ContinentName;
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
     * @param p_ContinentID int with ID
     */
    public void setID(int p_ContinentID) {
        this.ID = p_ContinentID;
    }

    /**
     * Returns the control value
     * @return An int with control value
     */
    public int getControlValue() {
        return CONTROLVALUE;
    }

    /**
     * Sets the control value
     * @param p_ControlValue int with control value
     */
    public void setControlValue(int p_ControlValue) {
        this.CONTROLVALUE = p_ControlValue;
    }

    /**
     * Print continent
     */
    public String printContinent() {
        return String.format("%s %d %d", this.NAME, this.ID, this.CONTROLVALUE);
    }

}
