package team14.warzone.MapModule;

import team14.warzone.GameEngine.Player;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * This class consists the information about the continent
 *
 * @author razashaik
 * @version 1.0
 */

public class Continent implements Serializable {

    /**
     * Unique integer ID of continent
     */
    private int d_ContinentIntID;
    /**
     * The unique ID of continent (in our case - name)
     */
    private String d_ContinentID;
    /**
     * Control Value of continent
     */
    private int d_ControlValue;

    /**
     * Arraylist containing a record of owners
     */
    private ArrayList<Player> d_CurrentOwners;

    /**
     * Constructor for Continent
     *
     * @param p_ContinentIntID unique integer ID
     * @param p_ContinentID    unique ID (name)
     * @param p_ControlValue   control value
     */
    public Continent(int p_ContinentIntID, String p_ContinentID, int p_ControlValue) {
        this.d_ContinentIntID = p_ContinentIntID;
        this.d_ContinentID = p_ContinentID;
        this.d_ControlValue = p_ControlValue;
    }

    /**
     * Returns the integer ID
     *
     * @return An int with ID
     */
    public int getD_ContinentIntID() {
        return d_ContinentIntID;
    }

    /**
     * Sets the integer ID
     *
     * @param p_ContinentIntID String with ID
     */
    public void setD_ContinentIntID(int p_ContinentIntID) {
        this.d_ContinentIntID = p_ContinentIntID;
    }

    /**
     * Returns the ID name
     *
     * @return A String with ID
     */
    public String getD_ContinentID() {
        return d_ContinentID;
    }

    /**
     * Sets the ID name
     *
     * @param p_ContinentID String with ID
     */
    public void setD_ContinentID(String p_ContinentID) {
        this.d_ContinentID = p_ContinentID;
    }

    /**
     * Returns the control value
     *
     * @return An int with control value
     */
    public int getD_ControlValue() {
        return d_ControlValue;
    }

    /**
     * Sets the control value
     *
     * @param p_ControlValue int with control value
     */
    public void setD_ControlValue(int p_ControlValue) {
        this.d_ControlValue = p_ControlValue;
    }

    /**
     * Getter
     * @return current owner
     */
    public ArrayList<Player> getD_CurrentOwners() {
        return d_CurrentOwners;
    }


    /**
     * Prints the continent
     *
     * @return Continent type with all attributes
     */
    public String printContinent() {
        return String.format("%d %s %d", this.d_ContinentIntID, this.d_ContinentID, this.d_ControlValue);
    }


    /**
     * @return String
     */
    @Override
    public String toString() {
        return "Continent{" +
                "Int ID=" + d_ContinentIntID +
                "," + "Name ID=" + d_ContinentID +
                "," + "Control Value=" + d_ControlValue +
                ", d_CurrentOwner='" + d_CurrentOwners + '\'' +
                '}';
    }
}