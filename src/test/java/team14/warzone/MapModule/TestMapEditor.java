package team14.warzone.MapModule;


import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.jupiter.api.DisplayName;
import org.junit.Test;
import org.junit.jupiter.api.DisplayName;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Stack;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Test the MapEditor class
 */
public class TestMapEditor {

    public static MapEditor d_MapEditor;

    /** 
     * @throws FileNotFoundException
     */
    @BeforeClass
    public static void init() throws FileNotFoundException {

        d_MapEditor = new MapEditor();
        d_MapEditor.loadMap("europass.map");
    }

    /**
     * Test the loadmap method
     * Verify that all countries are being loaded
     */
    @Test
    @DisplayName("Testing loading a map")
    public void testLoadMap() {
        Map l_Map = d_MapEditor.getD_LoadedMap();
        assertEquals(7, l_Map.getD_Continents().size());
        assertEquals(110, l_Map.getD_Countries().size());
        assertEquals(51, l_Map.getD_Countries().get(1).getD_Neighbours().get(1).getD_CountryIntID());
    }

    /**
     * Test for the validatemap method
     */
    @Test
    @DisplayName("Testing map validator")
    public void testValidateMap() {
        Map l_Map = d_MapEditor.getD_LoadedMap();
        boolean l_Test = d_MapEditor.validateMap(l_Map);
        assertEquals(true, l_Test);
    }

    /**
     * Test for validatemap method to check if the map is a connected graph
     */
    @Test
    public void testValidateMap_isConnected() {
        Map p_Map = d_MapEditor.getD_LoadedMap();
        ArrayList<Country> l_Countries = p_Map.getD_Countries();
        boolean l_ConnectedGraph = false;
        l_ConnectedGraph = d_MapEditor.dfs(l_Countries, "graph");
        
        assert l_ConnectedGraph == true;

    }

    /**
     * Test checks that the validate map verifies that there are no continent without any countries
     */
    @Test
    public void testValidateMap_allContinentHasCountry() {
        Map p_Map = d_MapEditor.getD_LoadedMap();
        ArrayList<Continent> l_Continents = p_Map.getD_Continents();
        ArrayList<Country> l_Countries = p_Map.getD_Countries();
        ArrayList<String> l_CountryContinent = new ArrayList<>();
        for (int l_Index = 0; l_Index < l_Countries.size(); l_Index++) {
            if (!l_Countries.get(l_Index).getD_CountryContinentID().equals("")) {
                
                if (!l_CountryContinent.contains(l_Countries.get(l_Index).getD_CountryContinentID())) {
                    l_CountryContinent.add(l_Countries.get(l_Index).getD_CountryContinentID());
                }
            } 
        }
        // checking if all continent has a country
        assert l_CountryContinent.size() == l_Continents.size();

    }

    /**
     * Verify that all the countries have a continent
     */
    @Test
    public void testValidateMap_allCountryHasContinent() {
        Map p_Map = d_MapEditor.getD_LoadedMap();
        ArrayList<Country> l_Countries = p_Map.getD_Countries();
        boolean l_HasContinent = true;
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
        assert l_HasContinent == true;
    }

    
    @Test
    public void testValidateMap_hasConnectedSubGraphs(){
        Map l_Map = d_MapEditor.getD_LoadedMap();
        ArrayList<Continent> l_Continents = l_Map.getD_Continents();
        boolean l_ConnectedSubGraph = false;
        for (int l_ContIndex = 0; l_ContIndex < l_Continents.size(); l_ContIndex++) {

            ArrayList<Country> l_Countries2 = l_Map
                    .getCountryListOfContinent(l_Continents.get(l_ContIndex).getD_ContinentID());

            l_ConnectedSubGraph = d_MapEditor.dfs(l_Countries2, "sub-graph");
            System.out.println(l_ConnectedSubGraph);
            if(l_ConnectedSubGraph == false) break;

        }
            assert l_ConnectedSubGraph == true;
    }

    /**
     * teardown
     */
    @AfterClass
    public static void tearDown() {
        d_MapEditor = null;
    }


}