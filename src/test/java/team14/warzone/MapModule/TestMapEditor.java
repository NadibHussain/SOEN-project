package team14.warzone.MapModule;

import static org.junit.jupiter.api.Assertions.assertEquals;


import java.util.ArrayList;
import java.util.Stack;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class TestMapEditor {

    public static MapEditor me;
    public static MapEditor me2;

    @BeforeAll
    public static void init(){
        me = new MapEditor();
        me2 = new MapEditor();
        me.loadMap("/home/nadib/Desktop/soen project/SOEN-project/europass.map");
    }

    @Test
    @DisplayName("Testing loading a map")
    public void testLoadMap() {
        Map m = me.getD_loadedMap();
        assertEquals(7,m.getD_continents().size());
        assertEquals(110,m.getD_countries().size());
        assertEquals(51,m.getD_countries().get(1).getD_neighbours().get(1).getD_CountryIntID());
    }

    @Test
    @DisplayName("Testing map validator")
    public void testValidateMap() {
        Map m1 = me.getD_loadedMap();
        boolean test = me.validateMap(m1);
        assertEquals(true, test);
    }

    @Test
    void testValidateMap_isConnected(){
        me.loadMap("D:/Concordia Courses/SOEN 6441/Project Tools/bigeurope.map");
        Map p_map = me.getD_loadedMap();
        ArrayList<Country> l_countries = p_map.getD_countries();
        Stack<Integer> l_stackNodes = new Stack<Integer>();
        for (int l_aCountryIndex = 0; l_aCountryIndex < l_countries.size(); l_aCountryIndex++) {
            for (int l_aNeighbourIndex = 0; l_countries.get(l_aCountryIndex).getD_neighbours()
                    .size() > l_aNeighbourIndex; l_aNeighbourIndex++) {
                if(!(l_stackNodes.contains(l_countries.get(l_aCountryIndex).getD_neighbours().get(l_aNeighbourIndex).getD_CountryIntID())))
                l_stackNodes.push(l_countries.get(l_aCountryIndex).getD_neighbours().get(l_aNeighbourIndex).getD_CountryIntID());

            }
        }
        assert l_stackNodes.size() == l_countries.size();

    }

    @Test 
    void testValidateMap_allContinentHasCountry(){
        me.loadMap("D:/Concordia Courses/SOEN 6441/Project Tools/bigeurope.map");
        Map p_map = me.getD_loadedMap();
        ArrayList<Continent> l_mContinents = p_map.getD_continents();
        ArrayList<Country> l_countries = p_map.getD_countries();
        Stack<String> l_stackContinents = new Stack<String>(); 
        for (int l_aCountryIndex = 0; l_aCountryIndex < l_countries.size(); l_aCountryIndex++) {
            if (!(l_stackContinents.contains(l_countries.get(l_aCountryIndex).getD_CountryContinentID()))) {
                l_stackContinents.push(l_countries.get(l_aCountryIndex).getD_CountryContinentID());
            }
        }
        assert l_stackContinents.size() == l_mContinents.size();

    }

    @Test 
    void testValidateMap_allCountryHasContinent(){
        me.loadMap("D:/Concordia Courses/SOEN 6441/Project Tools/bigeurope.map");
        Map p_map = me.getD_loadedMap();
        ArrayList<Country> l_countries = p_map.getD_countries();
        boolean l_hasContinent = true;
        for (int l_aCountryIndex = 0; l_aCountryIndex < l_countries.size(); l_aCountryIndex++) {
            if (l_countries.get(l_aCountryIndex).getD_CountryContinentID().isEmpty()) {
                l_hasContinent = false;
                break;
            }
        }
        assert l_hasContinent == true;
    }

    @DisplayName("Testing save map feature")
    public void testSaveMap() {
        me.saveMap("/home/nadib/Desktop/soen project/SOEN-project/test.map");
        me.loadMap("/home/nadib/Desktop/soen project/SOEN-project/test.map");
        me.validateMap(me.d_loadedMap);

    }


}
