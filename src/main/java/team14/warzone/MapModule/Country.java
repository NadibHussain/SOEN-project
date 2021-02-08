package team14.warzone.MapModule;

/**
 * This class consists the information about the country
 * @author razashaik
 * @version 1.0
 */

public class Country {
    /**
     * The name of country
     */
    private String NAME;
    /**
     * Unique ID of country
     */
    private int ID;
    /**
     * Continent containing the country
     */
    private String ContinentName;
    /**
     * Name of current owner
     */
    private String d_CurrentOwner;
    /**
     * Number of armies on the country
     */
    private int NumberOfArmies;

    /**
     * Constructor for Country
     * @param NAME name
     * @param ID unique ID
     * @param ContinentName Continent Name
     * @param d_CurrentOwner Current Owner
     * @param NumberOfArmies Number of Armies
     */
    public Country(String NAME, int ID, String ContinentName, String d_CurrentOwner, int NumberOfArmies){
        this.NAME = NAME;
        this.ID = ID;
        this.ContinentName = ContinentName;
        this.d_CurrentOwner = d_CurrentOwner;
        this.NumberOfArmies = NumberOfArmies;
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
     * @param CountryName string with name
     */
    public void setName(String CountryName) {
        this.NAME = CountryName;
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
     * @param CountryID int with ID
     */
    public void setID(int CountryID) {
        this.ID = CountryID;
    }

    /**
     * Returns the continent name which contains the country
     * @return A string with continent name
     */
    public String getContinentName() {
        return ContinentName;
    }

    /**
     * Sets the control value
     * @param ContinentName with control value
     */
    public void setControlValue(String ContinentName) {
        this.ContinentName = ContinentName;
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
     * @param d_CurrentOwner with current player who owns the country
     */
    public void setD_CurrentOwner(String d_CurrentOwner) {
        this.d_CurrentOwner = d_CurrentOwner;
    }

    /**
     * Returns the number of armies on the country
     * @return An int with number of armies
     */
    public int getNumberOfArmies() {
        return NumberOfArmies;
    }

    /**
     * Sets the number of armies on the country
     * @param NumberOfArmies with current player who owns the country
     */
    public void setNumberOfArmies(int NumberOfArmies) {
        this.NumberOfArmies = NumberOfArmies;
    }

    /**
     * Print Country
     */
    public String printCountry () {
        return String.format("%s %d %s %s %d", NAME, ID, ContinentName, d_CurrentOwner, NumberOfArmies);
    }
}

