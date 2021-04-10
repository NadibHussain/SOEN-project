package team14.warzone.GameEngine;

import java.io.*;

public class GameSaveLoad implements Serializable {

    private File d_GameFile;
    private GameEngine d_GE;

    public GameSaveLoad(GameEngine p_GE) {
        d_GE = p_GE;
    }
    public boolean saveGame(String p_FileName) {
        boolean d_saved = false;
        try {
            d_GameFile = new File(p_FileName + ".wrz");
            if (!d_GameFile.createNewFile()) {
                System.out.println("Overriding " + p_FileName + ".wrz");
            }

            FileOutputStream d_FileOut = new FileOutputStream(p_FileName);
            ObjectOutputStream d_ObjectOut = new ObjectOutputStream(d_FileOut);
            d_ObjectOut.writeObject(d_GE.getD_LoadedMap().getD_Continents());
            d_ObjectOut.writeObject(d_GE.getD_LoadedMap().getD_Countries());
            d_ObjectOut.writeObject(d_GE.getD_PlayerList());
            d_ObjectOut.writeObject(d_GE.getD_CurrentPlayer());
            d_ObjectOut.writeObject(d_GE.getD_CurrentPhase());
            d_ObjectOut.close();
            d_ObjectOut.flush();
            System.out.println("Game Saved Successfully as" + " " + p_FileName);
            d_saved = true;
        } catch (IOException e) {
            e.printStackTrace();
            d_saved = false;
        }
        return d_saved;
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
/**
    private File d_GameFile;
    private FileWriter d_GameFileWriter;
    private GameEngine d_GE;

    public GameSaveLoad(GameEngine p_GE) {
        d_GE = p_GE;
    }
    public void setD_GameFile(String p_GameFileName) throws IOException {
        d_GameFile = new File(p_GameFileName);
        d_GameFile.createNewFile();
        d_GameFileWriter = new FileWriter(d_GameFile);
    }

    public File getD_GameFile() {
        return d_GameFile;
    }

    public void saveGame(String p_FileName) {
        try {

            FileOutputStream d_FileOut = new FileOutputStream(d_GameFile);
            ObjectOutputStream d_ObjectOut = new ObjectOutputStream(d_FileOut);
            d_ObjectOut.writeObject(d_GE.getD_LoadedMap().getD_Continents());
            d_ObjectOut.writeObject(d_GE.getD_LoadedMap().getD_Countries());
            d_ObjectOut.writeObject(d_GE.getD_PlayerList());
            d_ObjectOut.writeObject(d_GE.getD_CurrentPlayer());
            d_ObjectOut.writeObject(d_GE.getD_CurrentPhase());
            d_ObjectOut.close();
            d_ObjectOut.flush();
            System.out.println("Game Saved Successfully as" + p_FileName);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    */
    public void runSaveGame(String p_FileName) {

        saveGame(p_FileName);
        deserealize(d_GameFile);
    }

    public GameEngine runLoadGame(String p_FileName) {
        return null;
    }
}
