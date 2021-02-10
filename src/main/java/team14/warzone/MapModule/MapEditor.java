package team14.warzone.MapModule;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.Stack;

public class MapEditor {

    public ArrayList<Object> d_loadedMap;

    public MapEditor() {

    }

    /**
     * @param p_fileName
     * @return Map
     */
    public Map loadMap(String p_fileName) {
        Map map = new Map();
        try {
            File myObj = new File(p_fileName);
            Scanner myReader = new Scanner(myObj);
            while (myReader.hasNextLine()) {
                String data = myReader.nextLine();
                if (data.equals("[countries]")) {
                    while (true) {
                        String l_line = myReader.nextLine();
                        ArrayList<Continent> l_continentList = map.getD_continents();
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
                            map.addCountry(l_country_array[1], Integer.parseInt(l_country_array[0]), continentName);
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
                            map.addContinent(l_continent_array[0], id, Integer.parseInt(l_continent_array[1]));
                            id++;
                        }
                    }
                } else if (data.equals("[borders]")) {
                    ArrayList<Country> l_countires = map.getD_countries();
                    int l_index = 0;
                    while (myReader.hasNextLine()) {
                        String l_line = myReader.nextLine();
                        if (l_line.equals("")) {
                            break;
                        } else {
                            String[] l_neighbour_array = l_line.split(" ");
                            ArrayList<String> l_neighbourID_array = new ArrayList<String>();
                            for (int x = 1; x < l_neighbour_array.length; x++) {
                                l_neighbourID_array.add(l_neighbour_array[x]);
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
        return map;

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
        boolean l_connected = false;
        boolean l_hasContinent = false;

        /**
         * checking if the map is connected
         */
        Stack<Integer> l_stack = new Stack<Integer>();
        for (int l_aCountryIndex = 0; l_aCountryIndex < l_countries.size(); l_aCountryIndex++) {
            for (int l_aNeighbourIndex = 0; l_countries.get(l_aCountryIndex).getD_neighbours()
                    .size() > l_aNeighbourIndex; l_aNeighbourIndex++) {
                // if(!(l_stack.contains(l_countries.get(l_aCountryIndex).getD_neighbours().get(l_aNeighbourIndex).getID())))
                // l_stack.push(l_countries.get(l_aCountryIndex).getD_neighbours().get(l_aNeighbourIndex).getID();

            }
            if (l_countries.get(l_aCountryIndex).getContinentName().isEmpty()){
                System.out.println(l_countries.get(l_aCountryIndex).getName()+" country does not belong to any continent.");
                return false;
            }else{
                l_hasContinent = true;
            }

        }
        if (l_stack.size() == l_countries.size())
            l_connected = true;
        if (l_connected && l_hasContinent)
            return true;
        else{
            System.out.println("The map is not connected.");
            return false;
        }
            
    }

}
