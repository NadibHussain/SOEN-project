package team14.warzone.GameEngine;

import team14.warzone.Console.Console;
import team14.warzone.Console.InputValidator;
import team14.warzone.GameEngine.Commands.AdminCommands;
import team14.warzone.GameEngine.Commands.Order;
import team14.warzone.GameEngine.Observer.LogEntryBuffer;
import team14.warzone.GameEngine.Observer.LogerOberver;
import team14.warzone.GameEngine.State.*;
import team14.warzone.MapModule.Country;
import team14.warzone.MapModule.Map;
import team14.warzone.MapModule.MapEditor;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * This class implements the functionalities related to gameplay
 *
 * @author Anagh
 * @author Zeina
 * @version 1.0
 */
public class GameEngine {
    /**
     * field stores the current player who's turn is ongoing
     */
    private Player d_CurrentPlayer;
    /**
     * the loaded map
     */
    private Map d_LoadedMap;
    /**
     * list of players active in the game
     */
    private ArrayList<Player> d_PlayerList;
    /**
     * Neutral player
     */
    private NeutralPlayer d_NeutralPlayer;

    /**
     * intance of console
     */
    private Console d_Console;
    /**
     * instance of map editor
     */
    private MapEditor d_MapEditor;

    /**
     * Stores admin commands that are yet to be executed
     */
    private ArrayList<AdminCommands> d_AdminCommandsBuffer;
    /**
     * Stores two-part orders.
     * Some order have effects that need to be reset at the end of the round. They are temporarily stored here.
     */
    private ArrayList<Order> d_OrderBuffer;
    /**
     * Stores user inputs needed to create orders
     */
    private List<List<String>> d_OrderStrBuffer;

    /**
     * This is used to send logs to the observer
     */
    private LogEntryBuffer d_LogEntryBuffer = new LogEntryBuffer();

    // State pattern attributes
    /**
     * Current phase of the game engine
     */
    private Phase d_CurrentPhase;

    /**
     * Phase before a map is loaded for editing
     */
    private Phase d_PreMapLoadPhase;
    /**
     * Phase after a map is loaded for editing
     */
    private Phase d_PostMapEditLoadPhase;
    /**
     * Startup phase attribute
     */
    private Phase d_StartupPhase;
    /**
     * Phase for issuing orders
     */
    private Phase d_IssueOrdersPhase;
    /**
     * Phase for executing orders
     */
    private Phase d_ExecuteOrdersPhase;
    /**
     * Phase when game ends
     */
    private Phase d_GameOverPhase;

    /**
     * Constructor for Game Engine
     *
     * @param p_Console   console object
     * @param p_MapEditor map editor object
     */
    public GameEngine(Console p_Console, MapEditor p_MapEditor) {
        d_Console = p_Console;
        d_MapEditor = p_MapEditor;
        d_PlayerList = new ArrayList<Player>();
        d_NeutralPlayer = new NeutralPlayer();
        d_AdminCommandsBuffer = new ArrayList<>();
        d_OrderBuffer = new ArrayList<>();

        d_PreMapLoadPhase = new PreMapLoadPhase(this);
        d_PostMapEditLoadPhase = new PostMapEditLoadPhase(this);
        d_StartupPhase = new StartupPhase(this);
        d_IssueOrdersPhase = new IssueOrdersPhase(this);
        d_ExecuteOrdersPhase = new ExecuteOrdersPhase(this);
        d_GameOverPhase = new GameOverPhase(this);

        d_CurrentPhase = d_PreMapLoadPhase;
        d_LogEntryBuffer.attach(new LogerOberver());
    }

    /**
     * Copy Constructor
     *
     * @param p_GameEngine
     */
    public GameEngine(GameEngine p_GameEngine) {
        d_Console = p_GameEngine.d_Console;
        d_MapEditor = p_GameEngine.d_MapEditor;
        d_PlayerList = p_GameEngine.d_PlayerList;
        d_NeutralPlayer = p_GameEngine.d_NeutralPlayer;
        d_AdminCommandsBuffer = p_GameEngine.d_AdminCommandsBuffer;
        d_OrderBuffer = p_GameEngine.d_OrderBuffer;

        d_PreMapLoadPhase = p_GameEngine.d_PreMapLoadPhase;
        d_PostMapEditLoadPhase = p_GameEngine.d_PostMapEditLoadPhase;
        d_StartupPhase = p_GameEngine.d_StartupPhase;
        d_IssueOrdersPhase = p_GameEngine.d_IssueOrdersPhase;
        d_ExecuteOrdersPhase = p_GameEngine.d_ExecuteOrdersPhase;
        d_GameOverPhase = p_GameEngine.d_GameOverPhase;
        d_CurrentPhase = p_GameEngine.d_CurrentPhase;

        d_LoadedMap = p_GameEngine.d_LoadedMap;
    }

