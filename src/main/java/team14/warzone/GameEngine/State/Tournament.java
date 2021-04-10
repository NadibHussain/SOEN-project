package team14.warzone.GameEngine.State;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
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
    /**
     * tournament table
     */
    private String [][] d_GameTable;
    /**
     * Constructor
     * @param p_GameEngine
     */

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
     * @param p_NumOfGames
     */
    public void tournamentNumOfGames(String p_NumOfGames) {
        this.d_NumOfGames = Integer.parseInt(p_NumOfGames);
    }

    /**
     * @param p_NumOfTurns
     */
    public void tournamentMaxNumOfTurns(String p_NumOfTurns) {
        this.d_NumOfTurns = Integer.parseInt(p_NumOfTurns);
    }

    @Override
    public void run() {
        d_GameTable = new String [d_Maps.size()][d_NumOfGames];
        for (int l_MapIndex=0; l_MapIndex< d_Maps.size(); l_MapIndex++) {
            for (int l_GameCount = 0; l_GameCount < d_NumOfGames; l_GameCount++) {
                for (int l_TurnCount = 0; l_TurnCount < d_NumOfTurns; l_TurnCount++) {
                    for (Player l_APlayer : d_Players) {
                        l_APlayer.issueOrder();
                    }
                }
                ArrayList<Integer> l_IndexListOfWinners=determineWinner();
                String l_PlayerBehaviors = "";
                if(l_IndexListOfWinners.size()>1){
                    for(int i=0; i<l_IndexListOfWinners.size(); i++){
                        l_PlayerBehaviors = l_PlayerBehaviors+" "+d_Players.get(l_IndexListOfWinners.get(i)).getD_IssueOrderBehavior();
                    }
                    System.out.println("The game is a draw between "+ l_PlayerBehaviors);
                    d_GameTable[l_MapIndex][l_GameCount] = "Draw between "+ l_PlayerBehaviors;
                }
                if(l_IndexListOfWinners.size() == 1){
                    l_PlayerBehaviors = l_PlayerBehaviors+" "+d_Players.get(l_IndexListOfWinners.get(0)).getD_Name();
                    System.out.println("The winner is "+ l_PlayerBehaviors);
                    d_GameTable[l_MapIndex][l_GameCount] = l_PlayerBehaviors;
                }else System.out.println("The game does not have any winner");
            }
        }
       
    }

    private ArrayList<Integer> determineWinner() {
        ArrayList<Integer> l_maxCoOwn = new ArrayList<>();
        for(int l_PCount=0; l_PCount< d_Players.size(); l_PCount++){
            l_maxCoOwn.add(d_Players.get(l_PCount).getD_CountriesOwned().size());
        }
        int l_maxVal = Collections.max(l_maxCoOwn);
        ArrayList<Integer> l_IndexListOfWinners = new ArrayList<>();
        for(int i=0;i<l_maxCoOwn.size();i++){
            l_IndexListOfWinners.add(l_maxCoOwn.indexOf(l_maxVal));
        }              
        return l_IndexListOfWinners;
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
