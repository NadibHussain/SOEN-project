package team14.warzone.MapModule;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class AdapterMapEditor extends MapEditorDomination {

    /**
     * copy of conquest map editor conquest
     */
    MapEditorConquest d_MapEditorConquest;


    /**
     * Constructor which takes map editor conquest as local variable
     *
     * @param l_MapEditorConquest takes MapEditorConquest to adapt with map editor domination
     */
    public AdapterMapEditor(MapEditorConquest l_MapEditorConquest) {
        this.d_MapEditorConquest = l_MapEditorConquest;
    }


    /**
     * This method loads an existing file. If file is not found, it creates a file
     * and map from scratch.
     *
     * @param p_FileName filename param
     */
    public void editMap(String p_FileName) {
        loadMap(p_FileName);
    }

    /**
     * loads the map in the loaded map variable
     *
     * @param p_filename name of the file that needs to be loaded
     */
    public void loadMap(String p_filename) {

        try {
            if (isDomination(p_filename)) {
                loadMapDomination(p_filename);
            } else {
                d_MapEditorConquest.loadMapConquest(p_filename);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

    }

    /**
     * Saves the map object in the .map file
     *
     * @param p_format   the format can be (domination/conquest) in which the file will be saved
     * @param p_filename name of file
     */
    public void saveMap(String p_format, String p_filename) {
        Map l_LoadedMap;
        if (d_MapEditorConquest.getD_LoadedMap() == null) {
            l_LoadedMap = d_LoadedMap;
        } else {
            l_LoadedMap = d_MapEditorConquest.d_LoadedMap;
        }

        if (p_format.equals("domination")) {
            saveMapDomination(p_filename, l_LoadedMap);
        } else {
            d_MapEditorConquest.saveMapConquest(p_filename, l_LoadedMap);
        }
    }

    /**
     * checks if the map file is domination
     *
     * @param p_FileName file path of the map file which needs to be checked
     * @return true if domination
     */
    public boolean isDomination(String p_FileName) {

        try {
            File l_FileObject = new File(p_FileName);
            Scanner l_ReaderObject = new Scanner(l_FileObject);
            while (l_ReaderObject.hasNextLine()) {
                String l_Data = l_ReaderObject.nextLine();
                if (l_Data.equals("[continents]")) {
                    return true;
                }
            }
            l_ReaderObject.close();
            return false;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Get a loaded map
     *
     * @return a loaded map file
     */
    @Override
    public Map getD_LoadedMap() {
        if (d_LoadedMap != null) {
            return d_LoadedMap;
        } else {
            return d_MapEditorConquest.d_LoadedMap;
        }
    }

}
