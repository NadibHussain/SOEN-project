package team14.warzone.MapModule;

import static org.junit.jupiter.api.Assertions.assertEquals;

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
    @DisplayName("Testing save map feature")
    public void testSaveMap() {
        me.saveMap("/home/nadib/Desktop/soen project/SOEN-project/test.map");
        me.loadMap("/home/nadib/Desktop/soen project/SOEN-project/test.map");
        me.validateMap(me.d_loadedMap);

    }


}
