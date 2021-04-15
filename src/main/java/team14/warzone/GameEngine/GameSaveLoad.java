package team14.warzone.GameEngine;

import java.io.*;

/**
 * This class implements the Save Game and Load Game feature
 */
public class GameSaveLoad implements Serializable {
    /**
     * File to save the game
     */
    private File d_GameFile;
    /**
     * Game Engine object to save the state of the game
     */
    private GameEngine d_GE;
    /**
     * Game Engine object to load the game from
     */
    private GameEngine d_loadGameEngine;

    /**
     * GameSaveLoad method
     * @param p_GE GameEngine parameter
     */
    public GameSaveLoad(GameEngine p_GE) {
        d_GE = p_GE;
    }

    /**
     * Save Game implementation
     * @param p_FileName takes input as a string to save file with the name
     * @return true if game is saved
     */
    public boolean saveGame(String p_FileName) {
        boolean d_saved = false;
        try {
            d_GameFile = new File(p_FileName);
            if (!d_GameFile.createNewFile()) {

            }

            FileOutputStream d_FileOut = new FileOutputStream(p_FileName);
            ObjectOutputStream d_ObjectOut = new ObjectOutputStream(d_FileOut);
            d_ObjectOut.writeObject(d_GE);
            System.out.println("Game Saved Successfully as" + " " + p_FileName);
            d_saved = true;
        } catch (IOException e) {
            e.printStackTrace();
            d_saved = false;
        }
        return d_saved;
    }

    /**
     * Save Game implementation
     * @param p_FileName takes input as a string to load file with the name
     * @return true if game is loaded
     */
    public boolean loadGame(String p_FileName) {
        boolean d_loaded = false;
        try {
            FileInputStream d_FileIn = new FileInputStream(p_FileName);
            ObjectInputStream d_ObjectIn = new ObjectInputStream(d_FileIn);

            d_loadGameEngine = (GameEngine) d_ObjectIn.readObject();
            d_loaded = true;
        } catch (EOFException f) {
//                f.printStackTrace();
            System.out.println("Error: could not load game");
        } catch (IOException e) {
            System.out.println("Error: file does not exist");
            d_loaded = false;
        } catch (ClassNotFoundException e) {
//            e.printStackTrace();
            System.out.println("Error: could not load game");
        }
        return d_loaded;
    }

    /**
     * Deserealizing the object stream which has the saved game in serealized form
     * @param d_GameFile takes input as a gamefile
     */
    public void deserealize(File d_GameFile) {
        try {
            File file = d_GameFile;
            FileInputStream fin = new FileInputStream(d_GameFile);
            ObjectInputStream in = new ObjectInputStream(fin);

            while (true) {
                System.out.println(in.readObject().toString());
            }

        } catch (EOFException e) {
            System.out.println("----------------");
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    /**
     * Run Save Game method to trigger savegame from command
     * @param p_FileName filename
     */
    public void runSaveGame(String p_FileName) {
        saveGame(p_FileName);
    }

    /**
     * Run Load Game method to trigger loadgame from command
     * @param p_FileName filename
     * @return Game Engine with parameters same as when game was saved
     */
    public GameEngine runLoadGame(String p_FileName) {
        loadGame(p_FileName);
        return d_loadGameEngine;
    }

    /**
     * Getter for the loaded game engine after loading a game
     * @return loaded game engine
     */
    public GameEngine getD_loadGameEngine() {
        return d_loadGameEngine;
    }
}
