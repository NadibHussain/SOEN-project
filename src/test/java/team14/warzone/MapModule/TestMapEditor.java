package team14.warzone.MapModule;


import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.Stack;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.jupiter.api.DisplayName;
import org.junit.Test;
import java.io.FileNotFoundException;


public class TestMapEditor {

    public static MapEditor d_MapEditor;


    
    /** 
     * @throws FileNotFoundException
     */
    @BeforeClass
    public static void init() throws FileNotFoundException {

        d_MapEditor = new MapEditor();
        d_MapEditor.loadMap("testmap.map");
    }

    @Test
    @DisplayName("Testing loading a map")
    public void testLoadMap() {
        Map l_Map = d_MapEditor.getD_LoadedMap();
        assertEquals(7, l_Map.getD_Continents().size());
        assertEquals(110, l_Map.getD_Countries().size());
        assertEquals(51, l_Map.getD_Countries().get(1).getD_Neighbours().get(1).getD_CountryIntID());
    }

    @Test
    @DisplayName("Testing map validator")
    public void testValidateMap() {
        Map l_Map = d_MapEditor.getD_LoadedMap();
        boolean l_Test = d_MapEditor.validateMap(l_Map);
        assertEquals(true, l_Test);
    }

    @Test
    public void testValidateMap_isConnected() {
        Map p_Map = d_MapEditor.getD_LoadedMap();
        ArrayList<Country> l_Countries = p_Map.getD_Countries();
        boolean l_ConnectedGraph = false;
        l_ConnectedGraph = d_MapEditor.dfs(l_Countries, "graph");
        
        assert l_ConnectedGraph == true;

    }

    @Test
    public void testValidateMap_allContinentHasCountry() {
        Map p_Map = d_MapEditor.getD_LoadedMap();
        ArrayList<Continent> l_Continents = p_Map.getD_Continents();
        ArrayList<Country> l_Countries = p_Map.getD_Countries();
        Stack<String> l_StackContinents = new Stack<String>();
        for (int l_CountryIndex = 0; l_CountryIndex < l_Countries.size(); l_CountryIndex++) {
            if (!(l_StackContinents.contains(l_Countries.get(l_CountryIndex).getD_CountryContinentID()))) {
                l_StackContinents.push(l_Countries.get(l_CountryIndex).getD_CountryContinentID());
            }
        }
        assert l_StackContinents.size() == l_Continents.size();

    }

    @Test
    public void testValidateMap_allCountryHasContinent() {
        Map p_Map = d_MapEditor.getD_LoadedMap();
        ArrayList<Country> l_Countries = p_Map.getD_Countries();
        boolean l_HasContinent = true;
        for (int l_CountryIndex = 0; l_CountryIndex < l_Countries.size(); l_CountryIndex++) {
            if (l_Countries.get(l_CountryIndex).getD_CountryContinentID().isEmpty()) {
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

    @AfterClass
    public static void tearDown() {
        d_MapEditor = null;
    }


}