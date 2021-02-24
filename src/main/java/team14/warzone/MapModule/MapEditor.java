package team14.warzone.MapModule;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.Stack;

/**
 * This is the main class for Mapeditor functions
 */

public class MapEditor {

    /**
     * loaded map object
     */
    public Map d_LoadedMap;

    /**
     * Mapeditor method
     */
    public MapEditor() {
    }

    /**
     * Loadmap from file
     * 
     * @param p_FileName String filename
     */
    public void loadMap(String p_FileName) {
        Map l_Map = new Map();
        try {
            File l_FileObject = new File(p_FileName);
            Scanner l_ReaderObject = new Scanner(l_FileObject);
            while (l_ReaderObject.hasNextLine()) {
                String l_Data = l_ReaderObject.nextLine();
                if (l_Data.equals("[countries]")) {
                    while (true) {
                        String l_Line = l_ReaderObject.nextLine();
                        if (l_Line.length() > 0 && l_Line.charAt(0) == ';') {
                            continue;
                        }
                        ArrayList<Continent> l_ContinentList = l_Map.getD_Continents();
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
                            l_Map.addCountry(l_CountryArray[1], l_ContinentName);
                        }
                    }
                } else if (l_Data.equals("[continents]")) {
                    while (true) {
                        String l_Line = l_ReaderObject.nextLine();
                        if (l_Line.length() > 0 && l_Line.charAt(0) == ';') {
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
                    ArrayList<Country> l_Countires = l_Map.getD_Countries();
                    int l_Index = 0;
                    while (l_ReaderObject.hasNextLine()) {
                        String l_Line = l_ReaderObject.nextLine();
                        if (l_Line.charAt(0) == ';' && l_Line.length() > 0) {
                            continue;
                        }
                        if (l_Line.equals("")) {
                            break;
                        } else {
                            String[] l_NeighbourArray = l_Line.split(" ");
                            ArrayList<Country> l_NeighbourIDArray = new ArrayList<Country>();
                            for (int l_NeighbourIndex = 1; l_NeighbourIndex < l_NeighbourArray.length; l_NeighbourIndex++) {
                                for (int l_countryIndex = 0; l_countryIndex < l_Countires.size(); l_countryIndex++) {
                                    if (l_Countires.get(l_countryIndex).getD_CountryIntID() == Integer
                                            .parseInt(l_NeighbourArray[l_NeighbourIndex])) {
                                        l_NeighbourIDArray.add(l_Countires.get(l_countryIndex));
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
            System.out.println("The map file does not exist");
        }
        this.d_LoadedMap = l_Map;

    }

    /**
     * This method is used to save a map in a text format
     * 
     * @param p_FileName String filename
     */
    public void saveMap(String p_FileName) {
        String l_Content = "This map was created from a SOEN-6441 Project \n \n";
        l_Content += "[continents]\n";
        for (Continent l_Continent : d_LoadedMap.getD_Continents()) {
            l_Content += l_Continent.getD_ContinentID() + " " + l_Continent.getD_ControlValue() + "\n";
        }
        l_Content += "\n[countries]\n";
        for (Country l_Country : d_LoadedMap.getD_Countries()) {
            int l_ContinentIntId = -1;
            for (Continent l_continent : d_LoadedMap.getD_Continents()) {
                if (l_continent.getD_ContinentID().equals(l_Country.getD_CountryContinentID())) {
                    l_ContinentIntId = l_continent.getD_ContinentIntID();
                }
            }
            l_Content += l_Country.getD_CountryIntID() + " " + "-" + " " + l_ContinentIntId + "\n";
        }

        l_Content += "\n[borders]\n";
        for (Country l_Country : d_LoadedMap.getD_Countries()) {
            l_Content += l_Country.getD_CountryIntID() + " ";
            for (Country l_neighbour : l_Country.getD_neighbours()) {
                l_Content += l_neighbour.getD_CountryIntID() + " ";
            }
            l_Content += "\n";
        }

        try {
            FileWriter l_Writer = new FileWriter(p_FileName);
            l_Writer.write(l_Content);
            l_Writer.close();
            System.out.println("Successfully wrote to the file.");
        } catch (IOException e) {
            System.out.println("Saving the map file was unsuccessful.");
        }

    }

    /**
     * checking if the map is connected, all countries belong to at least one
     * continent, all continents have at least one country, sub-graphs are connected
     * which means the countries inside a continent are connected.
     * 
     * @author tanzia
     * 
     * @param p_Map Map object
     * @return checking validity
     */
    public boolean validateMap(Map p_Map) {
        ArrayList<Country> l_Countries = p_Map.getD_Countries();
        ArrayList<Continent> l_Continents = p_Map.getD_Continents();

        boolean l_ConnectedGraph = false;
        boolean l_HasContinent = false;

        Stack<Integer> l_StackNodes = new Stack<Integer>();
        Stack<String> l_StackContinents = new Stack<String>();

        // executing bfs on countries list and stacking connected nodes/country;

        for (int l_CountryIndex = 0; l_CountryIndex < l_Countries.size(); l_CountryIndex++) {
            for (int l_NeighbourIndex = 0; l_Countries.get(l_CountryIndex).getD_neighbours()
                    .size() > l_NeighbourIndex; l_NeighbourIndex++) {
                if (!(l_StackNodes.contains(
                        l_Countries.get(l_CountryIndex).getD_neighbours().get(l_NeighbourIndex).getD_CountryIntID())))
                    l_StackNodes.push(l_Countries.get(l_CountryIndex).getD_neighbours().get(l_NeighbourIndex)
                            .getD_CountryIntID());

            }
            // stacking continent names for each country; used for later
            if (!(l_StackContinents.contains(l_Countries.get(l_CountryIndex).getD_CountryContinentID()))) {
                l_StackContinents.push(l_Countries.get(l_CountryIndex).getD_CountryContinentID());
            }

            // checking if current country has continent
            if (l_Countries.get(l_CountryIndex).getD_CountryContinentID().isEmpty()) {
                System.out.println(l_Countries.get(l_CountryIndex).getD_CountryID()
                        + " country does not belong to any continent.");
                return false;
            } else {
                l_HasContinent = true;
            }

        }

        // checking if map is connected
        if (l_StackNodes.size() == l_Countries.size())
            l_ConnectedGraph = true;
            else{
                System.out.println("The map is not connected.");
            }

        // checking if all continent has at least one country
        if (l_StackContinents.size() != l_Continents.size()) {
            System.out.println("A continent without a country found.");
            return false;
        }

        // Running bfs on the sub-graph or, countries inside a continent
        boolean l_ConnectedSubGraph = false;
        for (int l_ContIndex = 0; l_ContIndex < l_Continents.size(); l_ContIndex++) {

            ArrayList<Country> l_Countries2 = p_Map
                    .getCountryListOfContinent(l_Continents.get(l_ContIndex).getD_ContinentID());
            Stack<Integer> l_StackNodes2 = new Stack<Integer>();

            for (int l_CountryIndex = 0; l_CountryIndex < l_Countries2.size(); l_CountryIndex++) {
                for (int l_NeighbourIndex = 0; l_Countries2.get(l_CountryIndex).getD_neighbours()
                        .size() > l_NeighbourIndex; l_NeighbourIndex++) {
                    if (!(l_StackNodes2.contains(l_Countries2.get(l_CountryIndex).getD_neighbours()
                            .get(l_NeighbourIndex).getD_CountryIntID()))
                            && l_Countries2.get(l_CountryIndex).getD_neighbours().get(l_NeighbourIndex)
                                    .getD_CountryContinentID() == l_Continents.get(l_ContIndex).getD_ContinentID())
                        l_StackNodes2.push(l_Countries2.get(l_CountryIndex).getD_neighbours().get(l_NeighbourIndex)
                                .getD_CountryIntID());

                }
            }

            // checking if subgraph is connected
            if (l_StackNodes2.size() == l_Countries2.size())
                l_ConnectedSubGraph = true;
            else {
                System.out.println("Sub-graph is not connected.");
                return false;
            }

        }

        if (l_ConnectedGraph && l_HasContinent && l_ConnectedSubGraph)
            return true;
        else {
            System.out.println("The map is not valid.");
            return false;
        }

    }

    /**
     * Get a loaded map
     * 
     * @return a loaded map file
     */
    public Map getD_LoadedMap() {
        return d_LoadedMap;
    }

}
