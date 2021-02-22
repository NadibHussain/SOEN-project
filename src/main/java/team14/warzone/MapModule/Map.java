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


    public void showMap() {

    }

    /**
     * @param p_CountryIntID integer ID of country
     * @param p_CountryID    ID of country to be added
     * @param p_ContinentID Continent to which the country belongs to
     */
    public void addCountry(int p_CountryIntID, String p_CountryID, String p_ContinentID) {
        if (d_countries.size() > 0)
        {
            p_CountryIntID = d_countries.get(d_countries.size()-1).getD_CountryIntID()+1;

        }
        else{
            p_CountryIntID = 1;
        }
        Country l_country = new Country(p_CountryIntID, p_CountryID, p_ContinentID, "", 0);

        for (int j = 0; j < d_countries.size(); j++) {
            if (d_countries.get(j).getD_CountryID() == p_CountryID) {
                System.out.println("Country already exists");
                return;
            }
        }

        boolean invalid = false;
        for (int i = 0; i < d_continents.size(); i++) {
            if (d_continents.get(i).getD_ContinentID().equals(p_ContinentID)) {
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
     * @param p_CountryID ID of the country to be removed
     */
    public void removeCountry(String p_CountryID) {
        Iterator itr = d_countries.iterator();

        boolean found = false;
        while (itr.hasNext()) {
            Country l_country = (Country) itr.next();
            l_country.removeNeighbour(p_CountryID);
            if (l_country.getD_CountryID().equals(p_CountryID)) {
                found = true;
                itr.remove();
            }
        }
        if (!found) {
            System.out.println("Country does not exist");
        }
    }


    /**
     * @param p_ContinentIntID integer ID of country
     * @param p_ContinentID ID of the continent to be added in our case the name
     * @param p_ControlValue Control Value of the continent
     */
    public void addContinent(int p_ContinentIntID, String p_ContinentID, int p_ControlValue) {
        Iterator<Continent> itr = d_continents.iterator();
        if (d_continents.size() > 0)
        {
            p_ContinentIntID = d_continents.get(d_continents.size()-1).getD_ContinentIntID()+1;

        }
        else {
            p_ContinentIntID = 1;
        }
        boolean found = false;
        Continent l_continent = new Continent(p_ContinentIntID, p_ContinentID, p_ControlValue);
        while (itr.hasNext()) {
            Continent cur = (Continent) itr.next();
            if (cur.getD_ContinentID() == p_ContinentID) {
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
     * @param p_ContinentID Name of the continent to be removed
     */
    public void removeContinent(String p_ContinentID) {
        Iterator itr = d_continents.iterator();
        Iterator itr1 = d_countries.iterator();
        boolean found = false;

        while (itr.hasNext()) {
            Continent l_continent = (Continent) itr.next();
            if (l_continent.getD_ContinentID().equals(p_ContinentID)) {
                found = true;
                itr.remove();
                break;
            }
        }
        while (itr1.hasNext()) {
            Country l_country = (Country) itr1.next();
            if (l_country.getD_CountryContinentID().equals(p_ContinentID)) {
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
    public void addNeighbour(String p_CountryID, String p_neighbourID) {
        boolean invalid1 = true;
        boolean invalid2 = true;
        Country l_country = findCountry(p_CountryID);
        Country l_neighbour = findCountry(p_neighbourID);
        for (Country d_country : d_countries) {
            if (d_country.getD_CountryID().equals(l_country.getD_CountryID())) {
                d_country.addNeighbour(l_country);
                invalid1 = false;
            }
            if (d_country.getD_CountryID().equals(l_neighbour.getD_CountryID())) {
                d_country.addNeighbour(l_neighbour);
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
    public void removeNeighbour(String p_CountryID, String p_neighbourID) {
        boolean invalid1 = true;
        boolean invalid2 = true;
        Country l_country = findCountry(p_CountryID);
        Country l_neighbour = findCountry(p_neighbourID);
        for (Country d_country : d_countries) {
            if (d_country.getD_CountryID().equals(l_country.getD_CountryID())) {
                invalid1 = !d_country.removeNeighbour(l_neighbour.getD_CountryID());
            }
            if (d_country.getD_CountryID().equals(l_neighbour.getD_CountryID())) {
                invalid2 = !d_country.removeNeighbour(l_country.getD_CountryID());
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


    /**
     * Finds country from the name
     * @return country object with a specific name
     */
    public Country findCountry(String p_countryName){

        for (Country l_country: d_countries) {
            if(l_country.getD_CountryID().equals(p_countryName))
            {
                return l_country;
            }
        }
        return null;
    }

    /**
     * Finds country from the name
     * @return continent object with a specific name
     */
    public Continent findContinent(String p_continetName){
        for (Continent l_continent: d_continents) {
            if(l_continent.getD_ContinentID().equals(p_continetName))
            {
                return l_continent;
            }
        }
        return null;
    }
}
