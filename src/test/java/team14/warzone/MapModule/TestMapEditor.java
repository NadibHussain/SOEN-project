package team14.warzone.MapModule;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.Stack;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class TestMapEditor {

    public static MapEditor d_MapEditor;
    public static MapEditor me2;

    @BeforeAll
    public static void init() {
        d_MapEditor = new MapEditor();
        d_MapEditor.loadMap("europass.map");
    }

    @Test
    @DisplayName("Testing loading a map")
    public void testLoadMap() {
        // me2.loadMap("europass.map");
        Map l_Map = d_MapEditor.getD_loadedMap();
        assertEquals(7, l_Map.getD_continents().size());
        assertEquals(110, l_Map.getD_countries().size());
        assertEquals(51, l_Map.getD_countries().get(1).getD_neighbours().get(1).getD_CountryIntID());
    }

    @Test
    @DisplayName("Testing map validator")
    public void testValidateMap() {
        Map l_Map = d_MapEditor.getD_loadedMap();
        boolean l_Test = d_MapEditor.validateMap(l_Map);
        assertEquals(true, l_Test);
    }

    @Test
    void testValidateMap_isConnected() {
        // me.loadMap("bigeurope.map");
        Map p_Map = d_MapEditor.getD_loadedMap();
        ArrayList<Country> l_Countries = p_Map.getD_countries();
        Stack<Integer> l_StackNodes = new Stack<Integer>();
        for (int l_CountryIndex = 0; l_CountryIndex < l_Countries.size(); l_CountryIndex++) {
            for (int l_NeighbourIndex = 0; l_Countries.get(l_CountryIndex).getD_neighbours()
                    .size() > l_NeighbourIndex; l_NeighbourIndex++) {
                if (!(l_StackNodes.contains(
                        l_Countries.get(l_CountryIndex).getD_neighbours().get(l_NeighbourIndex).getD_CountryIntID())))
                    l_StackNodes.push(l_Countries.get(l_CountryIndex).getD_neighbours().get(l_NeighbourIndex)
                            .getD_CountryIntID());

            }
        }
        assert l_StackNodes.size() == l_Countries.size();

    }

    @Test
    void testValidateMap_allContinentHasCountry() {
        // me.loadMap("bigeurope.map");
        Map p_Map = d_MapEditor.getD_loadedMap();
        ArrayList<Continent> l_Continents = p_Map.getD_continents();
        ArrayList<Country> l_Countries = p_Map.getD_countries();
        Stack<String> l_StackContinents = new Stack<String>();
        for (int l_CountryIndex = 0; l_CountryIndex < l_Countries.size(); l_CountryIndex++) {
            if (!(l_StackContinents.contains(l_Countries.get(l_CountryIndex).getD_CountryContinentID()))) {
                l_StackContinents.push(l_Countries.get(l_CountryIndex).getD_CountryContinentID());
            }
        }
        assert l_StackContinents.size() == l_Continents.size();

    }

    @Test
    void testValidateMap_allCountryHasContinent() {
        // me.loadMap("bigeurope.map");
        Map p_Map = d_MapEditor.getD_loadedMap();
        ArrayList<Country> l_Countries = p_Map.getD_countries();
        boolean l_HasContinent = true;
        for (int l_CountryIndex = 0; l_CountryIndex < l_Countries.size(); l_CountryIndex++) {
            if (l_Countries.get(l_CountryIndex).getD_CountryContinentID().isEmpty()) {
                l_HasContinent = false;
                break;
            }
        }
        assert l_HasContinent == true;
    }

    @AfterAll
    public static void tearDown() {
        d_MapEditor = null;
    }

    @DisplayName("Testing save map feature")
    public void testSaveMap() {
        // me.saveMap("/home/nadib/Desktop/soen project/SOEN-project/test.map");
        // me.loadMap("/home/nadib/Desktop/soen project/SOEN-project/test.map");
        // me.validateMap(me.d_LoadedMap);

    }

}