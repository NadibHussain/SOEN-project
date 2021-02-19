package team14.warzone.MapModule;

import java.util.ArrayList;
import java.util.Iterator;

/**
 * This class consists the information and functionality of the map
 *
 * @author NadibHussain
 * @author razashaik
 * @version 1.0
 */

public class Map {

    /**
     * Array List of Continents defined in our map
     */

    public ArrayList<Continent> d_continents = new ArrayList<Continent>();
    /**
     * Array List of Countries defined in our map
     */

    public ArrayList<Country> d_countries = new ArrayList<Country>();
    /**
     * Auto increment counters for generating integer ID for countries and continents
     */
    private static int d_IdTracker = 0;
    private static int d_IdTracker1 = 0;


    public void showMap() {

    }

    /**
     * @param l_ID          integer ID of country
     * @param p_ID          ID of country to be added
     * @param p_continentID Continent to which the country belongs to
     */
    public void addCountry(int l_ID, String p_ID, String p_continentID) {
        l_ID = ++d_IdTracker1;
        Country l_country = new Country(l_ID, p_ID, p_continentID, "", 0);

        for (int j = 0; j < d_countries.size(); j++) {
            if (d_countries.get(j).getID() == p_ID) {
                System.out.println("Country already exists");
                return;
            }
        }

        boolean invalid = false;
        for (int i = 0; i < d_continents.size(); i++) {
            if (d_continents.get(i).getID().equals(p_continentID)) {
                d_countries.add(l_country);
                invalid = true;
                break;
            }
        }
        if (!invalid) {
            System.out.println("Continent does not exist");
        }
    }


    /**
     * @param p_ID ID of the country to be removed
     */
    public void removeCountry(String p_ID) {
        Iterator itr = d_countries.iterator();

        boolean found = false;
        while (itr.hasNext()) {
            Country l_country = (Country) itr.next();
            l_country.removeNeighbour(p_ID);
            if (l_country.getID().equals(p_ID)) {
                found = true;
                itr.remove();
            }
        }
        if (!found) {
            System.out.println("Country does not exist");
        }
    }


    /**
     * @param l_ID            integer ID of country
     * @param p_ID            ID of the continent to be added
     * @param p_controllValue Control Value of the continent
     */
    public void addContinent(int l_ID, String p_ID, int p_controllValue) {
        Iterator<Continent> itr = d_continents.iterator();
        l_ID = ++d_IdTracker;
        boolean found = false;
        Continent l_continent = new Continent(l_ID, p_ID, p_controllValue);
        while (itr.hasNext()) {
            Continent cur = (Continent) itr.next();
            if (cur.getID() == p_ID) {
                found = true;

                System.out.println("Continent already exists");
                break;
            }
        }
        if (!found) {
            d_continents.add(l_continent);
        }
    }


    /**
     * @param p_ID Name of the continent to be removed
     */
    public void removeContinent(String p_ID) {
        Iterator itr = d_continents.iterator();
        Iterator itr1 = d_countries.iterator();
        boolean found = false;

        while (itr.hasNext()) {
            Continent l_continent = (Continent) itr.next();
            if (l_continent.getID().equals(p_ID)) {
                found = true;
                itr.remove();
                break;
            }
        }
        while (itr1.hasNext()) {
            Country l_country = (Country) itr1.next();
            if (l_country.getContinentID().equals(p_ID)) {
                itr1.remove();
            }
        }
        if (!found) {
            System.out.println("Continent does not exist");
        }
    }


    /**
     * @param p_CountryID   Country to which the neighbour is to be added
     * @param p_neighbourID Name of the neighbour country to be added
     */
    public void addNeighbour(Country p_CountryID, Country p_neighbourID) {
        boolean invalid1 = true;
        boolean invalid2 = true;
        for (int i = 0; i < d_countries.size(); i++) {
            if (d_countries.get(i).getID() == p_CountryID.getID()) {
                d_countries.get(i).addNeighbour(p_neighbourID);
                invalid1 = false;
            }
            if (d_countries.get(i).getID() == p_neighbourID.getID()) {
                d_countries.get(i).addNeighbour(p_CountryID);
                invalid2 = false;
            }
        }

        if (invalid1 || invalid2) {
            System.out.println("Invalid Country");
        }
    }

    /**
     * @param p_CountryID   Country from which the neighbour is to be removed
     * @param p_neighbourID Name of the neighbour to be removed
     */
    public void removeNeighbour(Country p_CountryID, Country p_neighbourID) {
        boolean invalid1 = true;
        boolean invalid2 = true;
        for (int i = 0; i < d_countries.size(); i++) {
            if (d_countries.get(i).getID() == p_CountryID.getID()) {
                invalid1 = !d_countries.get(i).removeNeighbour(p_neighbourID.getID());
            }
            if (d_countries.get(i).getID() == p_neighbourID.getID()) {
                invalid2 = !d_countries.get(i).removeNeighbour(p_CountryID.getID());
            }
        }

        if (invalid1 || invalid2) {
            System.out.println("Invalid Neighbour country");
        }
    }

    /**
     * @return ArrayList<Continent>
     */
    public ArrayList<Continent> getD_continents() {
        return d_continents;
    }


    /**
     * @return ArrayList<Country>
     */
    public ArrayList<Country> getD_countries() {
        return d_countries;
    }
}
