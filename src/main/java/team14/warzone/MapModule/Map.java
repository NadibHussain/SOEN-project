package team14.warzone.MapModule;

import javax.swing.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;

/**
 * This class consists the information and functionality of the map
 *
 * @author NadibHussain
 * @author razashaik
 * @version 1.0
 */

public class Map implements Serializable {

    /**
     * Array List of Continents defined in our map
     */

    public ArrayList<Continent> d_Continents = new ArrayList<Continent>();
    /**
     * Array List of Countries defined in our map
     */

    public ArrayList<Country> d_Countries = new ArrayList<Country>();

    /**
     * @author tanzia-ahmed
     * this method triggers a window displaying the map
     * each column represents - #SL, Country, Continent, Neighbours, Current Owner, No. of Armies
     * each row represents each country object
     *
     */
    public void showMap() {
        String[] l_ColumnNames = {"SL.","Country", "Continent","Neighbours", "Current Owner", "No. of Armies"};
        Object[][] l_Data = new Object [this.d_Countries.size()][6];

        for (int l_Index = 0; l_Index<this.d_Countries.size();l_Index++){
            l_Data[l_Index][0] = l_Index;
            l_Data[l_Index][1] = this.d_Countries.get(l_Index).getD_CountryID();
            l_Data[l_Index][2] = this.d_Countries.get(l_Index).getD_CountryContinentID();

            // Concating all neighbour names in 'neighbours' string
            String l_Neighbours = "";
            for(int l_NeighbourIndex = 0; l_NeighbourIndex < this.d_Countries.get(l_Index).getD_Neighbours().size(); l_NeighbourIndex++){
                l_Neighbours = l_Neighbours +this.d_Countries.get(l_Index).getD_Neighbours().get(l_NeighbourIndex).getD_CountryID();
                if(l_NeighbourIndex !=this.d_Countries.get(l_Index).getD_Neighbours().size()-1 ){
                    l_Neighbours = l_Neighbours + " ,";
                }
            }

            l_Data[l_Index][3] = l_Neighbours;
            l_Data[l_Index][4] = this.d_Countries.get(l_Index).getD_CurrentOwner();
            l_Data[l_Index][5] = this.d_Countries.get(l_Index).getD_NumberOfArmies();

        }
        JTable l_Table = new JTable(l_Data, l_ColumnNames);

        //Create and set up the window.
        JFrame l_Frame = new JFrame("Map");

        //Create and set up the content pane.
        l_Frame.add(new JScrollPane(l_Table));

        //Display the window.
        l_Frame.pack();
        l_Frame.setVisible(true);

    }

    /**
     * @param p_CountryID    ID of country to be added
     * @param p_ContinentID Continent to which the country belongs to
     */
    public void addCountry(String p_CountryID, String p_ContinentID) {
        int l_CountryIntID=0;
        if (d_Countries.size() > 0)
        {
            l_CountryIntID = d_Countries.get(d_Countries.size()-1).getD_CountryIntID()+1;

        }
        else{
            l_CountryIntID = 1;
        }
        Country l_Country = new Country(l_CountryIntID, p_CountryID, p_ContinentID, "", 0);

        for (Country d_country : d_Countries) {
            if (d_country.getD_CountryID().equals(p_CountryID)) {
                System.out.println("Country already exists");
                return;
            }
        }

        boolean l_Invalid = false;
        for (Continent d_continent : d_Continents) {
            if (d_continent.getD_ContinentID().equals(p_ContinentID)) {
                d_Countries.add(l_Country);
                l_Invalid = true;
                break;
            }
        }
        if (!l_Invalid) {
            System.out.println("Continent does not exist");

        }
    }


    /**
     * @param p_CountryID ID of the country to be removed
     */
    public void removeCountry(String p_CountryID) {
        Iterator l_Itr = d_Countries.iterator();

        boolean l_Found = false;
        while (l_Itr.hasNext()) {
            Country l_Country = (Country) l_Itr.next();
            l_Country.removeNeighbour(p_CountryID);
            if (l_Country.getD_CountryID().equals(p_CountryID)) {
                l_Found = true;
                l_Itr.remove();
            }
        }
        if (!l_Found) {
            System.out.println("Country does not exist");
        }
    }


    /**
     * @param p_ContinentID ID of the continent to be added in our case the name
     * @param p_ControlValue Control Value of the continent
     */
    public void addContinent(String p_ContinentID, int p_ControlValue) {
        int l_ContinentIntID=0;
        Iterator<Continent> l_Itr = d_Continents.iterator();
        if (d_Continents.size() > 0)
        {
            l_ContinentIntID = d_Continents.get(d_Continents.size()-1).getD_ContinentIntID()+1;

        }
        else {
            l_ContinentIntID = 1;
        }
        boolean l_Found = false;
        Continent l_Continent = new Continent(l_ContinentIntID, p_ContinentID, p_ControlValue);
        while (l_Itr.hasNext()) {
            Continent l_Cur = (Continent) l_Itr.next();
            if (l_Cur.getD_ContinentID() == p_ContinentID) {
                l_Found = true;

                System.out.println("Continent already exists");
                break;
            }
        }
        if (!l_Found) {
            d_Continents.add(l_Continent);
        }
    }


