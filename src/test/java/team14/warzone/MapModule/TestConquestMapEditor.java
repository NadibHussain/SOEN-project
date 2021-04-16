package team14.warzone.MapModule;

import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.jupiter.api.DisplayName;

import java.io.FileNotFoundException;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TestConquestMapEditor {


    /**
     * static mapeditor
     */
    public static MapEditorConquest d_MapEditorConquest;

    /**
     * Initialize before tests run
     * @throws FileNotFoundException throws a filenotfoundexception
     */
    @BeforeClass
    public static void init() throws FileNotFoundException {

        d_MapEditorConquest = new MapEditorConquest();
        d_MapEditorConquest.loadMapConquest("conquest.map");
    }

    /**
     * tests load map
     */
    @Test
    @DisplayName("Testing loading a map")
    public void testLoadConquestMap() {
        Map l_Map = d_MapEditorConquest.getD_LoadedMap();
        assertEquals(11, l_Map.getD_Continents().size());
        assertEquals(128, l_Map.getD_Countries().size());
        assertEquals(6, l_Map.getD_Countries().get(8).getD_Neighbours().size());
    }

    /**
     * tests Save map
     */
    @Test
    @DisplayName("Testing Save map")
    public void testSaveConquestMap() {
        d_MapEditorConquest.saveMapConquest("saveConquest.map",d_MapEditorConquest.d_LoadedMap);
        Map l_Map = d_MapEditorConquest.getD_LoadedMap();
        assertEquals(11, l_Map.getD_Continents().size());
        assertEquals(128, l_Map.getD_Countries().size());
        assertEquals(6, l_Map.getD_Countries().get(8).getD_Neighbours().size());
    }


    /**
     * tests Validate map
     */
    @Test
    @DisplayName("Testing Validate a map")
    public void testValidateConquestMap() {
        d_MapEditorConquest.validateMapConquest(d_MapEditorConquest.getD_LoadedMap());
    }



}