    /**
     * Assign Countries method
     */
    public void assignCountries() {
        ArrayList<Country> l_Countries = d_LoadedMap.getD_Countries();
        //if number of players between 2 and 5, assign countries to players randomly
        if (d_PlayerList.size() >= 2 && d_PlayerList.size() <= 5) {
            int l_CountryCounter = 0;
            while (l_CountryCounter < l_Countries.size()) {
                for (int l_PlayerIterator = 0; l_PlayerIterator < d_PlayerList.size() && l_CountryCounter < l_Countries.size(); l_PlayerIterator++) {
                    // add country to player's country-list
                    d_PlayerList.get(l_PlayerIterator).addCountryOwned(l_Countries.get(l_CountryCounter));
                    // set country's current owner to player
                    l_Countries.get(l_CountryCounter).setD_CurrentOwner(d_PlayerList.get(l_PlayerIterator).getD_Name());
                    l_CountryCounter++;
                }
            }
            Console.displayMsg("Success: countries assigned");
            // change phase to game play
            InputValidator.CURRENT_PHASE = InputValidator.Phase.GAMEPLAY;
        } else {
            Console.displayMsg("Failed: 2-5 players required");
        }
    }

    /**
     * Method adds players to the player list
     *
     * @param p_PlayerName String name of the player
     */
    public void addPlayer(String p_PlayerName) {
        if (d_PlayerList.size() == 5)
            Console.displayMsg("You can not addd more than 5 players");
        else if (d_PlayerList.stream().anyMatch(o -> o.getD_Name().equals(p_PlayerName)))
            Console.displayMsg("Player already exists!");
        else {
            Player l_LocalPlayer = new Player(p_PlayerName, this);
            d_PlayerList.add(l_LocalPlayer);
            Console.displayMsg("Player added: " + p_PlayerName);
        }
    }

    /**
     * Method remove a player from the player list
     *
     * @param p_PlayerName String name of the player
     */
    public void removePlayer(String p_PlayerName) {
        if (d_PlayerList.isEmpty())
            Console.displayMsg("You can not remove a player, player list is empty!");
        else if (!d_PlayerList.stream().anyMatch(o -> o.getD_Name().equals(p_PlayerName))) {
            Console.displayMsg("Player " + p_PlayerName + " does not exist!");
        } else {
            Player l_PlayerToRemove = new Player();
            for (Player l_Player : d_PlayerList) {
                if (l_Player.getD_Name().equals(p_PlayerName))
                    l_PlayerToRemove = l_Player;
            }
            d_PlayerList.remove(l_PlayerToRemove);
            Console.displayMsg("Player removed: " + p_PlayerName);
        }
    }

    /**
     * A method loops and continually invokes the run method in each phase
     */
    public void gameLoop() {
        do {
            d_CurrentPhase.run();
        } while (!d_CurrentPhase.equals(d_GameOverPhase));
    }

    public void appendToCommandBuffer(AdminCommands p_AdminCommands) {
        d_AdminCommandsBuffer.add(p_AdminCommands);
    }

    public void clearCommandBuffer() {
        d_AdminCommandsBuffer.clear();
    }

    /**
     * Set console
     *
     * @param p_Console console parameter
     */
    public void setD_Console(Console p_Console) {
        d_Console = p_Console;
    }

    /**
     * Get loaded map
     *
     * @return d_LoadedMap loaded map is returned
     */
    public Map getD_LoadedMap() {
        return d_LoadedMap;
    }

    /**
     * Getter for Map editor field
     * @return Object of type Map Editor
     */
    public MapEditor getD_MapEditor() {
        return d_MapEditor;
    }

    /**
     * Get player list
     *
     * @return d_PlayerList Player list is returned
     */
    public ArrayList<Player> getD_PlayerList() {
        return d_PlayerList;
    }

