package team14.warzone.GameEngine;

import java.io.*;

/**
 * This class implements the Save Game and Load Game feature
 */
public class GameSaveLoad implements Serializable {

    private File d_GameFile;
    private GameEngine d_GE;
    private GameEngine d_loadGameEngine;

    public GameSaveLoad(GameEngine p_GE) {
        d_GE = p_GE;
    }

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

    public boolean loadGame(String p_FileName) {
        boolean d_loaded = false;
        try {
            FileInputStream d_FileIn = new FileInputStream(p_FileName);
            ObjectInputStream d_ObjectIn = new ObjectInputStream(d_FileIn);

            d_loadGameEngine = (GameEngine) d_ObjectIn.readObject();
            d_loaded = true;
        } catch (EOFException f) {
                f.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
            d_loaded = false;
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return d_loaded;
    }

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

    public void runSaveGame(String p_FileName) {
        saveGame(p_FileName);
    }

    public GameEngine runLoadGame(String p_FileName) {
        loadGame(p_FileName);
        return d_loadGameEngine;
    }
}
