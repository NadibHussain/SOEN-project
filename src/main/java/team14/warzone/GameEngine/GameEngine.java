package team14.warzone.GameEngine;

import team14.warzone.Console.Console;
import team14.warzone.GameEngine.Commands.AdminCommands;
import team14.warzone.GameEngine.Commands.Order;
import team14.warzone.GameEngine.Observer.LogEntryBuffer;
import team14.warzone.GameEngine.Observer.LogerOberver;
import team14.warzone.GameEngine.State.*;
import team14.warzone.MapModule.AdapterMapEditor;
import team14.warzone.MapModule.Map;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Random;

/**
 * This class implements the functionalities related to gameplay
 *
 * @author Anagh
 * @author Zeina
 * @version 1.0
 */
public class GameEngine implements Serializable {
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
    private AdapterMapEditor d_MapEditor;

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
     * Tournament mode phase
     */
    private Phase d_TournamentModePhase;
    /**
     * Flag to check whether automated player passed their turn
     */
    private boolean d_PlayerPassed;
    /**
     * GameSaveLoad object to access GameEngine
     */
    private GameSaveLoad d_GameSaveLoad;
    /**
     * Tournament mode current number of turns
     */
    private int d_CurrentNumberOfTurns;
    /**
     * Check if game is over, used in tournament mode
     */
    private boolean d_GameOver;
    /**
     * Check if it is tournament mode
     */
    private boolean d_TournamentMode;
    /**
     * Check if tournament ended
     */
    private boolean d_TournamentEnded;

    /**
     * Constructor for Game Engine
     *
     * @param p_Console   console object
     * @param p_MapEditor map editor object
     */
    public GameEngine(Console p_Console, AdapterMapEditor p_MapEditor) {
        d_Console = p_Console;
        d_MapEditor = p_MapEditor;
        d_PlayerList = new ArrayList<Player>();
        d_NeutralPlayer = new NeutralPlayer();
        d_AdminCommandsBuffer = new ArrayList<>();
        d_OrderBuffer = new ArrayList<>();
        d_GameSaveLoad = new GameSaveLoad(this);
        d_PreMapLoadPhase = new PreMapLoadPhase(this);
        d_PostMapEditLoadPhase = new PostMapEditLoadPhase(this);
        d_StartupPhase = new StartupPhase(this);
        d_IssueOrdersPhase = new IssueOrdersPhase(this);
        d_ExecuteOrdersPhase = new ExecuteOrdersPhase(this);
        d_GameOverPhase = new GameOverPhase(this);
        d_TournamentModePhase = new Tournament(this);
        d_CurrentPlayer = new Player("temp", this);
        d_CurrentNumberOfTurns = 0;
        d_TournamentEnded = false;
        d_TournamentMode = false;
        d_GameOver = false;

        d_CurrentPhase = d_PreMapLoadPhase;
        d_LogEntryBuffer.attach(new LogerOberver());
        d_PlayerPassed = false;
    }

    /**
     * Copy Constructor
     *
     * @param p_GameEngine gameengine parameter
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
        d_TournamentModePhase = p_GameEngine.d_TournamentModePhase;
        d_CurrentPhase = p_GameEngine.d_CurrentPhase;

        d_LoadedMap = p_GameEngine.d_LoadedMap;
    }

    /**
     * A method loops and continually invokes the run method in each phase
     */
    public void gameLoop() {
        while (!d_TournamentEnded) {
            d_CurrentPhase.run();
        }
    }

    /**
     * Reset all players
     */
    public void resetPlayers() {
        for (Player l_Player : d_PlayerList) {
            l_Player.resetPlayer();
        }
    }

