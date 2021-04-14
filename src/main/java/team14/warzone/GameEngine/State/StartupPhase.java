package team14.warzone.GameEngine.State;

import team14.warzone.Console.Console;
import team14.warzone.Console.InputValidator;
import team14.warzone.GameEngine.Commands.AdminCommands;
import team14.warzone.GameEngine.GameEngine;
import team14.warzone.GameEngine.Player;
import team14.warzone.MapModule.Country;
import team14.warzone.MapModule.Map;

import java.util.ArrayList;
import java.util.List;

/**
 * Class defines the start up phase of the game
 * Is started when a valid map is loaded
 */
public class StartupPhase extends GamePlayPhase {
    /**
     * StartupPhase
     *
     * @param p_GameEngine GE
     */
    public StartupPhase(GameEngine p_GameEngine) {
        super(p_GameEngine);
    }

    @Override
    public void run() {
        if (d_GameEngine.getD_LoadedMap() == null) {
            System.out.println("Please load map first before starting Startup phase");
            d_GameEngine.setD_CurrentPhase(d_GameEngine.getD_PreMapLoadPhase());
        }
        issueCommands();
        executeCommands();
    }

    /**
     * Issue a command
     */
    @Override
    public void issueCommands() {
        System.out.println("Enter command:");
        List<List<String>> l_CommandStrList = Console.readInput();
        createAdminCommand(l_CommandStrList);
    }

    /**
     * Executes command
     */
    @Override
    public void executeCommands() {
        for (AdminCommands l_AdminCommands : d_GameEngine.getD_CommandBuffer()) {
            l_AdminCommands.execute();
        }
        d_GameEngine.clearCommandBuffer();
    }

    /**
     * Methods adds new player to playerlist
     *
     * @param p_Name Name of the player
     */
    @Override
    public void addPlayer(String p_Name, String p_PlayerType) {
        if (d_GameEngine.getD_PlayerList().size() == 5)
            Console.displayMsg("You can not addd more than 5 players");
        else if (d_GameEngine.getD_PlayerList().stream().anyMatch(o -> o.getD_Name().equals(p_Name)))
            Console.displayMsg("Player already exists!");
        else {
            Player l_LocalPlayer = new Player(p_Name, p_PlayerType, d_GameEngine);
            d_GameEngine.getD_PlayerList().add(l_LocalPlayer);
            Console.displayMsg("Player added: " + p_Name);
        }

        d_GameEngine.getD_LogEntryBuffer().setD_log("Added player:" + p_Name);
        d_GameEngine.getD_LogEntryBuffer().notifyObservers(d_GameEngine.getD_LogEntryBuffer());
    }

    /**
     * Method remove a player from the player list
     *
     * @param p_Name String name of the player
     */
    @Override
    public void removePlayer(String p_Name) {
        if (d_GameEngine.getD_PlayerList().isEmpty())
            Console.displayMsg("You can not remove a player, player list is empty!");
        else if (!d_GameEngine.getD_PlayerList().stream().anyMatch(o -> o.getD_Name().equals(p_Name))) {
            Console.displayMsg("Player " + p_Name + " does not exist!");
        } else {
            Player l_PlayerToRemove = new Player();
            for (Player l_Player : d_GameEngine.getD_PlayerList()) {
                if (l_Player.getD_Name().equals(p_Name))
                    l_PlayerToRemove = l_Player;
            }
            d_GameEngine.getD_PlayerList().remove(l_PlayerToRemove);
            Console.displayMsg("Player removed: " + p_Name);
        }

        d_GameEngine.getD_LogEntryBuffer().setD_log("Removed player:" + p_Name);
        d_GameEngine.getD_LogEntryBuffer().notifyObservers(d_GameEngine.getD_LogEntryBuffer());
    }

