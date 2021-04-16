package team14.warzone.GameEngine.State;

import team14.warzone.Console.Console;
import team14.warzone.GameEngine.GameEngine;
import team14.warzone.GameEngine.Player;
import team14.warzone.MapModule.AdapterMapEditor;
import team14.warzone.MapModule.Country;
import team14.warzone.MapModule.Map;
import team14.warzone.MapModule.MapEditorConquest;

import javax.swing.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

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
    private AdapterMapEditor d_MapEditor;
    /**
     * tournament table
     */
    private String[][] d_GameTable;
    /**
     * Current Map object
     */
    private Map d_CurrentMap;

    /**
     * Constructor
     *
     * @param p_GameEngine Game Engine object
     */
    public Tournament(GameEngine p_GameEngine) {
        super(p_GameEngine);
        d_MapEditor = new AdapterMapEditor(new MapEditorConquest());
    }

    /**
     * adds to map list d_Maps
     *
     * @param p_MapList List of maps
     */
    public void tournamentAddMaps(List<String> p_MapList) {
        d_Maps = new ArrayList<>();
        for (String p_MapFileName : p_MapList) {
            try {

                d_MapEditor.loadMap(p_MapFileName);
                if (d_MapEditor.validateMap(d_MapEditor.d_LoadedMap))
                    d_Maps.add(d_MapEditor.d_LoadedMap);
                else
                    System.out.println(p_MapFileName + " map is not valid.");
            } catch (Exception e) {
                System.out.println(p_MapFileName + " file does not exist.");
                e.printStackTrace();
            }

        }
    }

    /**
     * adds to player list d_Players
     *
     * @param p_Strategies List of strategies
     */
    public void tournamentAddPlayersStrategies(List<String> p_Strategies) {
        d_Players = new ArrayList<>();
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
        d_GameEngine.setD_PlayerList(d_Players);
    }

    /**
     * Number of games
     *
     * @param p_NumOfGames integer value for num of games
     */
    public void tournamentNumOfGames(String p_NumOfGames) {
        this.d_NumOfGames = Integer.parseInt(p_NumOfGames);
    }

    /**
     * Max turns
     *
     * @param p_NumOfTurns number of turns
     */
    public void tournamentMaxNumOfTurns(String p_NumOfTurns) {
        this.d_NumOfTurns = Integer.parseInt(p_NumOfTurns);
    }

    /**
     * Run method
     */
    @Override
    public void run() {
        d_GameEngine.setD_TournamentMode(true);
        d_GameTable = new String[d_Maps.size()][d_NumOfGames];
        for (int l_MapIndex = 0; l_MapIndex < d_Maps.size(); l_MapIndex++) {

            for (int l_GameCount = 0; l_GameCount < d_NumOfGames; l_GameCount++) {
                d_GameEngine.setD_CurrentPhase(d_GameEngine.getD_StartupPhase());
                d_GameEngine.setD_GameOver(false);
                d_GameEngine.resetPlayers();
                d_CurrentMap = d_Maps.get(l_MapIndex);
                d_GameEngine.setD_LoadedMap(d_CurrentMap);
                assignCountries();// assign countries to players for current map

                while (d_GameEngine.getD_CurrentNumberOfTurns() < d_NumOfTurns && !d_GameEngine.isD_GameOver()) {
                    d_GameEngine.getD_CurrentPhase().run();
                }
                d_GameEngine.setD_CurrentNumberOfTurns(0);

                ArrayList<Integer> l_IndexListOfWinners = determineWinner();
                String l_WinningValue = "";
                if (l_IndexListOfWinners.size() > 1) {
                    System.out.println(l_IndexListOfWinners.get(0) + " " + l_IndexListOfWinners.get(1));
                    for (int i = 0; i < l_IndexListOfWinners.size(); i++) {
                        l_WinningValue =
                                l_WinningValue + " " + d_Players.get(l_IndexListOfWinners.get(i)).getD_Name() + " "
                                        + d_Players.get(l_IndexListOfWinners.get(i)).getD_IssueOrderBehavior().toString();
                    }
                    System.out.println("The game is a draw between " + l_WinningValue);
                    System.out.println("____________________________________________________________");
                    d_GameTable[l_MapIndex][l_GameCount] = "Draw between " + l_WinningValue;
                } else if (l_IndexListOfWinners.size() == 1) {
                    l_WinningValue = l_WinningValue + " " + d_Players.get(l_IndexListOfWinners.get(0)).getD_Name() + " "
                            + d_Players.get(l_IndexListOfWinners.get(0)).getD_IssueOrderBehavior().toString();
                    System.out.println("The winner is " + l_WinningValue + " with owning countries " + d_Players.get(l_IndexListOfWinners.get(0)).getD_CountriesOwned().size());
                    System.out.println(
                            "_____________________________________________________________________________________");
                    d_GameTable[l_MapIndex][l_GameCount] = l_WinningValue;
                } else
                    System.out.println("The game does not have any winner");
            }
        }
        // set tournament end flag
        d_GameEngine.setD_TournamentEnded(true);
        //show in table
        String[] l_ColumnNames = new String[d_NumOfGames];
        for (int l_IndexColumn = 0; l_IndexColumn < d_NumOfGames; l_IndexColumn++) {
            l_ColumnNames[l_IndexColumn] = "Game" + (l_IndexColumn + 1);
        }
        JTable l_Table = new JTable(d_GameTable, l_ColumnNames);

        //Create and set up the window.
        JFrame l_Frame = new JFrame("Tournament");

        //Create and set up the content pane.
        l_Frame.add(new JScrollPane(l_Table));

        //Display the window.
        l_Frame.pack();
        l_Frame.setVisible(true);
        l_Frame.setDefaultCloseOperation (JFrame.EXIT_ON_CLOSE);
        
        Console.displayMsg("This Tournament has ended. Close frame to exit game.");
    }

    /**
     * Determine winner of tounrament
     *
     * @return Winning player index
     */
    private ArrayList<Integer> determineWinner() {
        ArrayList<Integer> l_NumCountriesOwned = new ArrayList<>();//num of countries for each player
        for (int l_PCount = 0; l_PCount < d_Players.size(); l_PCount++) {
            l_NumCountriesOwned.add(d_Players.get(l_PCount).getD_CountriesOwned().size());
        }
        int l_MaxVal = Collections.max(l_NumCountriesOwned);
        ArrayList<Integer> l_IndexListOfWinners = new ArrayList<>();
        for (int i = 0; i < l_NumCountriesOwned.size(); i++) {
            if (l_NumCountriesOwned.get(i) == l_MaxVal) {
                l_IndexListOfWinners.add(i);
            }
        }
        return l_IndexListOfWinners;
    }

    /**
     * Add country
     *
     * @param p_CountryId   Country id
     * @param p_ContinentId Continent
     */
    @Override
    public void addCountry(String p_CountryId, String p_ContinentId) {
        // TODO Auto-generated method stub

    }

    /**
     * Remove country
     *
     * @param p_CountryId Country name
     */
    @Override
    public void removeCountry(String p_CountryId) {
        // TODO Auto-generated method stub

    }

    /**
     * Add continent
     *
     * @param p_ContinentId  Continent name
     * @param p_ControlValue Control value
     */
    @Override
    public void addContinent(String p_ContinentId, int p_ControlValue) {
        // TODO Auto-generated method stub

    }

    /**
     * Remove continent
     *
     * @param p_ContinentId Continent name
     */
    @Override
    public void removeContinent(String p_ContinentId) {
        // TODO Auto-generated method stub

    }

    /**
     * Add neighbor
     *
     * @param p_CountryId  country
     * @param p_NeighborId neighbor
     */
    @Override
    public void addNeighbor(String p_CountryId, String p_NeighborId) {
        // TODO Auto-generated method stub

    }

    /**
     * Remove neighbor
     *
     * @param p_CountryId  country
     * @param p_NeighborId neighbor
     */
    @Override
    public void removeNeighbor(String p_CountryId, String p_NeighborId) {
        // TODO Auto-generated method stub

    }

    /**
     * Load map from file
     *
     * @param p_FileName file name
     */
    @Override
    public void loadMap(String p_FileName) {
        // TODO Auto-generated method stub

    }

    /**
     * Save map to file
     *
     * @param p_FileName file name
     */
    @Override
    public void saveMap(String p_FileName, String p_MapType) {
        // TODO Auto-generated method stub

    }

    /**
     * Edit map file
     *
     * @param p_FileName file name
     */
    @Override
    public void editMap(String p_FileName) {
        // TODO Auto-generated method stub

    }

    /**
     * Validate loaded map
     *
     * @param p_Map map object
     */
    @Override
    public void validateMap(Map p_Map) {
        // TODO Auto-generated method stub

    }

    /**
     * Add player to list
     *
     * @param p_Name       Name of player
     * @param p_PlayerType Type of player
     */
    @Override
    public void addPlayer(String p_Name, String p_PlayerType) {
        // TODO Auto-generated method stub

    }

    /**
     * Remove player from list
     *
     * @param p_Name name of player
     */
    @Override
    public void removePlayer(String p_Name) {
        // TODO Auto-generated method stub

    }

    /**
     * Assign countries to players
     */
    @Override
    public void assignCountries() {
        // if number of players between 2 and 5, assign countries to players randomly
        if (d_Players.size() >= 2 && d_Players.size() <= 5) {
            int l_CountryCounter = 0;
            ArrayList<Country> l_Countries = d_CurrentMap.getD_Countries();
            while (l_CountryCounter < l_Countries.size()) {
                for (int l_PlayerIterator = 0; l_PlayerIterator < d_Players.size()
                        && l_CountryCounter < l_Countries.size(); l_PlayerIterator++) {
                    // add country to player's country-list
                    d_Players.get(l_PlayerIterator).addCountryOwned(l_Countries.get(l_CountryCounter));
                    // set country's current owner to player
                    l_Countries.get(l_CountryCounter).setD_CurrentOwner(d_Players.get(l_PlayerIterator).getD_Name());
                    l_CountryCounter++;
                }
            }
            Console.displayMsg("Success: countries assigned");
            d_GameEngine.setD_CurrentPhase(d_GameEngine.getD_IssueOrdersPhase());//change to issueOrder phase
        } else {
            Console.displayMsg("Failed: 2-5 players required");
        }
    }

    /**
     * Reinforcement
     */
    @Override
    public void reinforce() {

    }

    /**
     * Save game to file
     *
     * @param p_FileName name of file
     */
    @Override
    public void saveGame(String p_FileName) {

    }

    /**
     * Issue commands
     */
    @Override
    public void issueCommands() {
        // TODO Auto-generated method stub

    }

    /**
     * Show cards for player
     */
    @Override
    public void showCards() {

    }

    /**
     * Execute commands
     */
    @Override
    public void executeCommands() {

    }

    /**
     * Deploy
     */
    @Override
    public void deploy() {

    }

    /**
     * Advance
     */
    @Override
    public void advance() {

    }

    /**
     * Bomb
     */
    @Override
    public void bomb() {

    }

    /**
     * Blockade
     */
    @Override
    public void blockade() {

    }

    /**
     * Airflift
     */
    @Override
    public void airlift() {

    }

    /**
     * Diplomacy
     */
    @Override
    public void diplomacy() {

    }

}
