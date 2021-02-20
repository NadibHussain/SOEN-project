package team14.warzone.MapModule;

import static org.junit.jupiter.api.Assertions.assertEquals;

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
}
