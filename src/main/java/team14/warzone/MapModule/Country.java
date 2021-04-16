package team14.warzone.MapModule;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * This class consists the information about the country
 *
 * @author razashaik
 * @version 1.0
 */

public class Country implements Serializable {
    /**
     * Unique integer ID of country
     */
    private int d_CountryIntID;
    /**
     * Unique ID of country (in our case - name)
     */
    private String d_CountryID;
    /**
     * Continent containing the country
     */
    private String d_CountryContinentID;
    /**
     * Name of current owner
     */
    private String d_CurrentOwner;
    /**
     * Number of armies on the country
     */
    private int d_NumberOfArmies;
    /**
     * A flag to tell if country was already been chosen in previous commands
     */
    private boolean d_UsedCountry;

    /**
     * list of the country object of neighbours
     */
    private ArrayList<Country> d_Neighbours = new ArrayList<>();

    /**
     * Constructor for Country
     *
     * @param p_CountryIntID       unique integer ID
     * @param p_CountryID          unique ID (name)
     * @param p_CountryContinentID Continent ID
     * @param p_CurrentOwner       Current Owner
     * @param p_NumberOfArmies     Number of Armies
     */
    public Country(int p_CountryIntID, String p_CountryID, String p_CountryContinentID, String p_CurrentOwner,
                   int p_NumberOfArmies) {
        this.d_CountryIntID = p_CountryIntID;
        this.d_CountryID = p_CountryID;
        this.d_CountryContinentID = p_CountryContinentID;
        this.d_CurrentOwner = p_CurrentOwner;
        this.d_NumberOfArmies = p_NumberOfArmies;
        this.d_UsedCountry = false;
    }

    /**
     * Initializing a country
     *
     * @param p_Country country type with all attributes
     */
    public Country(Country p_Country) {
        this(p_Country.getD_CountryIntID(), p_Country.getD_CountryID(), p_Country.getD_CountryContinentID(),
                p_Country.getD_CurrentOwner(), p_Country.getD_NumberOfArmies());
    }

    /**
     * Returns the integer ID
     *
     * @return An int with ID
     */
    public int getD_CountryIntID() {
        return d_CountryIntID;
    }

    /**
     * Sets the integer ID
     *
     * @param p_CountryIntID int with ID
     */
    public void setD_CountryIntID(int p_CountryIntID) {
        this.d_CountryIntID = p_CountryIntID;
    }

    /**
     * Returns the CountryID
     *
     * @return A String with ID
     */
    public String getD_CountryID() {
        return d_CountryID;
    }

    /**
     * Sets the CountryID
     *
     * @param p_CountryID String with ID
     */
    public void setD_CountryID(String p_CountryID) {
        this.d_CountryID = p_CountryID;
    }

    /**
     * Returns the continent ID which contains the country
     *
     * @return A string with continent ID
     */
    public String getD_CountryContinentID() {
        return d_CountryContinentID;
    }


    /**
     * Sets the country continent ID which contains the country
     *
     * @param p_CountryContinentID String with Continent ID which contains the country
     */
    public void setD_CountryContinentID(String p_CountryContinentID) {
        this.d_CountryContinentID = p_CountryContinentID;
    }

    /**
     * Returns the current player who owns the country
     *
     * @return A string with name of player
     */
    public String getD_CurrentOwner() {
        return d_CurrentOwner;
    }

    /**
     * Sets the current owner name
     *
     * @param p_CurrentOwner with current player who owns the country
     */
    public void setD_CurrentOwner(String p_CurrentOwner) {
        this.d_CurrentOwner = p_CurrentOwner;
    }

    /**
     * Returns the number of armies on the country
     *
     * @return An int with number of armies
     */
    public int getD_NumberOfArmies() {
        return d_NumberOfArmies;
    }

    /**
     * Sets the number of armies on the country
     *
     * @param p_NumberOfArmies with current player who owns the country
     */
    public void setD_NumberOfArmies(int p_NumberOfArmies) {
        this.d_NumberOfArmies = p_NumberOfArmies;
    }

    /**
     * Prints the country
     *
     * @return country type with all attributes
     */
    public String printCountry() {
        return String.format("%d %s %s %s %d", d_CountryIntID, d_CountryID, d_CountryContinentID, d_CurrentOwner,
                d_NumberOfArmies);
    }

    /**
     * Get neighbours method
     *
     * @return returns an array with all neighbours
     */
    public ArrayList<Country> getD_Neighbours() {
        return d_Neighbours;
    }

    /**
     * set neighbour list
     *
     * @param p_Neighbours array list of the country object of the neighbour
     */
    public void setD_Neighbours(ArrayList<Country> p_Neighbours) {
        this.d_Neighbours = p_Neighbours;
    }

    /**
     * Add neighbour method
     *
     * @param p_Country Country object to be added
     * @return boolean to check if valid country or not
     */
    public boolean addNeighbour(Country p_Country) {

        for (var i = 0; i < d_Neighbours.size(); i++) {
            if (d_Neighbours.get(i).getD_CountryID() == p_Country.getD_CountryID()) {
                return false;
            }
        }
        ;
        d_Neighbours.add(new Country(p_Country));
        return true;
    }

    /**
     * Remove neighbour method
     *
     * @param p_ID String ID of country which is to be removed from neighbours list
     * @return boolean to check if valid country
     */
    public boolean removeNeighbour(String p_ID) {

        for (var i = 0; i < d_Neighbours.size(); i++) {
            if (d_Neighbours.get(i).getD_CountryID() == p_ID) {
                d_Neighbours.removeIf(c -> c.getD_CountryID() == p_ID);
                return true;
            }
        }
        return false;
    }

    /**
     * A method to get usedCountry flag
     *
     * @return true if country is used, false otherwise
     */
    public boolean isD_UsedCountry() {
        return d_UsedCountry;
    }

    /**
     * A method to set usedCountry flag
     *
     * @param p_UsedCountry true/false
     */
    public void setD_UsedCountry(boolean p_UsedCountry) {
        this.d_UsedCountry = p_UsedCountry;
    }

    /**
     * @return String
     */
    @Override
    public String toString() {
        String l_NeighbourList = "[ ";
        for (Country l_Country : d_Neighbours) {
            l_NeighbourList += l_Country.getD_CountryIntID() + ", ";
        }
        l_NeighbourList += " ]";
        return "Country{" +
                "Int ID=" + d_CountryIntID +
                "," + "Name ID=" + d_CountryID +
                ", d_CurrentOwner='" + d_CurrentOwner + '\'' +
                ", d_neighbours=" + l_NeighbourList +
                '}';
    }
}