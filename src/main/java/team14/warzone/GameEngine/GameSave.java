package team14.warzone.GameEngine;
import team14.warzone.MapModule.Continent;
import team14.warzone.Console.Console;
import team14.warzone.MapModule.Country;


import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class GameSave {

    private File d_GameFile;
    private FileWriter d_GameFileWriter;
    private GameEngine d_GE;

    public GameSave(GameEngine p_GE) {
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

    public void saveContinents() {
        try {
            this.d_GameFileWriter.write("[continents]" + System.lineSeparator());

            for(Continent itr : d_GE.getD_LoadedMap().getD_Continents()) {

                this.d_GameFileWriter.write(itr.getD_ContinentIntID() + "," + itr.getD_ContinentID() + "," + itr.getD_ControlValue() + "," + itr.getD_CurrentOwners() + System.lineSeparator());
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void saveCountries() {
        try {
            this.d_GameFileWriter.write("[countries]" + System.lineSeparator());

            for(Country itr : d_GE.getD_LoadedMap().getD_Countries()) {

                this.d_GameFileWriter.write(itr.getD_CountryIntID() + "," + itr.getD_CountryID() + "," + itr.getD_CountryContinentID()+ "," + itr.getD_CurrentOwner()+ "," + itr.getD_NumberOfArmies() + System.lineSeparator());
                this.d_GameFileWriter.write("[borders]" + System.lineSeparator());
                for (Country itr1 : itr.getD_Neighbours()) {
                    this.d_GameFileWriter.write(itr1.getD_CountryID() + ",");
                }
                this.d_GameFileWriter.write(System.lineSeparator());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void savePlayers() {
        try {
            this.d_GameFileWriter.write("[Players]" + System.lineSeparator());

            if (d_GE.getD_PlayerList().size() >= 1) {
                for (Player itr : d_GE.getD_PlayerList()){
                    this.d_GameFileWriter.write(itr.getD_Name() + "," + itr.getD_TotalNumberOfArmies() + "," + itr.getD_ArmiesOrderedToBeDeployed() + "," + itr.getD_CountriesOwned() + "," + itr.getD_OrderList() + "," + itr.getCardList() + "," + itr.getD_DiplomaticPlayerList() + System.lineSeparator());
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void saveCurrentGameStatus() {
        try {
            this.d_GameFileWriter.write("[CurrentGamePhase]" + System.lineSeparator());
            if (d_GE.getD_CurrentPhase() != null) {
                this.d_GameFileWriter.write(String.valueOf(d_GE.getD_CurrentPhase()) + System.lineSeparator());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public void saveCurrentPlayer() {
        try {
            this.d_GameFileWriter.write("[CurrentPlayer]" + System.lineSeparator());
            if (d_GE.getD_CurrentPlayer() != null) {
                this.d_GameFileWriter.write(String.valueOf(d_GE.getD_CurrentPlayer()) + System.lineSeparator());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public void runSaveGame(String p_FileName) {
        try {
            setD_GameFile(p_FileName);
        } catch (IOException e) {
            Console.displayMsg(e.getMessage());
        }
        saveContinents();
        saveCountries();
        savePlayers();
        saveCurrentGameStatus();
        saveCurrentPlayer();
    }

    public void runLoadGame(String p_FileName) {

    }
}
