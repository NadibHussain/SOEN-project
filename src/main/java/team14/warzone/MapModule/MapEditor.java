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
     * This method loads an existing file. If file is not found, it creates a file
     * and map from scratch.
     * 
     * @param p_FileName filename param
     */
    public void editMap(String p_FileName) {
        try {
            loadMap(p_FileName);
        } catch (FileNotFoundException l_FileException) {
            System.out.println("Unable to find map file.Creating a new map file.");
            d_LoadedMap = new Map();
            saveMap(p_FileName);
        }
    }

    /**
     * Loadmap from file
     *
     * @param p_FileName String filename
     * @throws FileNotFoundException throws exception when filename is invalid
     */
    public void loadMap(String p_FileName) throws FileNotFoundException {
        Map l_Map = new Map();
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
                            for (int l_CountryIndex = 0; l_CountryIndex < l_Countires.size(); l_CountryIndex++) {
                                if (l_Countires.get(l_CountryIndex).getD_CountryIntID() == Integer
                                        .parseInt(l_NeighbourArray[l_NeighbourIndex])) {
                                    l_NeighbourIDArray.add(l_Countires.get(l_CountryIndex));
                                }
                            }
                        }
                        l_Countires.get(l_Index).setD_Neighbours(l_NeighbourIDArray);
                        l_Index++;
                    }
                }
            }

        }
        l_ReaderObject.close();

        this.d_LoadedMap = l_Map;

    }

    /**
     * This method is used to save a map in a text format
     *
     * @param p_FileName String filename
     */
    public void saveMap(String p_FileName) {
        String l_Content = "This map was created from a SOEN-6441 Project \n \n";
        // writing all the continents
        l_Content += "[continents]\n";
        for (Continent l_Continent : d_LoadedMap.getD_Continents()) {
            l_Content += l_Continent.getD_ContinentID() + " " + l_Continent.getD_ControlValue() + "\n";
        }

        // writing all the countries
        l_Content += "\n[countries]\n";
        for (Country l_Country : d_LoadedMap.getD_Countries()) {
            int l_ContinentIntId = -1;
            for (Continent l_Continent : d_LoadedMap.getD_Continents()) {
                if (l_Continent.getD_ContinentID().equals(l_Country.getD_CountryContinentID())) {
                    l_ContinentIntId = l_Continent.getD_ContinentIntID();
                }
            }
            l_Content += l_Country.getD_CountryIntID() + " " + l_Country.getD_CountryID() + " " + l_ContinentIntId
                    + "\n";
        }

        // writing all the borders
        l_Content += "\n[borders]\n";
        for (Country l_Country : d_LoadedMap.getD_Countries()) {
            l_Content += l_Country.getD_CountryIntID() + " ";
            for (Country l_Neighbour : l_Country.getD_Neighbours()) {
                l_Content += l_Neighbour.getD_CountryIntID() + " ";
            }
            l_Content += "\n";
        }

        try {
            FileWriter l_Writer = new FileWriter(p_FileName);
            l_Writer.write(l_Content);
            l_Writer.close();
            System.out.println("Successfully wrote to the file.");
        } catch (IOException l_IOException) {
            System.out.println("An error occurred.");
            l_IOException.printStackTrace();
        }

    }

    /**
     * checking if the map is connected, all countries belong to at least one
     * continent, all continents have at least one country, sub-graphs are connected
     * which means the countries inside a continent are connected.
     *
     * @param p_Map Map object
     * @return checking validity
     * @author tanzia
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
            for (int l_NeighbourIndex = 0; l_Countries.get(l_CountryIndex).getD_Neighbours()
                    .size() > l_NeighbourIndex; l_NeighbourIndex++) {
                if (!(l_StackNodes.contains(
                        l_Countries.get(l_CountryIndex).getD_Neighbours().get(l_NeighbourIndex).getD_CountryIntID())))
                    l_StackNodes.push(l_Countries.get(l_CountryIndex).getD_Neighbours().get(l_NeighbourIndex)
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
        else {
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
                for (int l_NeighbourIndex = 0; l_Countries2.get(l_CountryIndex).getD_Neighbours()
                        .size() > l_NeighbourIndex; l_NeighbourIndex++) {
                    if (!(l_StackNodes2.contains(l_Countries2.get(l_CountryIndex).getD_Neighbours()
                            .get(l_NeighbourIndex).getD_CountryIntID()))
                            && l_Countries2.get(l_CountryIndex).getD_Neighbours().get(l_NeighbourIndex)
                                    .getD_CountryContinentID() == l_Continents.get(l_ContIndex).getD_ContinentID())
                        l_StackNodes2.push(l_Countries2.get(l_CountryIndex).getD_Neighbours().get(l_NeighbourIndex)
                                .getD_CountryIntID());

                }
            }

            // checking if subgraph is connected
            if (l_StackNodes2.size() == l_Countries2.size())
                l_ConnectedSubGraph = true;
            else {
                System.out.println("The sub-graph is not connected.");
                l_ConnectedSubGraph = false;
                break;
            }

        }

        if (l_ConnectedGraph && l_HasContinent && l_ConnectedSubGraph) {
            System.out.println("The map is valid.");
            return true;
        } else {
//            System.out.println("The map is not valid.");
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

    public void dfs(ArrayList<Country> p_CountryList) {
        ArrayList<Country> l_Countries = p_CountryList;

        int l_Visited[] = new int[l_Countries.size()];
        for (int l_Index = 0; l_Index < l_Visited.length; l_Index++) {
            l_Visited[l_Index] = 0;
        }

        Stack l_Stack = new Stack();

        for (int l_Index = 0; allVisited(l_Visited) == false; l_Index++) {
            
                l_Stack.push(l_Countries.get(l_Index).getD_CountryID());
                ArrayList<Country> l_Neighbours = l_Countries.get(l_Index).getD_Neighbours();
                for(int i = 0; i<l_Neighbours.size(); i++){

                
            }

        }
    }

    private boolean allVisited(int[] l_Visited) {
        for (int l_Index = 0; l_Index < l_Visited.length; l_Index++) {
           if( l_Visited[l_Index] == 1){

           }
           else{
               return false;
           }
        }
        return true;
    }

    

    

}
