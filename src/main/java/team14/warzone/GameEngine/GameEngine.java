package team14.warzone.GameEngine;

import team14.warzone.Console.Console;
import team14.warzone.GameEngine.Commands.AdminCommands;
import team14.warzone.GameEngine.Commands.Order;
import team14.warzone.GameEngine.Observer.LogEntryBuffer;
import team14.warzone.GameEngine.Observer.LogerOberver;
import team14.warzone.GameEngine.State.*;
import team14.warzone.MapModule.Map;
import team14.warzone.MapModule.MapEditor;

import java.io.*;
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
<<<<<<< HEAD
    /**
     * Flag to check whether automated player passed their turn
     */
    private boolean d_PlayerPassed;
=======
    private GameSaveLoad d_GameSaveLoad;
>>>>>>> be9fc428ae0f29d8c527c943c8d533587fa25f71

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
        d_GameSaveLoad = new GameSaveLoad(this);
        d_PreMapLoadPhase = new PreMapLoadPhase(this);
        d_PostMapEditLoadPhase = new PostMapEditLoadPhase(this);
        d_StartupPhase = new StartupPhase(this);
        d_IssueOrdersPhase = new IssueOrdersPhase(this);
        d_ExecuteOrdersPhase = new ExecuteOrdersPhase(this);
        d_GameOverPhase = new GameOverPhase(this);

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
        d_CurrentPhase = p_GameEngine.d_CurrentPhase;

        d_LoadedMap = p_GameEngine.d_LoadedMap;
    }

    /**
     * A method loops and continually invokes the run method in each phase
     */
    public void gameLoop() {
        while (true) {
            d_CurrentPhase.run();
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
     * Getter for LogEntryBuffer
     *
     * @return the current log entry buffer for this engine
     */
    public LogEntryBuffer getD_LogEntryBuffer() {
        return d_LogEntryBuffer;
    }

<<<<<<< HEAD
    public boolean getD_PlayerPassed() {
        return d_PlayerPassed;
    }

    public void setD_PlayerPassed(boolean p_PlayerPassed) {
        d_PlayerPassed = p_PlayerPassed;
    }

    public void resetPlayerPassed() {
        d_PlayerPassed = false;
    }
=======
    public GameSaveLoad getD_GameSaveLoad() {
        return d_GameSaveLoad;
    }
    /**
    public static void saveGame(String p_FileName, GameEngine p_GE) {
        File d_GameFile = new File(p_FileName);
        GameEngine d_GE = p_GE;
        try {
            FileWriter d_GameFileWriter = new FileWriter(d_GameFile);
            FileOutputStream d_FileOut = new FileOutputStream(d_GameFile);
            ObjectOutputStream d_ObjectOut = new ObjectOutputStream(d_FileOut);
            d_ObjectOut.writeObject(d_GE.getD_LoadedMap().getD_Continents());
            d_ObjectOut.writeObject(d_GE.getD_LoadedMap().getD_Countries());
            d_ObjectOut.writeObject(d_GE.getD_PlayerList());
            d_ObjectOut.writeObject(d_GE.getD_CurrentPlayer());
            d_ObjectOut.writeObject(d_GE.getD_CurrentPhase());
            d_ObjectOut.close();
            d_ObjectOut.flush();
            System.out.println("Game Saved Successfully as" + p_FileName);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
     */
>>>>>>> be9fc428ae0f29d8c527c943c8d533587fa25f71
}
