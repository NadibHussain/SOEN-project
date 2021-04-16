package team14.warzone.GameEngine.State;

import team14.warzone.GameEngine.Commands.AdminCommands;
import team14.warzone.GameEngine.Commands.Option;
import team14.warzone.GameEngine.GameEngine;
import team14.warzone.MapModule.Map;

import java.io.Serializable;
import java.util.Arrays;
import java.util.List;

/**
 * This class
 */
public abstract class Phase implements Serializable {

    /**
     * Reference to the game engine obj
     */
    GameEngine d_GameEngine;

    /**
     * phase
     *
     * @param p_GameEngine gameengine
     */
    public Phase(GameEngine p_GameEngine) {
        d_GameEngine = p_GameEngine;
    }

    /**
     * run method
     */
    abstract public void run();

    /**
     * showmap method
     */
    public void showMap() {
        if (d_GameEngine.getD_LoadedMap() == null && d_GameEngine.getD_MapEditor().getD_LoadedMap() == null)
            System.out.println("Please load a map first!");
        else if (d_GameEngine.getD_LoadedMap() != null)
            d_GameEngine.getD_LoadedMap().showMap();
        else
            d_GameEngine.getD_MapEditor().getD_LoadedMap().showMap();
    }

    /**
     * invalid command message
     */
    public void invalidCommandMessage() {
        System.out.println("Error: command invalid for " + this.getClass().getSimpleName() + " phase");
    }

    /**
     * next method
     */
    public void next() {
        if (d_GameEngine.getD_CurrentPhase() instanceof PreMapLoadPhase)
            d_GameEngine.setD_CurrentPhase(d_GameEngine.getD_PostMapEditLoadPhase());
        else if (d_GameEngine.getD_CurrentPhase() instanceof PostMapEditLoadPhase)
            d_GameEngine.setD_CurrentPhase(d_GameEngine.getD_StartupPhase());
        else if (d_GameEngine.getD_CurrentPhase() instanceof StartupPhase)
            d_GameEngine.setD_CurrentPhase(d_GameEngine.getD_IssueOrdersPhase());
        else if (d_GameEngine.getD_CurrentPhase() instanceof IssueOrdersPhase)
            d_GameEngine.setD_CurrentPhase(d_GameEngine.getD_ExecuteOrdersPhase());
        else if (d_GameEngine.getD_CurrentPhase() instanceof ExecuteOrdersPhase)
            d_GameEngine.setD_CurrentPhase(d_GameEngine.getD_IssueOrdersPhase());
    }

    /**
     * creating commands
     *
     * @param p_CommandStrList command list
     */
    public void createAdminCommand(List<List<String>> p_CommandStrList) {
        for (List<String> l_CommandStr : p_CommandStrList) {
            Option l_Option = new Option(l_CommandStr.get(1),
                    Arrays.asList(l_CommandStr.get(2).replaceAll(" ", "").split(
                            ",")));
            AdminCommands l_AdminCommands = new AdminCommands(l_CommandStr.get(0), l_Option, d_GameEngine);
            d_GameEngine.appendToCommandBuffer(l_AdminCommands);
        }
    }

    /**
     * end game method
     */
    public void endGame() {
    }

    /**
     * Adds country to the loaded-map in the game engine
     *
     * @param p_CountryId   Country ID which the needs is to be added
     * @param p_ContinentId Name of the Continent country to be added
     */
    abstract public void addCountry(String p_CountryId, String p_ContinentId);

    /**
     * removes country to the loaded-map in the game engine
     *
     * @param p_CountryId Country ID which the needs is to be removed
     */
    abstract public void removeCountry(String p_CountryId);

    /**
     * Adds Continent to the loaded-map in the game engine
     *
     * @param p_ContinentId  Continent ID which the needs is to be added
     * @param p_ControlValue amount of extra re-enforcement a player will get if concurred
     */
    abstract public void addContinent(String p_ContinentId, int p_ControlValue);

    /**
     * Removes Continent to the loaded-map in the game engine
     *
     * @param p_ContinentId Continent ID which the needs is to be removed
     */
    abstract public void removeContinent(String p_ContinentId);

    /**
     * Makes 2 country neighbour of each other
     *
     * @param p_CountryId  ID of the first country
     * @param p_NeighborId ID of the other country
     */
    abstract public void addNeighbor(String p_CountryId, String p_NeighborId);

    /**
     * Makes 2 neighbour country not neighbour anymore
     *
     * @param p_CountryId  Country from which the neighbour is to be removed
     * @param p_NeighborId Name of the neighbour to be removed
     */
    abstract public void removeNeighbor(String p_CountryId, String p_NeighborId);

    /**
     * load .map file as Map object in the game engine
     *
     * @param p_FileName Path of the .map file
     */
    abstract public void loadMap(String p_FileName);

    /**
     * save Map object as .map file
     *
     * @param p_FileName Path of the .map file
     * @param p_MapType  Type of map
     */
    abstract public void saveMap(String p_FileName, String p_MapType);

    /**
     * Needed command before editing the Map object
     *
     * @param p_FileName Path of the .map file
     */
    abstract public void editMap(String p_FileName);

    /**
     * Validate the map
     *
     * @param p_Map Map object that we want to validate
     */
    abstract public void validateMap(Map p_Map);

    // gameplay phase: startup, reinforce, issue, execute
    // startup state

    /**
     * Adding players in the game
     *
     * @param p_Name       Name of the player
     * @param p_PlayerType Behavior type of player
     */
    abstract public void addPlayer(String p_Name, String p_PlayerType);

    /**
     * Removing players in the game
     *
     * @param p_Name Name of the player
     */
    abstract public void removePlayer(String p_Name);

    /**
     * Assign and divide all countries among all players
     */
    abstract public void assignCountries();

    // reinforce phase

    /**
     * Used in reinforcement phase
     */
    abstract public void reinforce();

    /**
     * Used for saving a game
     *
     * @param p_FileName filename
     */
    abstract public void saveGame(String p_FileName);

    /**
     * takes command in round robin manner in the play phase
     */
    abstract public void issueCommands(); // instant for map phase and round robin for play phase

    /**
     * display the list of cards in current players possession
     */
    abstract public void showCards(); // instant for map phase and round robin for play phase


    // execute order phase

    /**
     * execute each command that is in the list and take appropriate action
     */
    abstract public void executeCommands(); // instant for map phase and round robin for play phase

    /**
     * place some armies on one of the current players territories.
     */
    abstract public void deploy();

    /**
     * move some armies from one of the current players territories (source) to an adjacent territory
     * (target).
     */
    abstract public void advance();

    /**
     * destroy half of the armies located on an opponents territory that is adjacent to one of the current
     * players territories
     */
    abstract public void bomb();

    /**
     * triple the number of armies on one of the current players territories and make it a neutral territory
     */
    abstract public void blockade();

    /**
     * advance some armies from one of the current players territories to any another territory
     */
    abstract public void airlift();

    /**
     * prevent attacks between the current player and another player until the end of the turn.
     */
    abstract public void diplomacy();


    /**
     * @param p_Maps maps for tournament
     */
    public void tournamentAddMaps(List<String> p_Maps) {
    }


    /**
     * @param p_Strategies player strategies for tournament
     */
    public void tournamentAddPlayersStrategies(List<String> p_Strategies) {
    }


    /**
     * @param p_NumOfTurns number of turns to be given
     */
    public void tournamentMaxNumOfTurns(String p_NumOfTurns) {
    }


    /**
     * @param p_NumOfGames number of games to be played
     */
    public void tournamentNumOfGames(String p_NumOfGames) {
    }
}
