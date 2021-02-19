package team14.warzone.MapModule;

import java.util.ArrayList;

/**
 * This class consists the information about the country
 * @author razashaik
 * @version 1.0
 */

public class Country {

     /**
     * Unique ID of country (in our case - name)
     */
    private String ID;
    /**
     * Continent containing the country
     */
    private String l_ContinentID;
    /** 
     * Name of current owner
     */
    private String d_CurrentOwner;
    /**
     * Number of armies on the country
     */
    private int NUMOFARMIES;



    /**
     * list of the country object of neighbours
     */
    private ArrayList<Country> d_neighbours = new ArrayList<>();

    /**
     * Constructor for Country
     * @param ID unique ID (name)
     * @param l_ContinentID Continent ID
     * @param d_CurrentOwner Current Owner
     * @param NUMOFARMIES Number of Armies
     */
    public Country(String ID, String l_ContinentID, String d_CurrentOwner, int NUMOFARMIES){
        /*this.NAME = NAME;
        */
        this.ID = ID;
        this.l_ContinentID = l_ContinentID;
        this.d_CurrentOwner = d_CurrentOwner;
        this.NUMOFARMIES = NUMOFARMIES;
    }

    public Country(Country country) {
        this(country.getID(), country.getContinentID(), country.getD_CurrentOwner(), country.getNumberOfArmies());
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
     * @param p_CountryID String with ID
     */
    public void setID(String p_CountryID) {
        this.ID = p_CountryID;
    }

    /**
     * Returns the continent ID which contains the country
     * @return A string with continent ID
     */
    public String getContinentID() {
        return l_ContinentID;
    }

    /**
     * Sets the control value
     * @param p_ContinentID with control value
     */
    public void setControlValue(String p_ContinentID) {
        this.l_ContinentID = p_ContinentID;
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
        return NUMOFARMIES;
    }

    /**
     * Sets the number of armies on the country
     * @param p_NumberOfArmies with current player who owns the country
     */
    public void setNumberOfArmies(int p_NumberOfArmies) {
        this.NUMOFARMIES = p_NumberOfArmies;
    }

    /**
     * Print Country
     */
    public String printCountry () {
        return String.format("%s %s %s %d", ID, l_ContinentID, d_CurrentOwner, NUMOFARMIES);
    }

    /**
     * set neighbour list
     * @param d_neighbours array list of the country object of the neighbour
     */
    public void setD_neighbours(ArrayList<Country> d_neighbours) {
        this.d_neighbours = d_neighbours;
    }

    /**
     * get neighbour list
     */
    public ArrayList<Country> getD_neighbours() {
        return d_neighbours;
    }

    public boolean addNeighbour(Country country) {

        for (var i = 0; i < d_neighbours.size(); i++ ) {
            if (d_neighbours.get(i).getID() == country.getID()) {
                return false;
            }
        };
        d_neighbours.add(new Country(country));
        return true;
    }

    public boolean removeNeighbour(String  p_ID) {

        for (var i = 0; i < d_neighbours.size(); i++) {
            if (d_neighbours.get(i).getID() == p_ID) {
                d_neighbours.removeIf(c -> c.getID() == p_ID);
                return true;
            }
        }
        return false;
    }

    @Override
    public String toString() {
        return "Country{" +
                "ID=" + ID +
                ", d_CurrentOwner='" + d_CurrentOwner + '\'' +
                ", d_neighbours=" + d_neighbours +
                '}';
    }
}