    /**
     * Method assigns all countries randomly between the players
     */
    @Override
    public void assignCountries() {
        d_GameEngine.getD_LogEntryBuffer().setD_log("Assigned Countries to players");
        d_GameEngine.getD_LogEntryBuffer().notifyObservers(d_GameEngine.getD_LogEntryBuffer());

        ArrayList<Country> l_Countries = d_GameEngine.getD_LoadedMap().getD_Countries();
        ArrayList<Player> l_PlayerList = d_GameEngine.getD_PlayerList();
        //if number of players between 2 and 5, assign countries to players randomly
        if (l_PlayerList.size() >= 2 && l_PlayerList.size() <= 5) {
            int l_CountryCounter = 0;
            while (l_CountryCounter < l_Countries.size()) {
                for (int l_PlayerIterator = 0; l_PlayerIterator < l_PlayerList.size() && l_CountryCounter < l_Countries.size(); l_PlayerIterator++) {
                    // add country to player's country-list
                    l_PlayerList.get(l_PlayerIterator).addCountryOwned(l_Countries.get(l_CountryCounter));
                    // set country's current owner to player
                    l_Countries.get(l_CountryCounter).setD_CurrentOwner(l_PlayerList.get(l_PlayerIterator).getD_Name());
                    l_CountryCounter++;
                }
            }
            Console.displayMsg("Success: countries assigned");
            // change phase to game play
            InputValidator.CURRENT_PHASE = InputValidator.Phase.GAMEPLAY;
        } else {
            Console.displayMsg("Failed: 2-5 players required");
        }

        next();
        d_GameEngine.getD_LogEntryBuffer().setD_log("Changing Phase from Startup Phase to IssueOrder Phase");
        d_GameEngine.getD_LogEntryBuffer().notifyObservers(d_GameEngine.getD_LogEntryBuffer());
    }

    /**
     * Reinforcement beginning of game play
     */
    @Override
    public void reinforce() {
        invalidCommandMessage();
    }

    /**
     * Add to country list
     *
     * @param p_CountryId Country ID to add
     * @param p_ContinentId Continent ID
     */
    @Override
    public void addCountry(String p_CountryId, String p_ContinentId) {
        invalidCommandMessage();
    }


    /**
     * Remove from country list
     *
     * @param p_CountryId Country ID to remove
     */
    @Override
    public void removeCountry(String p_CountryId) {
        invalidCommandMessage();
    }


    /**
     * Add to continent list
     *
     * @param p_ContinentId Continent ID to add
     * @param p_ControlValue Control value
     */
    @Override
    public void addContinent(String p_ContinentId, int p_ControlValue) {
        invalidCommandMessage();
    }


    /**
     * REmove from Continent list
     *
     * @param p_ContinentId Continent ID to remove
     */
    @Override
    public void removeContinent(String p_ContinentId) {
        invalidCommandMessage();
    }


    /**
     * Add neighbor
     *
     * @param p_CountryId Country ID
     * @param p_NeighborId Neighbour ID to add
     */
    @Override
    public void addNeighbor(String p_CountryId, String p_NeighborId) {
        invalidCommandMessage();
    }


    /**
     * Remove neighbor
     *
     * @param p_CountryId Country ID
     * @param p_NeighborId Neighbour ID to remove
     */
    @Override
    public void removeNeighbor(String p_CountryId, String p_NeighborId) {
        invalidCommandMessage();
    }


    /**
     * Loads map
     *
     * @param p_FileName File name to load map from
     */
    @Override
    public void loadMap(String p_FileName) {
        invalidCommandMessage();
    }


    /**
     * Saves map
     *
     * @param p_FileName file name to save map
     */
    @Override
    public void saveMap(String p_FileName, String p_MapType) {
        invalidCommandMessage();
    }


    /**
     * Edit map
     *
     * @param p_FileName file name to edit a map
     */
    @Override
    public void editMap(String p_FileName) {
        invalidCommandMessage();
    }


    /**
     * Validates a map
     *
     * @param p_Map map to be validated
     */
    @Override
    public void validateMap(Map p_Map) {
        invalidCommandMessage();
    }

    /**
     * Deploy command
     */
    @Override
    public void deploy() {
        invalidCommandMessage();
    }

    /**
     * Advance order
     */
    @Override
    public void advance() {
        invalidCommandMessage();
    }

    /**
     * Bomb order
     */
    @Override
    public void bomb() {
        invalidCommandMessage();
    }

    /**
     * Blockade order
     */
    @Override
    public void blockade() {

    }

    /**
     * Airlift order
     */
    @Override
    public void airlift() {
        invalidCommandMessage();
    }

    /**
     * Diplomacy order
     */
    @Override
    public void diplomacy() {
        invalidCommandMessage();
    }

}
