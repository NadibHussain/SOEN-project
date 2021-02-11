package team14.warzone.MapModule;

import org.junit.jupiter.api.Test;

public class TestMapEditor {
    MapEditor me = new MapEditor();
    @Test
    void testLoadMap() {
<<<<<<< HEAD
        Map m = me.loadMap("D:/Concordia Courses/SOEN 6441/Project Tools/bigeurope.map");
=======
        me.loadMap("/home/nadib/bigeurope.map");
        Map m = me.getD_loadedMap();
>>>>>>> 5f69a48d374c208ed2501ead7734b0c91879e9ab
        assert m.getD_continents().size() == 18;
        assert m.getD_countries().size() == 180;
        assert m.getD_countries().get(1).getD_neighbours().get(2).getID()==8;
    }
    @Test
    boolean testValidateMap(){
        Map m = me.loadMap("D:/Concordia Courses/SOEN 6441/Project Tools/bigeurope.map");
        me.validateMap(m);
        return false;
        
    }
}

