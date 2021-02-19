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
     * Unique integer ID of continent
     */
    private int l_ID;
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
     * @param l_ID unique integer ID
     * @param ID unique ID (name)
     * @param CONTROLVALUE control value
     */
    public Continent(int l_ID, String ID, int CONTROLVALUE){
        this.l_ID = l_ID;
        this.ID = ID;
        this.CONTROLVALUE = CONTROLVALUE;
    }

    /**
     * Returns the integer ID
     * @return An int with ID
     */
    public int getl_ID() {
        return l_ID;
    }

    /**
     * Sets the integer ID
     * @param p_Continentl_ID String with ID
     */
    public void setL_ID(int p_Continentl_ID) {
        this.l_ID = p_Continentl_ID;
    }

    /**
     * Returns the ID name
     * @return A String with ID
     */
    public String getID() {
        return ID;
    }

    /**
     * Sets the ID name
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
        return String.format("%d %s %d",this.l_ID,  this.ID, this.CONTROLVALUE);
    }


    @Override
    public String toString() {
        return "Continent{" +
                "l_ID=" + l_ID +
                "," + "ID=" + ID +
                "," + "Control Value=" + CONTROLVALUE +
                ", d_CurrentOwner='" + d_currentOwners + '\'' +
                '}';
    }
}