    /**
     * Load game. Replaces current game engine with game engine loaded from file
     *
     * @param p_FileName Name of file to load
     */
    public void loadGame(String p_FileName) {
        GameEngine l_GE = d_GameSaveLoad.runLoadGame(p_FileName);
        if (Objects.nonNull(l_GE)) {
            this.d_LoadedMap = l_GE.getD_LoadedMap();
            this.d_CurrentPlayer = l_GE.getD_CurrentPlayer();
            this.d_PlayerList = l_GE.getD_PlayerList();
            this.d_Console = l_GE.getD_Console();
            this.d_MapEditor = l_GE.getD_MapEditor();
            this.d_AdminCommandsBuffer = l_GE.getD_AdminCommandsBuffer();
            this.d_OrderBuffer = l_GE.getD_OrderBuffer();
            this.d_OrderStrBuffer = l_GE.getD_OrderStrBuffer();
            this.d_LogEntryBuffer = l_GE.getD_LogEntryBuffer();
            this.d_CurrentPhase = l_GE.getD_CurrentPhase();
            this.d_PreMapLoadPhase = l_GE.getD_PreMapLoadPhase();
            this.d_PostMapEditLoadPhase = l_GE.getD_PostMapEditLoadPhase();
            this.d_StartupPhase = l_GE.getD_StartupPhase();
            this.d_IssueOrdersPhase = l_GE.getD_IssueOrdersPhase();
            this.d_ExecuteOrdersPhase = l_GE.getD_ExecuteOrdersPhase();
            this.d_GameOverPhase = l_GE.getD_GameOverPhase();
            this.d_GameSaveLoad = l_GE.getD_GameSaveLoad();
        }
    }

    /**
     * appending to command buffer
     *
     * @param p_AdminCommands commands
     */
    public void appendToCommandBuffer(AdminCommands p_AdminCommands) {
        d_AdminCommandsBuffer.add(p_AdminCommands);
    }

