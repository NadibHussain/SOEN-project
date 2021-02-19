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
     * The unique ID of continent (in our case - name)
     */
    private String ID;
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
     * @param ID unique ID
     * @param CONTROLVALUE control value
     */
    public Continent(String ID, int CONTROLVALUE){
        this.ID = ID;
        this.CONTROLVALUE = CONTROLVALUE;
    }

    /**
     * Returns the ID
     * @return A String with ID
     */
    public String getID() {
        return ID;
    }

    /**
     * Sets the ID
     * @param p_ContinentID String with ID
     */
    public void setID(String p_ContinentID) {
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
        return String.format("%s %d", this.ID, this.CONTROLVALUE);
    }

}
