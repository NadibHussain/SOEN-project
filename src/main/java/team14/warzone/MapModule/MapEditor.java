package team14.warzone.MapModule;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.Stack;

public class MapEditor {

    public Map d_loadedMap;

    public MapEditor() {

    }

    /**
     * @param p_fileName
     * @return Map
     */

    public void loadMap(String p_fileName) {
        Map l_map = new Map();
        try {
            File myObj = new File(p_fileName);
            Scanner myReader = new Scanner(myObj);
            while (myReader.hasNextLine()) {
                String data = myReader.nextLine();
                if (data.equals("[countries]")) {
                    while (true) {
                        String l_line = myReader.nextLine();
                        ArrayList<Continent> l_continentList = l_map.getD_continents();
                        if (l_line.equals("")) {
                            break;
                        } else {
                            String[] l_country_array = l_line.split(" ");
                            String continentName = "";
                            for (Continent l_conrinent : l_continentList) {
                                if (l_conrinent.getID() == Integer.parseInt(l_country_array[2])) {
                                    continentName = l_conrinent.getName();
                                }
                            }
                            l_map.addCountry(l_country_array[1], Integer.parseInt(l_country_array[0]), continentName);
                        }
                    }
                } else if (data.equals("[continents]")) {
                    int id = 1;
                    while (true) {
                        String l_line = myReader.nextLine();
                        if (l_line.equals("")) {
                            break;
                        } else {
                            String[] l_continent_array = l_line.split(" ");
                            l_map.addContinent(l_continent_array[0], id, Integer.parseInt(l_continent_array[1]));
                            id++;
                        }
                    }
                } else if (data.equals("[borders]")) {
                    ArrayList<Country> l_countires = l_map.getD_countries();
                    int l_index = 0;
                    while (myReader.hasNextLine()) {
                        String l_line = myReader.nextLine();
                        if (l_line.equals("")) {
                            break;
                        } else {
                            String[] l_neighbour_array = l_line.split(" ");
                            ArrayList<Country> l_neighbourID_array = new ArrayList<Country>();
                            for (int x = 1; x < l_neighbour_array.length; x++) {
                                for (int j = 0; j < l_countires.size(); j++) {
                                    if (l_countires.get(j).getID() == Integer.parseInt(l_neighbour_array[x])) {
                                        l_neighbourID_array.add(l_countires.get(j));
                                    }
                                }
                            }
                            l_countires.get(l_index).setD_neighbours(l_neighbourID_array);
                            l_index++;
                        }
                    }
                }

            }
            myReader.close();
        } catch (FileNotFoundException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
        this.d_loadedMap = l_map;

    }

    /**
     * @param p_fileName
     */
    public void saveMap(String p_fileName) {

    }

    /**
     * @author tanzia-ahmed
     * @param p_map
     * @return boolean
     */
    public boolean validateMap(Map p_map) {
        ArrayList<Country> l_countries = p_map.getD_countries();
        ArrayList<Continent> l_mContinents = p_map.getD_continents();

        boolean l_connected = false;
        boolean l_hasContinent = false;

        /**
         * checking if the map is connected, all countries belong to at least one
         * continent, all continents have at least one country
         */
        Stack<Integer> l_stackNodes = new Stack<Integer>();
        Stack<String> l_stackContinents = new Stack<String>();
        for (int l_aCountryIndex = 0; l_aCountryIndex < l_countries.size(); l_aCountryIndex++) {
            for (int l_aNeighbourIndex = 0; l_countries.get(l_aCountryIndex).getD_neighbours()
                    .size() > l_aNeighbourIndex; l_aNeighbourIndex++) {
                if(!(l_stackNodes.contains(l_countries.get(l_aCountryIndex).getD_neighbours().get(l_aNeighbourIndex).getID())))
                l_stackNodes.push(l_countries.get(l_aCountryIndex).getD_neighbours().get(l_aNeighbourIndex).getID());

            }
            if (!(l_stackContinents.contains(l_countries.get(l_aCountryIndex).getContinentName()))) {
                l_stackContinents.push(l_countries.get(l_aCountryIndex).getContinentName());
            }

            if (l_countries.get(l_aCountryIndex).getContinentName().isEmpty()) {
                System.out.println(
                        l_countries.get(l_aCountryIndex).getName() + " country does not belong to any continent.");
                return false;
            } else {
                l_hasContinent = true;
            }

        }

        if (l_stackContinents.size() != l_mContinents.size()) {
            System.out.println("A continent without a country found.");
            return false;
        }
        if (l_stackNodes.size() == l_countries.size())
            l_connected = true;
        if (l_connected && l_hasContinent)
            return true;
        else {
            System.out.println("The map is not connected.");
            return false;
        }

    }

    /**
     * Gets Map
     * 
     * @return loaded map
     */
    public Map getD_loadedMap() {
        return d_loadedMap;
    }

}