    /**
     * @param p_ContinentID Name of the continent to be removed
     */
    public void removeContinent(String p_ContinentID) {
        Iterator l_Itr = d_Continents.iterator();
        Iterator l_Itr1 = d_Countries.iterator();
        boolean l_Found = false;

        while (l_Itr.hasNext()) {
            Continent l_Continent = (Continent) l_Itr.next();
            if (l_Continent.getD_ContinentID().equals(p_ContinentID)) {
                l_Found = true;
                l_Itr.remove();
                break;
            }
        }
        while (l_Itr1.hasNext()) {
            Country l_Country = (Country) l_Itr1.next();
            if (l_Country.getD_CountryContinentID().equals(p_ContinentID)) {
                l_Itr1.remove();
            }
        }
        if (!l_Found) {
            System.out.println("Continent does not exist");
        }
    }


    /**
     * @param p_CountryID   Country to which the neighbour is to be added
     * @param p_NeighbourID Name of the neighbour country to be added
     */
    public void addNeighbour(String p_CountryID, String p_NeighbourID) {
        boolean l_Invalid1 = true;
        boolean l_Invalid2 = true;
        Country l_Country = findCountry(p_CountryID);
        Country l_Neighbour = findCountry(p_NeighbourID);
        for (Country l_CountryIndex : d_Countries) {
            if (l_CountryIndex.getD_CountryID().equals(l_Country.getD_CountryID())) {
                l_CountryIndex.addNeighbour(l_Neighbour);
                l_Invalid1 = false;
            }
            if (l_CountryIndex.getD_CountryID().equals(l_Neighbour.getD_CountryID())) {
                l_CountryIndex.addNeighbour(l_Country);
                l_Invalid2 = false;
            }
        }

        if (l_Invalid1 || l_Invalid2) {
            System.out.println("Invalid Country");
        }
    }

    /**
     * @param p_CountryID   Country from which the neighbour is to be removed
     * @param p_NeighbourID Name of the neighbour to be removed
     */
    public void removeNeighbour(String p_CountryID, String p_NeighbourID) {
        boolean l_Invalid1 = true;
        boolean l_Invalid2 = true;
        Country l_Country = findCountry(p_CountryID);
        Country l_Neighbour = findCountry(p_NeighbourID);
        for (Country l_CountryIndex : d_Countries) {
            if (l_CountryIndex.getD_CountryID().equals(l_Country.getD_CountryID())) {
                l_Invalid1 = !l_CountryIndex.removeNeighbour(l_Neighbour.getD_CountryID());
            }
            if (l_CountryIndex.getD_CountryID().equals(l_Neighbour.getD_CountryID())) {
                l_Invalid2 = !l_CountryIndex.removeNeighbour(l_Country.getD_CountryID());
            }
        }

        if (l_Invalid1 || l_Invalid2) {
            System.out.println("Invalid Neighbour country");
        }
    }

    /**
     * Returns an array list of all continents
     * @return Arraylist of continents
     */
    public ArrayList<Continent> getD_Continents() {
        return d_Continents;
    }


    /**
     * Returns an arraylist of all countries
     * @return Arraylist of countries
     */
    public ArrayList<Country> getD_Countries() {
        return d_Countries;
    }


    /**
     * Find a country using its name
     * @param p_CountryName String country name
     * @return String country name
     */
    public Country findCountry(String p_CountryName){
        //replace "-" with space in country name
        for (Country l_CountryIndex: d_Countries) {
            if(l_CountryIndex.getD_CountryID().equals(p_CountryName))
            {
                return l_CountryIndex;
            }
        }
        return null;
    }

    /**
     * Find Continent using its name
     * @param p_ContinetName String continent name
     * @return String continent name
     */
    public Continent findContinent(String p_ContinetName){
        for (Continent l_ContIndex: d_Continents) {
            if(l_ContIndex.getD_ContinentID().equals(p_ContinetName))
            {
                return l_ContIndex;
            }
        }
        return null;
    }

    /**
     * Finds List of country for a specific Continent
     * @param p_ContinentID Continent id
     * @return Arraylist of the countries
     */
    public ArrayList<Country> getCountryListOfContinent(String p_ContinentID){
        ArrayList<Country> l_CountryArrayList = new ArrayList<>();
        for (Country l_Country:d_Countries) {
            if (l_Country.getD_CountryContinentID().equals(p_ContinentID))
            {
                l_CountryArrayList.add(l_Country);
            }
        }
        return l_CountryArrayList;
    }
}
