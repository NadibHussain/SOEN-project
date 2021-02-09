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
    public void addCountry(String p_name, int p_ID, String p_continentName){
        Country l_country = new Country(p_name,p_ID,p_continentName,"",0);
        d_countries.add(l_country);
    }
    public void removeCountry(String p_name){
        Iterator itr = d_countries.iterator();
        while (itr.hasNext())
        {
            Country l_country = (Country) itr.next();
            if (l_country.getName().equals(p_name))
                itr.remove();
        }
    }

    public void addContinent(String p_name, int p_ID, int p_controllValue){
        Continent l_continent = new Continent(p_name,p_ID,p_controllValue);
        d_continents.add(l_continent);

    }
    public void removeContinent(String p_name){

    }
    public void addNeighbour(String p_name){

    }
    public void removeNeighbour(String p_name){

    }
    public ArrayList<Continent> getD_continents() {
        return d_continents;
    }

    public ArrayList<Country> getD_countries() {
        return d_countries;
    }





}
