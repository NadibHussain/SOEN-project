package team14.warzone.MapModule;

import java.util.ArrayList;
import java.util.Iterator;

import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTable;

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

    /**
     * @author tanzia-ahmed
     * this method triggers a window displaying the map 
     * each column represents - #SL, Country, Continent, Neighbours, Current Owner, No. of Armies
     * each row represents each country object
     *
     */
    public void showMap() {
        String[] l_columnNames = {"SL.","Country", "Continent","Neighbours", "Current Owner", "No. of Armies"};
        Object[][] l_data = new Object [this.d_countries.size()][6];

        for (int l_index = 0; l_index<this.d_countries.size();l_index++){
            l_data[l_index][0] = l_index;
            l_data[l_index][1] = this.d_countries.get(l_index).getD_CountryID();
            l_data[l_index][2] = this.d_countries.get(l_index).getD_CountryContinentID();
            
            // Concating all neighbour names in 'neighbours' string
            String neighbours = "";
            for(int l_aNeighbourIndex = 0; l_aNeighbourIndex < this.d_countries.get(l_index).getD_neighbours().size(); l_aNeighbourIndex++){
                neighbours = neighbours +this.d_countries.get(l_index).getD_neighbours().get(l_aNeighbourIndex).getD_CountryID();
                if(l_aNeighbourIndex !=this.d_countries.get(l_index).getD_neighbours().size()-1 ){
                    neighbours = neighbours + " ,";
                }
            }

            l_data[l_index][3] = neighbours;
            l_data[l_index][4] = this.d_countries.get(l_index).getD_CurrentOwner();
            l_data[l_index][5] = this.d_countries.get(l_index).getNumberOfArmies();

        }
        JTable l_table = new JTable(l_data, l_columnNames);

        //Create and set up the window.
        JFrame frame = new JFrame("Map");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        //Create and set up the content pane.
        frame.add(new JScrollPane(l_table));
        
        //Display the window.
        frame.pack();
        frame.setVisible(true);

    }

    /**
     * @param p_CountryIntID integer ID of country
     * @param p_CountryID    ID of country to be added
     * @param p_ContinentID Continent to which the country belongs to
     */
    public void addCountry(int p_CountryIntID, String p_CountryID, String p_ContinentID) {
        p_CountryIntID = ++d_IdTracker1;
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
        p_ContinentIntID = ++d_IdTracker;
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
    public void addNeighbour(Country p_CountryID, Country p_neighbourID) {
        boolean invalid1 = true;
        boolean invalid2 = true;
        for (int i = 0; i < d_countries.size(); i++) {
            if (d_countries.get(i).getD_CountryID() == p_CountryID.getD_CountryID()) {
                d_countries.get(i).addNeighbour(p_neighbourID);
                invalid1 = false;
            }
            if (d_countries.get(i).getD_CountryID() == p_neighbourID.getD_CountryID()) {
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
            if (d_countries.get(i).getD_CountryID() == p_CountryID.getD_CountryID()) {
                invalid1 = !d_countries.get(i).removeNeighbour(p_neighbourID.getD_CountryID());
            }
            if (d_countries.get(i).getD_CountryID() == p_neighbourID.getD_CountryID()) {
                invalid2 = !d_countries.get(i).removeNeighbour(p_CountryID.getD_CountryID());
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
