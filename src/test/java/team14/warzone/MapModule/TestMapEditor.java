package team14.warzone.MapModule;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.Stack;

import org.junit.jupiter.api.Test;

public class TestMapEditor {
    MapEditor me = new MapEditor();

    @Test
    void testLoadMap() {
        me.loadMap("/home/nadib/bigeurope.map");
        Map m = me.getD_loadedMap();
        assert m.getD_continents().size() == 18;
        assert m.getD_countries().size() == 180;
        assert m.getD_countries().get(1).getD_neighbours().get(2).getD_CountryIntID() == 8;
    }

    @Test
    void testValidateMap() {
        me.loadMap("D:/Concordia Courses/SOEN 6441/Project Tools/bigeurope.map");
        Map m1 = me.getD_loadedMap();
        boolean test = me.validateMap(m1);
        assertEquals(true, test);
    }

    @Test
    void testValidateMap_isConnected(){
        me.loadMap("D:/Concordia Courses/SOEN 6441/Project Tools/europass.map");
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
        System.out.println(l_stackContinents.size());
        System.out.println(l_mContinents.size()+1);
        assert l_stackContinents.size() == l_mContinents.size();

    }
}
