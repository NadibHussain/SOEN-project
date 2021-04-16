package team14.warzone.MapModule;

import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.Stack;

/**
 * This is the main class for Mapeditor functions
 */

public class MapEditorDomination implements Serializable {

    /**
     * loaded map object
     */
    public Map d_LoadedMap;

    /**
     * Mapeditor method
     */
    public MapEditorDomination() {
    }

    /**
     * This method loads an existing file. If file is not found, it creates a file
     * and map from scratch.
     *
     * @param p_FileName filename param
     */
    public void editMapDomination(String p_FileName) {
        try {
            loadMapDomination(p_FileName);
        } catch (FileNotFoundException l_FileException) {
            System.out.println("Unable to find map file.Creating a new map file.");
            d_LoadedMap = new Map();
            saveMapDomination(p_FileName, d_LoadedMap);
        }
    }

    /**
     * Loadmap from file
     *
     * @param p_FileName String filename
     * @throws FileNotFoundException throws exception when filename is invalid
     */
    public void loadMapDomination(String p_FileName) throws FileNotFoundException {
        Map l_Map = new Map();
        File l_FileObject = new File(p_FileName);
        Scanner l_ReaderObject = new Scanner(l_FileObject);
        while (l_ReaderObject.hasNextLine()) {
            String l_Data = l_ReaderObject.nextLine();
            switch (l_Data) {
                case "[countries]":
                    handleCountriesDomination(l_ReaderObject, l_Map);
                    break;
                case "[continents]":
                    handleContinentsDomination(l_ReaderObject, l_Map);
                    break;
                case "[borders]":
                    handleNeighbourDomination(l_ReaderObject, l_Map);
                    break;
            }

        }
        l_ReaderObject.close();

        this.d_LoadedMap = l_Map;

    }

    /**
     * sub section of load map to hanlde countries
     *
     * @param l_ReaderObject to read the text from the file
     * @param l_Map          the map which needs to be edited
     */
    private void handleCountriesDomination(Scanner l_ReaderObject, Map l_Map) {
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
    }

    /**
     * sub section of load map to hanlde Continents
     *
     * @param l_ReaderObject to read the text from the file
     * @param l_Map          the map which needs to be edited
     */
    private void handleContinentsDomination(Scanner l_ReaderObject, Map l_Map) {
        while (true) {
            String l_Line = l_ReaderObject.nextLine();
            if (l_Line.length() > 0 && l_Line.charAt(0) == ';') {
                continue;
            }
            if (l_Line.equals("")) {
                break;
            } else {
                String[] l_ContinentArray = l_Line.split(" ");
                StringBuilder l_ContinentName = new StringBuilder();
                int l_ContinentValue = 0;
                for (int l_Counter = 0; l_Counter < l_ContinentArray.length; l_Counter++) {
                    try {
                        l_ContinentValue = Integer.parseInt(l_ContinentArray[l_Counter]);
                        break;
                    } catch (Exception e) {
                        l_ContinentName.append(l_ContinentArray[l_Counter]);
                    }
                }
                l_Map.addContinent(l_ContinentName.toString(), l_ContinentValue);
            }
        }
    }

