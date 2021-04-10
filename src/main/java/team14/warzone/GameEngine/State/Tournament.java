package team14.warzone.GameEngine.State;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;

import team14.warzone.GameEngine.GameEngine;
import team14.warzone.GameEngine.Player;
import team14.warzone.MapModule.Map;
import team14.warzone.MapModule.MapEditor;

/**
 * This is Tournament class which extends Phase
 * 
 * @author tanzia-ahmed
 */
public class Tournament extends Phase {
    /**
     * d_Maps is an ArrayList of Map
     */
    private ArrayList<Map> d_Maps;
    /**
     * d_Players is an ArrayList of Player
     */
    private ArrayList<Player> d_Players;
    /**
     * d_NumOfGames is the total number of games to be played among the Players
     */
    private int d_NumOfGames;
    /**
     * d_NumOfTurns is the total number of turns to be played per game
     */
    private int d_NumOfTurns;
    /**
     * instance of Map Editor
     */
    private MapEditor d_MapEditor;

    public Tournament(GameEngine p_GameEngine) {
        super(p_GameEngine);
        d_MapEditor = new MapEditor();
    }

    /**
     * adds to map list d_Maps
     * 
     * @param p_MapList
     */
    public void tournamentAddMaps(List<String> p_MapList) {
        for (String p_MapFileName : p_MapList) {
            try {
                d_MapEditor.loadMap(p_MapFileName);
            } catch (FileNotFoundException e) {
                System.out.println(p_MapFileName + " file does not exist.");
                e.printStackTrace();
            }
            if (d_MapEditor.validateMap(d_MapEditor.getD_LoadedMap()))
                d_Maps.add(d_MapEditor.getD_LoadedMap());
            else
                System.out.println(p_MapFileName + " map is not valid.");
        }
    }

    /**
     * adds to player list d_Players
     * 
     * @param p_Strategies
     */
    public void tournamentAddPlayersStrategies(List<String> p_Strategies) {
        int l_Count = 1;
        for (String p_AStrategy : p_Strategies) {
            if (p_AStrategy.equalsIgnoreCase("aggressive") || p_AStrategy.equalsIgnoreCase("benevolent")
                    || p_AStrategy.equalsIgnoreCase("random") || p_AStrategy.equalsIgnoreCase("cheater")) {
                Player l_NewPlayer = new Player("P" + l_Count, p_AStrategy, this.d_GameEngine);
                d_Players.add(l_NewPlayer);
                l_Count++;
            } else
                System.out.println(p_AStrategy + " is not valid or allowed to play tournament.");
        }
        this.assignCountries();
    }

    /**
     * @param p_NumOfTurns
     */
    public void tournamentMaxNumOfTurns(String p_NumOfTurns) {
        this.d_NumOfTurns = Integer.parseInt(p_NumOfTurns);
    }

    /**
     * @param p_NumOfGames
     */
    public void tournamentNumOfGames(String p_NumOfGames) {
        this.d_NumOfGames = Integer.parseInt(p_NumOfGames)
    }

    @Override
    public void run() {
        for (Map l_map : d_Maps) {
            for (int l_Index = 0; l_Index < d_NumOfGames; l_Index++) {

            }
        }
    }

    /**
     * @param p_CountryId
     * @param p_ContinentId
     */
    @Override
    public void addCountry(String p_CountryId, String p_ContinentId) {
        // TODO Auto-generated method stub

    }

    /**
     * @param p_CountryId
     */
    @Override
    public void removeCountry(String p_CountryId) {
        // TODO Auto-generated method stub

    }

    /**
     * @param p_ContinentId
     * @param p_ControlValue
     */
    @Override
    public void addContinent(String p_ContinentId, int p_ControlValue) {
        // TODO Auto-generated method stub

    }

    /**
     * @param p_ContinentId
     */
    @Override
    public void removeContinent(String p_ContinentId) {
        // TODO Auto-generated method stub

    }

    /**
     * @param p_CountryId
     * @param p_NeighborId
     */
    @Override
    public void addNeighbor(String p_CountryId, String p_NeighborId) {
        // TODO Auto-generated method stub

    }

    /**
     * @param p_CountryId
     * @param p_NeighborId
     */
    @Override
    public void removeNeighbor(String p_CountryId, String p_NeighborId) {
        // TODO Auto-generated method stub

    }

    /**
     * @param p_FileName
     */
    @Override
    public void loadMap(String p_FileName) {
        // TODO Auto-generated method stub

    }

    /**
     * @param p_FileName
     */
    @Override
    public void saveMap(String p_FileName) {
        // TODO Auto-generated method stub

    }

    /**
     * @param p_FileName
     */
    @Override
    public void editMap(String p_FileName) {
        // TODO Auto-generated method stub

    }

    /**
     * @param p_Map
     */
    @Override
    public void validateMap(Map p_Map) {
        // TODO Auto-generated method stub

    }

    /**
     * @param p_Name
     * @param p_PlayerType
     */
    @Override
    public void addPlayer(String p_Name, String p_PlayerType) {
        // TODO Auto-generated method stub

    }

    /**
     * @param p_Name
     */
    @Override
    public void removePlayer(String p_Name) {
        // TODO Auto-generated method stub

    }

    @Override
    public void assignCountries() {
        // TODO Auto-generated method stub

    }

    @Override
    public void reinforce() {
        // TODO Auto-generated method stub

    }

    /**
     * @param p_FileName
     */
    @Override
    public void saveGame(String p_FileName) {
        // TODO Auto-generated method stub

    }

    /**
     * @param p_FileName
     */
    @Override
    public void loadGame(String p_FileName) {
        // TODO Auto-generated method stub

    }

    @Override
    public void issueCommands() {
        // TODO Auto-generated method stub

    }

    @Override
    public void showCards() {
        // TODO Auto-generated method stub

    }

    @Override
    public void executeCommands() {
        // TODO Auto-generated method stub

    }

    @Override
    public void deploy() {
        // TODO Auto-generated method stub

    }

    @Override
    public void advance() {
        // TODO Auto-generated method stub

    }

    @Override
    public void bomb() {
        // TODO Auto-generated method stub

    }

    @Override
    public void blockade() {
        // TODO Auto-generated method stub

    }

    @Override
    public void airlift() {
        // TODO Auto-generated method stub

    }

    @Override
    public void diplomacy() {
        // TODO Auto-generated method stub

    }

}
