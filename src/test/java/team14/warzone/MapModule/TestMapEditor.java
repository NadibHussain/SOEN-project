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
        d_MapEditor.loadMap("europass.map");
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
        Stack<Integer> l_StackNodes = new Stack<Integer>();
        for (int l_CountryIndex = 0; l_CountryIndex < l_Countries.size(); l_CountryIndex++) {
            for (int l_NeighbourIndex = 0; l_Countries.get(l_CountryIndex).getD_Neighbours()
                    .size() > l_NeighbourIndex; l_NeighbourIndex++) {
                if (!(l_StackNodes.contains(
                        l_Countries.get(l_CountryIndex).getD_Neighbours().get(l_NeighbourIndex).getD_CountryIntID())))
                    l_StackNodes.push(l_Countries.get(l_CountryIndex).getD_Neighbours().get(l_NeighbourIndex)
                            .getD_CountryIntID());

            }
        }
        assert l_StackNodes.size() == l_Countries.size();

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

    @Ignore
    @Test
    public void testValidateMap_hasConnectedSubGraphs(){
        Map p_Map = d_MapEditor.getD_LoadedMap();
        ArrayList<Continent> l_Continents = p_Map.getD_Continents();
        boolean l_ConnectedSubGraph = false;
        //Running bfs
        for (int l_ContIndex = 0; l_ContIndex < l_Continents.size(); l_ContIndex++) {
            ArrayList<Country> l_Countries2 = p_Map.getCountryListOfContinent(l_Continents.get(l_ContIndex).getD_ContinentID());
            Stack<Integer> l_StackNodes2 = new Stack<Integer>();

            for (int l_CountryIndex = 0; l_CountryIndex < l_Countries2.size(); l_CountryIndex++) {
                
                for (int l_NeighbourIndex = 0; l_Countries2.get(l_CountryIndex).getD_Neighbours()
                        .size() > l_NeighbourIndex; l_NeighbourIndex++) {
                    if(!(l_StackNodes2.contains(l_Countries2.get(l_CountryIndex).getD_Neighbours().get(l_NeighbourIndex).getD_CountryIntID()))
                    && l_Countries2.get(l_CountryIndex).getD_Neighbours().get(l_NeighbourIndex).getD_CountryContinentID() == l_Continents.get(l_ContIndex).getD_ContinentID())
                        l_StackNodes2.push(l_Countries2.get(l_CountryIndex).getD_Neighbours().get(l_NeighbourIndex).getD_CountryIntID());
    
                }
            }
            System.out.println(l_StackNodes2.size() + " "+l_Countries2.size());

            //checking if subgraph is connected
            if(l_StackNodes2.size() == l_Countries2.size())
                l_ConnectedSubGraph = true;
                else{
                    l_ConnectedSubGraph = false;
                    break;
                } 
        }
            assert l_ConnectedSubGraph == true;
    }

    @AfterClass
    public static void tearDown() {
        d_MapEditor = null;
    }


}