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
        Map l_Map = new Map();
        try {
            File l_FileObject = new File(p_fileName);
            Scanner l_ReaderObject = new Scanner(l_FileObject);
            while (l_ReaderObject.hasNextLine()) {
                String l_Data = l_ReaderObject.nextLine();
                if (l_Data.equals("[countries]")) {
                    while (true) {
                        String l_Line = l_ReaderObject.nextLine();
                        if (l_Line.length()>0 && l_Line.charAt(0) == ';')
                        {
                            continue;
                        }
                        ArrayList<Continent> l_ContinentList = l_Map.getD_continents();
                        if (l_Line.equals("")) {
                            break;
                        } else {
                            String[] l_CountryArray = l_Line.split(" ");
                            String l_ContinentName = "";
                            for (Continent l_Conrinent : l_ContinentList) {
                                if (l_Conrinent.getD_ContinentIntID() == Integer.parseInt(l_CountryArray[2])) {
                                    l_ContinentName = l_Conrinent.getD_ContinentID();
                                    break;
                                }
                            }
                            l_Map.addCountry(l_CountryArray[1],l_ContinentName);
                        }
                    }
                } else if (l_Data.equals("[continents]")) {
                    while (true) {
                        String l_Line = l_ReaderObject.nextLine();
                        if (l_Line.length()>0 && l_Line.charAt(0) == ';')
                        {
                            continue;
                        }
                        if (l_Line.equals("")) {
                            break;
                        } else {
                            String[] l_ContinentArray = l_Line.split(" ");
                            l_Map.addContinent(l_ContinentArray[0], Integer.parseInt(l_ContinentArray[1]));
                        }
                    }

                } else if (l_Data.equals("[borders]")) {
                    ArrayList<Country> l_Countires = l_Map.getD_countries();
                    int l_Index = 0;
                    while (l_ReaderObject.hasNextLine()) {
                        String l_Line = l_ReaderObject.nextLine();
                        if (l_Line.charAt(0) == ';'&& l_Line.length()>0)
                        {
                            continue;
                        }
                        if (l_Line.equals("")) {
                            break;
                        } else {
                            String[] l_NeighbourArray = l_Line.split(" ");
                            ArrayList<Country> l_NeighbourIDArray = new ArrayList<>();
                            for (int l_NeighbourIndex = 1; l_NeighbourIndex < l_NeighbourArray.length; l_NeighbourIndex++) {
                                for (int l_CountryIndex = 0; l_CountryIndex < l_Countires.size(); l_CountryIndex++) {
                                    if (l_Countires.get(l_CountryIndex).getD_CountryIntID() == Integer.parseInt(l_NeighbourArray[l_NeighbourIndex])) {
                                        l_NeighbourIDArray.add(l_Countires.get(l_CountryIndex));
                                    }
                                }
                            }
                            l_Countires.get(l_Index).setD_neighbours(l_NeighbourIDArray);
                            l_Index++;
                        }
                    }
                }

            }
            l_ReaderObject.close();
        } catch (FileNotFoundException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
        this.d_loadedMap = l_Map;

    }

    /**
     * This method is used to save a map in a text format
     * @param p_FileName
     */
    public void saveMap(String p_FileName) {
        String l_Content = "This map was created from a SOEN-6441 Project \n \n";
        l_Content+="[continents]\n";
        for (Continent l_Continent:d_loadedMap.getD_continents()) {
            l_Content += l_Continent.getD_ContinentID()+" "+l_Continent.getD_ControlValue()+"\n";
        }
        l_Content+="\n[countries]\n";
        for (Country l_Country:d_loadedMap.getD_countries()) {
            int l_ContinentIntID = -1;
            for (Continent l_Continent: d_loadedMap.getD_continents()) {
                if (l_Continent.getD_ContinentID().equals(l_Country.getD_CountryContinentID()))
                {
                    l_ContinentIntID = l_Continent.getD_ContinentIntID();
                }
            }
            l_Content += l_Country.getD_CountryIntID()+" "+"-"+" "+l_ContinentIntID+"\n";
        }

        l_Content+="\n[borders]\n";
        for (Country l_Country:d_loadedMap.getD_countries()) {
            l_Content += l_Country.getD_CountryIntID()+" ";
            for (Country l_Neighbour:l_Country.getD_neighbours()) {
                l_Content += l_Neighbour.getD_CountryIntID()+ " ";
            }
            l_Content += "\n";
        }

        try {
            FileWriter l_Writer = new FileWriter(p_FileName);
            l_Writer.write(l_Content);
            l_Writer.close();
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
