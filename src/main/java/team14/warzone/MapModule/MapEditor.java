package team14.warzone.MapModule;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
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
                        if (l_line.length()>0 && l_line.charAt(0) == ';')
                        {
                            continue;
                        }
                        ArrayList<Continent> l_continentList = l_map.getD_continents();
                        if (l_line.equals("")) {
                            break;
                        } else {
                            String[] l_country_array = l_line.split(" ");
                            String continentName = "";
                            for (Continent l_conrinent : l_continentList) {
                                if (l_conrinent.getD_ContinentIntID() == Integer.parseInt(l_country_array[2])) {
                                    continentName = l_conrinent.getD_ContinentID();
                                    break;
                                }
                            }
                            l_map.addCountry(Integer.parseInt(l_country_array[0]),l_country_array[1],continentName);
                        }
                    }
                } else if (data.equals("[continents]")) {
                    int id = 1;
                    while (true) {
                        String l_line = myReader.nextLine();
                        if (l_line.length()>0 && l_line.charAt(0) == ';')
                        {
                            continue;
                        }
                        if (l_line.equals("")) {
                            break;
                        } else {
                            String[] l_continent_array = l_line.split(" ");
                            l_map.addContinent(id,l_continent_array[0], Integer.parseInt(l_continent_array[1]));
                            id++;
                        }
                    }

                } else if (data.equals("[borders]")) {
                    ArrayList<Country> l_countires = l_map.getD_countries();
                    int l_index = 0;
                    while (myReader.hasNextLine()) {
                        String l_line = myReader.nextLine();
                        if (l_line.charAt(0) == ';'&& l_line.length()>0)
                        {
                            continue;
                        }
                        if (l_line.equals("")) {
                            break;
                        } else {
                            String[] l_neighbour_array = l_line.split(" ");
                            ArrayList<Country> l_neighbourID_array = new ArrayList<Country>();
                            for (int x = 1; x < l_neighbour_array.length; x++) {
                                for (int j = 0; j < l_countires.size(); j++) {
                                    if (l_countires.get(j).getD_CountryIntID() == Integer.parseInt(l_neighbour_array[x])) {
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
     * This method is used to save a map in a text format
     * @param p_fileName
     */
    public void saveMap(String p_fileName) {
        String l_content = "This map was created from a SOEN-6441 Project \n \n";
        l_content+="[continents]\n";
        for (Continent l_continent:d_loadedMap.getD_continents()) {
            l_content += l_continent.getD_ContinentID()+" "+l_continent.getD_ControlValue()+"\n";
        }
        l_content+="\n[countries]\n";
        for (Country l_country:d_loadedMap.getD_countries()) {
            int l_continentIntId = -1;
            for (Continent l_continent: d_loadedMap.getD_continents()) {
                if (l_continent.getD_ContinentID().equals(l_country.getD_CountryContinentID()))
                {
                    l_continentIntId = l_continent.getD_ContinentIntID();
                }
            }
            l_content += l_country.getD_CountryIntID()+" "+"-"+" "+l_continentIntId+"\n";
        }

        l_content+="\n[borders]\n";
        for (Country l_country:d_loadedMap.getD_countries()) {
            l_content += l_country.getD_CountryIntID()+" ";
            for (Country l_neighbour:l_country.getD_neighbours()) {
                l_content += l_neighbour.getD_CountryIntID()+ " ";
            }
            l_content += "\n";
        }

        try {
            FileWriter l_writer = new FileWriter(p_fileName);
            l_writer.write(l_content);
            l_writer.close();
            System.out.println("Successfully wrote to the file.");
        } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }

    }

    /**
     * @author tanzia-ahmed
     * @param p_map
     * @return boolean
     * 
     * checking if the map is connected, all countries belong to at least one
     * continent, all continents have at least one country
     */
    public boolean validateMap(Map p_map) {
        ArrayList<Country> l_countries = p_map.getD_countries();
        ArrayList<Continent> l_mContinents = p_map.getD_continents();

        boolean l_connected = false;
        boolean l_hasContinent = false;

        Stack<Integer> l_stackNodes = new Stack<Integer>();
        Stack<String> l_stackContinents = new Stack<String>();

        //executing bfs on countries list and stacking connected nodes/country; used for later
        
        for (int l_aCountryIndex = 0; l_aCountryIndex < l_countries.size(); l_aCountryIndex++) {
            for (int l_aNeighbourIndex = 0; l_countries.get(l_aCountryIndex).getD_neighbours()
                    .size() > l_aNeighbourIndex; l_aNeighbourIndex++) {
                if(!(l_stackNodes.contains(l_countries.get(l_aCountryIndex).getD_neighbours().get(l_aNeighbourIndex).getD_CountryIntID())))
                l_stackNodes.push(l_countries.get(l_aCountryIndex).getD_neighbours().get(l_aNeighbourIndex).getD_CountryIntID());

            }
            //stacking continent names for each country; used for later
            if (!(l_stackContinents.contains(l_countries.get(l_aCountryIndex).getD_CountryContinentID()))) {
                l_stackContinents.push(l_countries.get(l_aCountryIndex).getD_CountryContinentID());
            }

            //checking if current country has continent
            if (l_countries.get(l_aCountryIndex).getD_CountryContinentID().isEmpty()) {
                System.out.println(
                        l_countries.get(l_aCountryIndex).getD_CountryID() + " country does not belong to any continent.");
                return false;
            } else {
                l_hasContinent = true;
            }

        }

        //checking if all continent has at least one country
        if (l_stackContinents.size() != l_mContinents.size()) {
            System.out.println("A continent without a country found.");
            return false;
        }
        //checking if map is connected
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