    /**
     * Get a player by name
     *
     * @param p_PlayerName player list parameter
     * @return Player object that has a match name
     */
    public Player findPlayer(String p_PlayerName) {
        for (Player l_Player : d_PlayerList) {
            if (l_Player.getD_Name().equals(p_PlayerName))
                return l_Player;
        }
        if (d_NeutralPlayer.getD_Name().equals(p_PlayerName))
            return d_NeutralPlayer;
        return null;
    }

    /**
     * Setter for player list
     * @param p_PlayerList player list parameter
     */
    public void setD_PlayerList(ArrayList<Player> p_PlayerList) {
        d_PlayerList = p_PlayerList;
    }

    /**
     * Setter for current player
     *
     * @param p_CurrentPlayer current player parameter
     */
    public void setD_CurrentPlayer(Player p_CurrentPlayer) {
        d_CurrentPlayer = p_CurrentPlayer;
    }

    /**
     * Getter for current phase
     * @return current phase object
     */
    public Phase getD_CurrentPhase() {
        return d_CurrentPhase;
    }

    /**
     * Getter
     * @return phase
     */
    public Phase getD_PreMapLoadPhase() {
        return d_PreMapLoadPhase;
    }

    /**
     * Getter
     * @return phase object
     */
    public Phase getD_PostMapEditLoadPhase() {
        return d_PostMapEditLoadPhase;
    }

    /**
     * Setter
     * @param p_CurrentPhase phase object
     */
    public void setD_CurrentPhase(Phase p_CurrentPhase) {
        d_CurrentPhase = p_CurrentPhase;
    }

    /**
     * Getter
     * @return phase object
     */
    public Phase getD_StartupPhase() {
        return d_StartupPhase;
    }

    /**
     * Getter
     * @return phase object
     */
    public Phase getD_IssueOrdersPhase() {
        return d_IssueOrdersPhase;
    }

    /**
     *
     * @return
     */
    public Phase getD_ExecuteOrdersPhase() {
        return d_ExecuteOrdersPhase;
    }

    /**
     * Getter
     * @return returns unexecuted commands stored in buffer
     */
    public ArrayList<AdminCommands> getD_CommandBuffer() {
        return d_AdminCommandsBuffer;
    }

    public void setD_CommandBuffer(ArrayList<AdminCommands> p_AdminCommandsBuffer) {
        d_AdminCommandsBuffer = p_AdminCommandsBuffer;
    }

    public Player getD_CurrentPlayer() {
        return d_CurrentPlayer;
    }

    public void setD_LoadedMap(Map d_LoadedMap) {
        this.d_LoadedMap = d_LoadedMap;
    }

    public void allotCard(Player p_player) {
        Card l_Card = new Card();
        Random l_RandomNumber = new Random();
        l_Card.setD_CardType(l_Card.TYPES[l_RandomNumber.nextInt(l_Card.TYPES.length)]);
        p_player.addCard(l_Card);
        Console.displayMsg("Player " + p_player.getD_Name() + " has received " + l_Card.getD_CardType() + " card!");
    }

    public List<List<String>> getD_OrderStrBuffer() {
        return d_OrderStrBuffer;
    }

    public void setD_OrderStrBuffer(List<List<String>> p_OrderStrBuffer) {
        d_OrderStrBuffer = p_OrderStrBuffer;
    }

    public void clearOrderStrBuffer() {
        d_OrderStrBuffer.clear();
    }

    public NeutralPlayer getD_NeutralPlayer() {
        return d_NeutralPlayer;
    }

    public ArrayList<Order> getD_OrderBuffer() {
        return d_OrderBuffer;
    }

    public void appendToOrderBuffer(Order p_Order) {
        d_OrderBuffer.add(p_Order);
    }

    public void resetOrderBuffer() {
        for (Order l_Order : d_OrderBuffer) {
            l_Order.reset();
        }
        d_OrderBuffer.clear();
    }

    public Phase getD_GameOverPhase() {
        return d_GameOverPhase;
    }

    /**
     * Getter for LogEntryBuffer
     *
     * @return the current log entry buffer for this engine
     */
    public LogEntryBuffer getD_LogEntryBuffer() {
        return d_LogEntryBuffer;
    }
}
