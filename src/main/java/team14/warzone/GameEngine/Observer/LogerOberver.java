package team14.warzone.GameEngine.Observer;

import team14.warzone.Console.Console;
import team14.warzone.GameEngine.GameEngine;
import team14.warzone.GameEngine.Player;
import team14.warzone.GameEngine.State.Phase;
import team14.warzone.MapModule.Continent;
import team14.warzone.MapModule.Country;
import team14.warzone.MapModule.Map;
import team14.warzone.MapModule.MapEditor;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class LogerOberver implements Observer{

    GameEngine d_CurrentGameEngine;

    public LogerOberver(){
        d_CurrentGameEngine = new GameEngine(new Console(),new MapEditor());
        d_CurrentGameEngine.setD_LoadedMap(new Map());
    }

    @Override
    public void update(Observable p_observable) {
        GameEngine l_GameEngine = (GameEngine)p_observable;
//        add every check method here
//        checkPlayerList(d_CurrentGameEngine.getD_PlayerList(),l_GameEngine.getD_PlayerList());
//        checkGameState(d_CurrentGameEngine.getD_CurrentPhase(),l_GameEngine.getD_CurrentPhase());
        checkCountryList(d_CurrentGameEngine.getD_LoadedMap().d_Countries, l_GameEngine.getD_LoadedMap().d_Countries);
//        checkContinentList(d_CurrentGameEngine.getD_LoadedMap().d_Continents,l_GameEngine.getD_LoadedMap().d_Continents);
//        checkNeighbourList(d_CurrentGameEngine.getD_LoadedMap().d_Countries, l_GameEngine.getD_LoadedMap().d_Countries);

//
        try {
            d_CurrentGameEngine = (GameEngine) l_GameEngine.clone();
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
        }

    }


    /**
     * Writes logs file inside logs.txt file
     * @param p_Log the log message that needs to be printed in the logs.txt file
     */
    private static void writeLogs(String p_Log)
    {
        String l_ExistingLogs="";
        try {
            File l_file = new File("./logs.txt");
            FileInputStream l_FileInputStream = new FileInputStream(l_file);
            byte[]data = new byte[(int) l_file.length()];
            l_FileInputStream.read(data);
            l_FileInputStream.close();
            l_ExistingLogs = new String(data, "UTF-8");

        } catch (IOException e) {
            System.out.println("new logs file created");
        }

        try {
            FileWriter l_Writer = new FileWriter("logs.txt");
            l_Writer.write(l_ExistingLogs+"\n"+p_Log);
            l_Writer.close();
        } catch (IOException l_IOException) {
            System.out.println("An error occurred.");
            l_IOException.printStackTrace();
        }
    }

    /**
     * Checks if there is any changes in the playerlist and creates logs accordingly
     * @param p_CurrentPlayerList list of players inside current game engine
     * @param p_NewPlayerList list of players inside current previous game engine
     */
    private void checkPlayerList(ArrayList<Player> p_CurrentPlayerList,ArrayList<Player> p_NewPlayerList){

    }
    /**
     * Checks if game Phase has changed
     * @param p_CurrentGamePhase phase of the current game engine
     * @param p_NewGamePhase phase of the new game engine
     */
    private void checkGameState(Phase p_CurrentGamePhase, Phase p_NewGamePhase){

    }
    /**
     * Checks if map country has changed
     * @param p_CurrentCountryList list of countries inside current loaded map
     * @param p_NewCountryList list of countries inside new loaded map
     */
    private void checkCountryList(ArrayList<Country> p_CurrentCountryList, ArrayList<Country> p_NewCountryList){
        System.out.println(p_CurrentCountryList.size());
        System.out.println(p_NewCountryList.size());
        if(p_NewCountryList.size()-p_CurrentCountryList.size()>10)
        {
            writeLogs("A new Map has been loaded");
            return;
        }
       if (p_CurrentCountryList.size()>p_NewCountryList.size()||p_CurrentCountryList.size()<p_NewCountryList.size())
       {
           String l_Operation = "";
           ArrayList<Country> l_ChangedCountry = new ArrayList<>();
           if (p_CurrentCountryList.size()>p_NewCountryList.size())
           {
               l_Operation="removed";
               for (Country l_CurrentCountry:p_CurrentCountryList) {
                   boolean l_matched = false;
                   for (Country l_NewCountry:p_NewCountryList) {
                       if (l_CurrentCountry.getD_CountryID().equals(l_NewCountry.getD_CountryID()))
                       {
                           l_matched=true;
                           break;
                       }
                   }
                   if (!l_matched)
                   {
                       l_ChangedCountry.add(l_CurrentCountry);
                   }
               }
           }else {
               l_Operation="added";
               for (Country l_NewCountry:p_NewCountryList) {
                   boolean l_matched = false;
                   for (Country l_CurrentCountry:p_CurrentCountryList) {
                       if (l_CurrentCountry.getD_CountryID().equals(l_NewCountry.getD_CountryID()))
                       {
                           l_matched=true;
                           break;
                       }
                   }
                   if (!l_matched)
                   {
                       l_ChangedCountry.add(l_NewCountry);
                   }
               }
           }
           for (Country l_Country:l_ChangedCountry) {
               writeLogs(l_Country.getD_CountryID()+" has been "+l_Operation);
           }

       }

    }
    /**
     * Checks if map Continent has changed
     * @param p_CurrentContinentList list of Continents inside current loaded map
     * @param p_NewContinentList list of Continents inside new loaded map
     */
    private void checkContinentList(ArrayList<Continent> p_CurrentContinentList, ArrayList<Continent> p_NewContinentList){

    }

    /**
     * Checks if map Neighbour has changed
     * @param p_CurrentNeighbourList list of Neighbour inside current loaded map
     * @param p_NewNeighbourList list of Neighbour inside new loaded map
     */
    private void checkNeighbourList(ArrayList<Country> p_CurrentNeighbourList, ArrayList<Country> p_NewNeighbourList){

    }
}
