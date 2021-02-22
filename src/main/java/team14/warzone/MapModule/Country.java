package team14.warzone.MapModule;

import java.util.ArrayList;

/**
 * This class consists the information about the country
 * @author razashaik
 * @version 1.0
 */

public class Country {
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
     * list of the country object of neighbours
     */
    private ArrayList<Country> d_Neighbours = new ArrayList<>();

    /**
     * Constructor for Country
     * @param p_CountryIntID unique integer ID
     * @param p_CountryID unique ID (name)
     * @param p_CountryContinentID Continent ID
     * @param p_CurrentOwner Current Owner
     * @param p_NumberOfArmies Number of Armies
     */
    public Country(int p_CountryIntID, String p_CountryID, String p_CountryContinentID, String p_CurrentOwner, int p_NumberOfArmies){
        this.d_CountryIntID = p_CountryIntID;
        this.d_CountryID = p_CountryID;
        this.d_CountryContinentID = p_CountryContinentID;
        this.d_CurrentOwner = p_CurrentOwner;
        this.d_NumberOfArmies = p_NumberOfArmies;
    }

    public Country(Country country) {
        this(country.getD_CountryIntID(), country.getD_CountryID(),country.getD_CountryContinentID(), country.getD_CurrentOwner(), country.getNumberOfArmies());
    }

    /**
     * Returns the integer ID
     * @return An int with ID
     */
    public int getD_CountryIntID() {
        return d_CountryIntID;
    }

    /**
     * Sets the integer ID
     * @param p_CountryIntID int with ID
     */
    public void setD_CountryIntID(int p_CountryIntID) {
        this.d_CountryIntID = p_CountryIntID;
    }

    /**
     * Returns the CountryID
     * @return A String with ID
     */
    public String getD_CountryID() {
        return d_CountryID;
    }

    /**
     * Sets the CountryID
     * @param p_CountryID String with ID
     */
    public void setD_CountryID(String p_CountryID) {
        this.d_CountryID = p_CountryID;
    }

    /**
     * Returns the continent ID which contains the country
     * @return A string with continent ID
     */
    public String getD_CountryContinentID() {
        return d_CountryContinentID;
    }


    /**
     * Sets the country continent ID which contains the country
     * @param p_CountryContinentID String with Continent ID which contains the country
     */
    public void setD_CountryContinentID(String p_CountryContinentID) {
        this.d_CountryContinentID = p_CountryContinentID;
    }

    /**
     * Returns the current player who owns the country
     * @return A string with name of player
     */
    public String getD_CurrentOwner() {
        return d_CurrentOwner;
    }

    /**
     * Sets the current owner name
     * @param p_CurrentOwner with current player who owns the country
     */
    public void setD_CurrentOwner(String p_CurrentOwner) {
        this.d_CurrentOwner = p_CurrentOwner;
    }

    /**
     * Returns the number of armies on the country
     * @return An int with number of armies
     */
    public int getNumberOfArmies() {
        return d_NumberOfArmies;
    }

    /**
     * Sets the number of armies on the country
     * @param p_NumberOfArmies with current player who owns the country
     */
    public void setNumberOfArmies(int p_NumberOfArmies) {
        this.d_NumberOfArmies = p_NumberOfArmies;
    }

    /**
     * Print Country
     */
    public String printCountry () {
        return String.format("%d %s %s %s %d",d_CountryIntID,  d_CountryID, d_CountryContinentID, d_CurrentOwner, d_NumberOfArmies);
    }

    /**
     * get neighbour list
     */
    public ArrayList<Country> getd_Neighbours() {
        return d_Neighbours;
    }

    /**
     * set neighbour list
     * @param d_Neighbours array list of the country object of the neighbour
     */
    public void setd_Neighbours(ArrayList<Country> d_Neighbours) {
        this.d_Neighbours = d_Neighbours;
    }

    public boolean addNeighbour(Country country) {

        for (var i = 0; i < d_Neighbours.size(); i++ ) {
            if (d_Neighbours.get(i).getD_CountryID() == country.getD_CountryID()) {
                return false;
            }
        };
        d_Neighbours.add(new Country(country));
        return true;
    }

    public boolean removeNeighbour(String  p_ID) {

        for (var i = 0; i < d_Neighbours.size(); i++) {
            if (d_Neighbours.get(i).getD_CountryID() == p_ID) {
                d_Neighbours.removeIf(c -> c.getD_CountryID() == p_ID);
                return true;
            }
        }
        return false;
    }

    @Override
    public String toString() {
        String neighbour_list = "[ ";
        for (Country l_country: d_Neighbours) {
            neighbour_list += l_country.getD_CountryIntID()+ ", ";
        }
        neighbour_list += " ]";
        return "Country{" +
                "Int ID=" + d_CountryIntID +
                "," + "Name ID=" + d_CountryID +
                ", d_CurrentOwner='" + d_CurrentOwner + '\'' +
                ", d_Neighbours=" + neighbour_list +
                '}';
    }
}