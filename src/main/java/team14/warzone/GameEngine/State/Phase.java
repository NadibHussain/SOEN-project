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
     * @param showMap(
     */
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
     * @param p_CountryId
     * @param p_ContinentId
     * @param p_CountryId
     * @param p_CountryId
     * @param p_Name
     * @param p_Maps
     */
    /**
     * Adds country to the loaded-map in the game engine
     *
     * @param p_CountryId   Country ID which the needs is to be added
     * @param p_ContinentId Name of the Continent country to be added
     */
    abstract public void addCountry(String p_CountryId, String p_ContinentId);

    
    /** 
     * @param p_ContinentId
     * @param p_CountryId
     * @param p_CountryId
     * @param p_Name
     * @param p_Maps
     */
    /**
     * removes country to the loaded-map in the game engine
     *
     * @param p_CountryId Country ID which the needs is to be removed
     */
    abstract public void removeCountry(String p_CountryId);

    
    /** 
     * @param p_ContinentId
     * @param p_CountryId
     * @param p_CountryId
     * @param p_Name
     * @param p_Maps
     */
    /**
     * Adds Continent to the loaded-map in the game engine
     *
     * @param p_ContinentId  Continent ID which the needs is to be added
     * @param p_ControlValue amount of extra re-enforcement a player will get if concurred
     */
    abstract public void addContinent(String p_ContinentId, int p_ControlValue);

    
    /** 
     * @param p_CountryId
     * @param p_CountryId
     * @param p_Name
     * @param p_Maps
     */
    /**
     * Removes Continent to the loaded-map in the game engine
     *
     * @param p_ContinentId Continent ID which the needs is to be removed
     */
    abstract public void removeContinent(String p_ContinentId);

    
    /** 
     * @param p_CountryId
     * @param p_CountryId
     * @param p_Name
     * @param p_Maps
     */
    /**
     * Makes 2 country neighbour of each other
     *
     * @param p_CountryId  ID of the first country
     * @param p_NeighborId ID of the other country
     */
    abstract public void addNeighbor(String p_CountryId, String p_NeighborId);

    
    /** 
     * @param p_CountryId
     * @param p_Name
     * @param p_Maps
     */
    /**
     * Makes 2 neighbour country not neighbour anymore
     *
     * @param p_CountryId  Country from which the neighbour is to be removed
     * @param p_NeighborId Name of the neighbour to be removed
     */
    abstract public void removeNeighbor(String p_CountryId, String p_NeighborId);

    
    /** 
     * @param p_Name
     * @param p_Maps
     */
    /**
     * load .map file as Map object in the game engine
     *
     * @param p_FileName Path of the .map file
     */
    abstract public void loadMap(String p_FileName);

    
    /** 
     * @param p_Name
     * @param p_Maps
     */
    /**
     * save Map object as .map file
     *
     * @param p_FileName Path of the .map file
     */
    abstract public void saveMap(String p_FileName);

    
    /** 
     * @param p_Name
     * @param p_Maps
     */
    /**
     * Needed command before editing the Map object
     *
     * @param p_FileName Path of the .map file
     */
    abstract public void editMap(String p_FileName);

    
    /** 
     * @param p_Name
     * @param p_Maps
     */
    /**
     * Validate the map
     *
     * @param p_Map Map object that we want to validate
     */
    abstract public void validateMap(Map p_Map);

    
    /** 
     * @param p_Name
     * @param p_Maps
     */
    // gameplay phase: startup, reinforce, issue, execute
    // startup state

    /**
     * Adding players in the game
     *
     * @param p_Name Name of the player
     * @param p_PlayerType Behavior type of player
     */
    abstract public void addPlayer(String p_Name, String p_PlayerType);

    
    /** 
     * @param p_Maps
     */
    /**
     * Removing players in the game
     *
     * @param p_Name Name of the player
     */
    abstract public void removePlayer(String p_Name);

    
    /** 
     * @param p_Maps
     */
    /**
     * Assign and divide all countries among all players
     */
    abstract public void assignCountries();

    
    /** 
     * @param p_Maps
     */
    // reinforce phase

    /**
     * Used in reinforcement phase
     */
    abstract public void reinforce();

    
    /** 
     * @param p_Maps
     */
    abstract public void saveGame(String p_FileName);

    
    /** 
     * @param p_Maps
     */
    abstract public void loadGame(String p_FileName);

    
    /** 
     * @param p_Maps
     */
    // issue order phase


    /**
     * takes command in round robin manner in the play phase
     */
    abstract public void issueCommands(); // instant for map phase and round robin for play phase

    
    /** 
     * @param p_Maps
     */
    /**
     * display the list of cards in current players possession
     */
    abstract public void showCards(); // instant for map phase and round robin for play phase



    
    /** 
     * @param p_Maps
     */
    // execute order phase

    /**
     * execute each command that is in the list and take appropriate action
     */
    abstract public void executeCommands(); // instant for map phase and round robin for play phase

    
    /** 
     * @param p_Maps
     */
    /**
     * place some armies on one of the current players territories.
     */
    abstract public void deploy();

    
    /** 
     * @param p_Maps
     */
    /**
     * move some armies from one of the current players territories (source) to an adjacent territory
     * (target).
     */
    abstract public void advance();

    
    /** 
     * @param p_Maps
     */
    /**
     * destroy half of the armies located on an opponents territory that is adjacent to one of the current
     * players territories
     */
    abstract public void bomb();

    
    /** 
     * @param p_Maps
     */
    /**
     * triple the number of armies on one of the current players territories and make it a neutral territory
     */
    abstract public void blockade();

    
    /** 
     * @param p_Maps
     */
    /**
     * advance some armies from one of the current players territories to any another territory
     */
    abstract public void airlift();

    
    /** 
     * @param p_Maps
     */
    /**
     * prevent attacks between the current player and another player until the end of the turn.
     */
    abstract public void diplomacy();

    
    /** 
     * @param p_Maps
     */
    public void tournamentAddMaps(List<String> p_Maps) {
    }

    
    /** 
     * @param p_Strategies
     */
    public void tournamentAddPlayersStrategies(List<String> p_Strategies) {
    }

    
    /** 
     * @param p_NumOfTurns
     */
    public void tournamentMaxNumOfTurns(String p_NumOfTurns) {
    }

    
    /** 
     * @param p_NumOfGames
     */
    public void tournamentNumOfGames(String p_NumOfGames) {
    }
}