    /**
     * sub section of load map to hanlde Neighbour
     *
     * @param l_ReaderObject to read the text from the file
     * @param l_Map          the map which needs to be edited
     */
    private void handleNeighbourDomination(Scanner l_ReaderObject, Map l_Map) {
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

    /**
     * This method is used to save a map in a text format
     *
     * @param p_FileName  String filename
     * @param p_LoadedMap Map object
     */
    public void saveMapDomination(String p_FileName, Map p_LoadedMap) {
        StringBuilder l_Content = new StringBuilder("This map was created from a SOEN-6441 Project \n \n");
        d_LoadedMap = p_LoadedMap;
        // writing all the continents
        l_Content.append("[continents]\n");
        for (Continent l_Continent : d_LoadedMap.getD_Continents()) {
            l_Content.append(l_Continent.getD_ContinentID()).append(" ").append(l_Continent.getD_ControlValue()).append("\n");
        }

        // writing all the countries
        l_Content.append("\n[countries]\n");
        for (Country l_Country : d_LoadedMap.getD_Countries()) {
            int l_ContinentIntId = -1;
            for (Continent l_Continent : d_LoadedMap.getD_Continents()) {
                if (l_Continent.getD_ContinentID().equals(l_Country.getD_CountryContinentID())) {
                    l_ContinentIntId = l_Continent.getD_ContinentIntID();
                }
            }
            l_Content.append(l_Country.getD_CountryIntID()).append(" ").append(l_Country.getD_CountryID()).append(" ").append(l_ContinentIntId).append("\n");
        }

        // writing all the borders
        l_Content.append("\n[borders]\n");
        for (Country l_Country : d_LoadedMap.getD_Countries()) {
            l_Content.append(l_Country.getD_CountryIntID()).append(" ");
            for (Country l_Neighbour : l_Country.getD_Neighbours()) {
                l_Content.append(l_Neighbour.getD_CountryIntID()).append(" ");
            }
            l_Content.append("\n");
        }

        try {
            FileWriter l_Writer = new FileWriter(p_FileName);
            l_Writer.write(l_Content.toString());
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
    public boolean validateMapDomination(Map p_Map) {
        ArrayList<Country> l_Countries = p_Map.getD_Countries();
        ArrayList<Continent> l_Continents = p_Map.getD_Continents();

        boolean l_ConnectedGraph = false;
        boolean l_HasContinent = false;

        l_ConnectedGraph = dfs(l_Countries, "graph");
        // checking if map is connected
        if (l_ConnectedGraph == true)
//            System.out.println(l_ConnectedGraph+" The map is connected.");
            ;
        else {
            System.out.println("The map is not connected.");
        }

        // checking if all country has a continent
        ArrayList<String> l_CountryContinent = new ArrayList<>();
        for (int l_Index = 0; l_Index < l_Countries.size(); l_Index++) {
            if (!l_Countries.get(l_Index).getD_CountryContinentID().equals("")) {
                l_HasContinent = true;
                if (!l_CountryContinent.contains(l_Countries.get(l_Index).getD_CountryContinentID())) {
                    l_CountryContinent.add(l_Countries.get(l_Index).getD_CountryContinentID());
                }
            } else {
                System.out.println("" + l_Countries.get(l_Index).getD_CountryID() + " does not have a continent");
                l_HasContinent = false;
                break;
            }
        }
        // checking if all continent has a country
        if (l_CountryContinent.size() != l_Continents.size()) {
            System.out.println("A continent without a country found.");
            return false;
        }

        // Running dfs on the sub-graph or, countries inside a continent
        boolean l_ConnectedSubGraph = false;
        for (int l_ContIndex = 0; l_ContIndex < l_Continents.size(); l_ContIndex++) {

            ArrayList<Country> l_Countries2 = p_Map
                    .getCountryListOfContinent(l_Continents.get(l_ContIndex).getD_ContinentID());
            l_ConnectedSubGraph = dfs(l_Countries2, "sub-graph");
            if (l_ConnectedSubGraph == false) {
                System.out.println("Sub-graph is not connected");
                break;
            }

        }

        if (l_ConnectedGraph && l_HasContinent && l_ConnectedSubGraph) {
            return true;
        } else {
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

    /**
     * Depth-first-search (dfs)
     *
     * @param p_CountryList list of countries
     * @param p_typeOfGraph type of graph
     * @return true if dfs was successful
     */
    public boolean dfs(ArrayList<Country> p_CountryList, String p_typeOfGraph) {
        ArrayList<Country> l_Countries = p_CountryList;

        int l_Visited[] = new int[d_LoadedMap.getD_Countries().size() * 2];
//        System.out.println(l_Visited.length + " " + d_LoadedMap.getD_Countries().size());
        for (int l_Index = 0; l_Index < l_Visited.length; l_Index++) {
            l_Visited[l_Index] = 0;
        }

        Stack<String> l_Stack = new Stack();

        l_Stack.push(l_Countries.get(0).getD_CountryID());

        for (int l_CountryIndex = 0; !l_Stack.isEmpty(); l_CountryIndex++) {

            Country l_CurrentCountry = d_LoadedMap.findCountry(l_Stack.pop());
            l_Visited[l_CurrentCountry.getD_CountryIntID()] = 1;

            ArrayList<Country> l_Neighbours = l_CurrentCountry.getD_Neighbours();

            for (int l_NeighbourIndex = 0; l_NeighbourIndex < l_Neighbours.size(); l_NeighbourIndex++) {

                if (!l_Stack.contains(l_Neighbours.get(l_NeighbourIndex).getD_CountryID())
                        & l_Visited[l_Neighbours.get(l_NeighbourIndex).getD_CountryIntID()] == 0) {

                    if (p_typeOfGraph.equals("sub-graph")) {

                        if (l_Neighbours.get(l_NeighbourIndex).getD_CountryContinentID() == l_CurrentCountry
                                .getD_CountryContinentID())
                            l_Stack.push(l_Neighbours.get(l_NeighbourIndex).getD_CountryID());
                    } else
                        l_Stack.push(l_Neighbours.get(l_NeighbourIndex).getD_CountryID());
                }
            }

        }

        int l_VisitedNodes = visitedNodes(l_Visited); // count of total nodes visited
        if (l_VisitedNodes == l_Countries.size())
            return true;
        else
            return false;

    }

    /**
     * counts how many nodes were visited
     *
     * @param l_Visited list of visited nodes
     * @return total nodes visited
     */
    private int visitedNodes(int[] l_Visited) {
        int l_Count = 0;
        for (int l_Index = 0; l_Index < l_Visited.length; l_Index++) {
            if (l_Visited[l_Index] == 1) {
                l_Count++;
            } else {
            }
        }
        return l_Count;
    }

}
