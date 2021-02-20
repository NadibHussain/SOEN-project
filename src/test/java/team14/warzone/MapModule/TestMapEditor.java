package team14.warzone.MapModule;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class TestMapEditor {

    public static MapEditor me;

    @BeforeAll
    public static void init(){
        me = new MapEditor();
        me.loadMap("/home/nadib/Desktop/soen project/SOEN-project/bigeurope.map");
    }

    @Test
    @DisplayName("Testing loading a map")
    public void testLoadMap() {
        Map m = me.getD_loadedMap();
        assertEquals(m.getD_continents().size(),18);
        assertEquals(m.getD_countries().size(),180);
        assertEquals(m.getD_countries().get(1).getD_neighbours().get(2).getD_CountryIntID() ,8);
    }

    @Test
    @DisplayName("Testing map validator")
    public void testValidateMap() {
        Map m1 = me.getD_loadedMap();
        boolean test = me.validateMap(m1);
        assertEquals(true, test);
    }


}