    /**
     * method to clear buffer
     */
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
     *
     * @return Object of type Map Editor
     */
    public AdapterMapEditor getD_MapEditor() {
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
     *
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
     *
     * @return current phase object
     */
    public Phase getD_CurrentPhase() {
        return d_CurrentPhase;
    }

    /**
     * Getter
     *
     * @return phase
     */
    public Phase getD_PreMapLoadPhase() {
        return d_PreMapLoadPhase;
    }

    /**
     * Getter
     *
     * @return phase object
     */
    public Phase getD_PostMapEditLoadPhase() {
        return d_PostMapEditLoadPhase;
    }

    /**
     * Setter
     *
     * @param p_CurrentPhase phase object
     */
    public void setD_CurrentPhase(Phase p_CurrentPhase) {
        d_CurrentPhase = p_CurrentPhase;
    }

    /**
     * Getter
     *
     * @return phase object
     */
    public Phase getD_StartupPhase() {
        return d_StartupPhase;
    }

    /**
     * Getter
     *
     * @return phase object
     */
    public Phase getD_IssueOrdersPhase() {
        return d_IssueOrdersPhase;
    }

    /**
     * @return returns ExecuteOrdersPhase
     */
    public Phase getD_ExecuteOrdersPhase() {
        return d_ExecuteOrdersPhase;
    }

    /**
     * Getter method
     *
     * @return returns unexecuted commands stored in buffer
     */
    public ArrayList<AdminCommands> getD_CommandBuffer() {
        return d_AdminCommandsBuffer;
    }

    /**
     * Setter
     *
     * @param p_AdminCommandsBuffer command buffer
     */
    public void setD_CommandBuffer(ArrayList<AdminCommands> p_AdminCommandsBuffer) {
        d_AdminCommandsBuffer = p_AdminCommandsBuffer;
    }

    /**
     * Getter
     *
     * @return current player
     */
    public Player getD_CurrentPlayer() {
        return d_CurrentPlayer;
    }

    /**
     * setter
     *
     * @param d_LoadedMap loaded map
     */
    public void setD_LoadedMap(Map d_LoadedMap) {
        this.d_LoadedMap = d_LoadedMap;
    }

    /**
     * Allot card method using random
     *
     * @param p_player current player to be given a card
     */
    public void allotCard(Player p_player) {
        Card l_Card = new Card();
        Random l_RandomNumber = new Random();
        l_Card.setD_CardType(l_Card.TYPES[l_RandomNumber.nextInt(l_Card.TYPES.length)]);
        p_player.addCard(l_Card);
        Console.displayMsg("Player " + p_player.getD_Name() + " has received " + l_Card.getD_CardType() + " card!");
    }

    /**
     * getter
     *
     * @return d_OrderStrBuffer
     */
    public List<List<String>> getD_OrderStrBuffer() {
        return d_OrderStrBuffer;
    }

    /**
     * setter
     *
     * @param p_OrderStrBuffer d_OrderStrBuffer
     */
    public void setD_OrderStrBuffer(List<List<String>> p_OrderStrBuffer) {
        d_OrderStrBuffer = p_OrderStrBuffer;
    }

    /**
     * clear buffer
     */
    public void clearOrderStrBuffer() {
        d_OrderStrBuffer.clear();
    }

    /**
     * Neutral player
     *
     * @return neutral player
     */
    public NeutralPlayer getD_NeutralPlayer() {
        return d_NeutralPlayer;
    }

    /**
     * getter
     *
     * @return d_OrderBuffer
     */
    public ArrayList<Order> getD_OrderBuffer() {
        return d_OrderBuffer;
    }

    /**
     * appending to buffer
     *
     * @param p_Order orders
     */
    public void appendToOrderBuffer(Order p_Order) {
        d_OrderBuffer.add(p_Order);
    }

    /**
     * reset the buffer
     */
    public void resetOrderBuffer() {
        for (Order l_Order : d_OrderBuffer) {
            l_Order.reset();
        }
        d_OrderBuffer.clear();
    }

    /**
     * getter
     *
     * @return d_GameOverPhase
     */
    public Phase getD_GameOverPhase() {
        return d_GameOverPhase;
    }

    /**
     * getter
     *
     * @return d_TournamentModePhase
     */
    public Phase getD_TournamentModePhase() {
        return d_TournamentModePhase;
    }

    /**
     * Getter for LogEntryBuffer
     *
     * @return the current log entry buffer for this engine
     */
    public LogEntryBuffer getD_LogEntryBuffer() {
        return d_LogEntryBuffer;
    }


    /**
     * @return boolean
     */
    public boolean getD_PlayerPassed() {
        return d_PlayerPassed;
    }


    /**
     * @param p_PlayerPassed player passed true or false
     */
    public void setD_PlayerPassed(boolean p_PlayerPassed) {
        d_PlayerPassed = p_PlayerPassed;
    }

    /**
     * Reset if player has passed flag
     */
    public void resetPlayerPassed() {
        d_PlayerPassed = false;
    }

    /**
     * Getter for GameSaveLoad function
     *
     * @return d_GameSaveLoad object
     */
    public GameSaveLoad getD_GameSaveLoad() {
        return d_GameSaveLoad;
    }

    /**
     * Getter for console
     *
     * @return Console object
     */
    public Console getD_Console() {
        return d_Console;
    }

    /**
     * Getter for admin commands buffer
     *
     * @return Array list of commands
     */
    public ArrayList<AdminCommands> getD_AdminCommandsBuffer() {
        return d_AdminCommandsBuffer;
    }

    /**
     * Increase number of turns
     */
    public void incrementD_CurrentNumberOfTurns() {
        d_CurrentNumberOfTurns++;
    }

    /**
     * Getter for current number of turns
     *
     * @return Number of turns
     */
    public int getD_CurrentNumberOfTurns() {
        return d_CurrentNumberOfTurns;
    }

    /**
     * Setter method
     *
     * @param p_CurrentNumberOfTurns Number of turns
     */
    public void setD_CurrentNumberOfTurns(int p_CurrentNumberOfTurns) {
        d_CurrentNumberOfTurns = p_CurrentNumberOfTurns;
    }

    /**
     * Setter method
     *
     * @param p_TournamentEnded true if tournament has ended
     */
    public void setD_TournamentEnded(boolean p_TournamentEnded) {
        d_TournamentEnded = p_TournamentEnded;
    }

    /**
     * Getter
     *
     * @return If tournament mode is true
     */
    public boolean isD_TournamentMode() {
        return d_TournamentMode;
    }

    /**
     * Setter
     *
     * @param p_TournamentMode Whether tournament mode is active
     */
    public void setD_TournamentMode(boolean p_TournamentMode) {
        d_TournamentMode = p_TournamentMode;
    }

    /**
     * Getter
     *
     * @return if game is over or not
     */
    public boolean isD_GameOver() {
        return d_GameOver;
    }

    /**
     * Setter
     *
     * @param p_GameOver whether game over or not
     */
    public void setD_GameOver(boolean p_GameOver) {
        d_GameOver = p_GameOver;
    }
}
