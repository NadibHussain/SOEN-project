package team14.warzone.MapModule;

import java.util.ArrayList;
import java.util.Iterator;

/**
 * This class consists the information about the map
 * @author NadibHussain
 * @author razashaik
 * @version 1.0
 */

public class Map {

    /**
     *  Array List of Continents defined in our map
     */
    public ArrayList<Continent> d_continents = new ArrayList<Continent>();
    /**
     *  Array List of Countries defined in our map
     */
    public ArrayList<Country> d_countries = new ArrayList<Country>();

    public Map(){

    }

    public void showMap() {

    }
    
    /** 
     * @param p_name Name of country to be added
     * @param p_ID ID of country to be added
     * @param p_continentName Continent to which the country belongs to
     */
    public void addCountry(String p_name, int p_ID, String p_continentName){
        Country l_country = new Country(p_name,p_ID,p_continentName,"",0);
        d_countries.add(l_country);
    }
    
    /** 
     * @param p_name Name of the country to be removed
     */
    public void removeCountry(String p_name){
        Iterator itr = d_countries.iterator();
        while (itr.hasNext())
        {
            Country l_country = (Country) itr.next();
            if (l_country.getName().equals(p_name))
                itr.remove();
        }
    }

    
    /** 
     * @param p_name Name of the continent to be added
     * @param p_ID ID of the continent to be added
     * @param p_controllValue Control Value of the continent
     */
    public void addContinent(String p_name, int p_ID, int p_controllValue){
        Continent l_continent = new Continent(p_name,p_ID,p_controllValue);
        d_continents.add(l_continent);

    }
    
    /** 
     * @param p_name Name of the continent to be removed
     */
    public void removeContinent(String p_name){
        Iterator itr = d_continents.iterator();
        Iterator itr1 = d_countries.iterator();
        while (itr.hasNext())
        {
            Continent l_continent = (Continent) itr.next();
            if (l_continent.getName().equals(p_name))
                itr.remove();
        }
        while (itr1.hasNext())
        {
            Country l_country = (Country) itr1.next();
            if (l_country.getContinentName().equals(p_name))
                itr1.remove();
        }
    }

    /**
     *
     * @param p_CountryID Country to which the neighbour is to be added
     * @param p_neighbourID Name of the neighbour country to be added
     */
    public void addNeighbour(Country p_CountryID, Country p_neighbourID) {
        for (int i = 0; i < d_countries.size(); i++ ) {
            if (d_countries.get(i).getID() == p_CountryID.getID()) {
                d_countries.get(i).addNeighbour(p_neighbourID);
            }
            if (d_countries.get(i).getID() == p_neighbourID.getID()) {
                d_countries.get(i).addNeighbour(p_CountryID);
            }
        }
    }

    /**
     *
     * @param p_CountryID Country from which the neighbour is to be removed
     * @param p_neighbourID Name of the neighbour to be removed
     */
    public void removeNeighbour(Country p_CountryID, Country p_neighbourID) {
        for (int i = 0; i < d_countries.size(); i++ ) {
            if (d_countries.get(i).getID() == p_CountryID.getID()) {
                d_countries.get(i).removeNeighbour(p_neighbourID);
            }
            if (d_countries.get(i).getID() == p_neighbourID.getID()) {
                d_countries.get(i).removeNeighbour(p_CountryID);
            }
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
