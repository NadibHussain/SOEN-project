package team14.warzone.MapModule;

import java.util.ArrayList;
import java.util.Iterator;

/**
 * This class consists the information about the map
 * @author NadibHussain
 * @version 1.0
 */

public class Map {


    public ArrayList<Continent> d_continents = new ArrayList<Continent>();
    public ArrayList<Country> d_countries = new ArrayList<Country>();

    public Map(){

    }

    public void showMap() {

    }
    
    /** 
     * @param p_name
     * @param p_ID
     * @param p_continentName
     */
    public void addCountry(String p_name, int p_ID, String p_continentName){
        Country l_country = new Country(p_name,p_ID,p_continentName,"",0);
        d_countries.add(l_country);
    }
    
    /** 
     * @param p_name
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
     * @param p_name
     * @param p_ID
     * @param p_controllValue
     */
    public void addContinent(String p_name, int p_ID, int p_controllValue){
        Continent l_continent = new Continent(p_name,p_ID,p_controllValue);
        d_continents.add(l_continent);

    }
    
    /** 
     * @param p_name
     */
    public void removeContinent(String p_name){
        Iterator itr = d_continents.iterator();
        while (itr.hasNext())
        {
            Continent l_continent = (Continent) itr.next();
            if (l_continent.getName().equals(p_name))
                itr.remove();
        }
    }
    
    /** 
     * @param p_name
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
     * @param p_name
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
