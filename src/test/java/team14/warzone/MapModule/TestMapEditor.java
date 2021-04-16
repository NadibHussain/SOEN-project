package team14.warzone.MapModule;


import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.jupiter.api.DisplayName;
import org.junit.Test;

import java.io.FileNotFoundException;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Test the MapEditor class
 */
public class TestMapEditor {

    /**
     * static mapeditor
     */
    public static MapEditorDomination d_MapEditor;

    /** 
     * Initialize before tests run
     * @throws FileNotFoundException throws a filenotfoundexception
     */
    @BeforeClass
    public static void init() throws FileNotFoundException {

        d_MapEditor = new AdapterMapEditor(new MapEditorConquest());
        d_MapEditor.loadMapDomination("europass.map");
    }

    /**
     * tests load map
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
     * test validation of a map
     */
    @Test
    @DisplayName("Testing map validator")
    public void testValidateMap() {
        Map l_Map = d_MapEditor.getD_LoadedMap();
        boolean l_Test = d_MapEditor.validateMapDomination(l_Map);
        assertEquals(true, l_Test);
    }

    /**
     * test if map is connected graph
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
     * test if all continents have a country each
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
     * test if all countries have a continent
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

    /**
     * test if map has connected sub-graph
     */
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
     * test if map is invalid
     */
    @Test
    @DisplayName("Testing invalid map")
    public void testInvalidMap() {
        try {
            d_MapEditor.loadMapDomination("invalidmap.map");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        Map l_Map = d_MapEditor.getD_LoadedMap();
        boolean l_Test = d_MapEditor.validateMapDomination(l_Map);
        assertEquals(false, l_Test);
    }

    /**
     * tear down after all test run
     */
    @AfterClass
    public static void tearDown() {
        d_MapEditor = null;
    }


}