package team14.warzone.MapModule;

import org.junit.jupiter.api.Test;

public class TestMapEditor {
    MapEditor me = new MapEditor();
    @Test
    void testLoadMap() {
        me.loadMap("/home/nadib/bigeurope.map");
        Map m = me.getD_loadedMap();
        assert m.getD_continents().size() == 18;
        assert m.getD_countries().size() == 180;
        assert m.getD_countries().get(1).getD_neighbours().get(2).getID()==8;
    }
}

