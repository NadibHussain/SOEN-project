package team14.warzone.GameEngine.State;

import team14.warzone.Console.Console;
import team14.warzone.Console.InputValidator;
import team14.warzone.GameEngine.Commands.AdminCommands;
import team14.warzone.GameEngine.GameEngine;

import java.io.FileNotFoundException;
import java.util.List;

/**
 * This is a class for MapEditorPhase
 */
public abstract class MapEditorPhase extends Phase {
    /**
     * MapEditorPhase
     * @param p_GameEngine GE
     */
    public MapEditorPhase(GameEngine p_GameEngine) {
        super(p_GameEngine);
    }

    /**
     * runs map editor phase
     */
    @Override
    public void run() {
        issueCommands();
        executeCommands();
    }

    /**
     * reinforce
     */
    @Override
    public void reinforce() {

    }

    /**
     * issues commands
     */
    @Override
    public void issueCommands() {
        // use console to prompt user to enter command
        System.out.println("Enter command:");
        List<List<String>> l_CommandStrList = Console.readInput();
        createAdminCommand(l_CommandStrList);
    }

    /**
     * execute commands
     */
    @Override
    public void executeCommands() {
        // execute the appropriate map editor command
        for (AdminCommands l_AdminCommands : d_GameEngine.getD_CommandBuffer()) {
            l_AdminCommands.execute();
        }
        d_GameEngine.clearCommandBuffer();

    }

    /**
     * loads map
     */
    @Override
    public void loadMap(String p_FileName) {
        try {
            d_GameEngine.getD_MapEditor().loadMap(p_FileName);
            d_GameEngine.setD_LoadedMap(d_GameEngine.getD_MapEditor().getD_LoadedMap());
            // validate map right after loading
            if (!d_GameEngine.getD_MapEditor().validateMap(d_GameEngine.getD_LoadedMap()))
            {
                System.out.println("Error: map validation failed, not loaded");
                d_GameEngine.getD_LogEntryBuffer().setD_log("Error: map validation failed, not loaded");
                d_GameEngine.getD_LogEntryBuffer().notifyObservers(d_GameEngine.getD_LogEntryBuffer());
            }
            else {
                System.out.println("Success: map loaded and validated");
                d_GameEngine.getD_LogEntryBuffer().setD_log("Success: map loaded and validated map:"+p_FileName);
                d_GameEngine.getD_LogEntryBuffer().notifyObservers(d_GameEngine.getD_LogEntryBuffer());
                // move to the startup phase
                d_GameEngine.setD_CurrentPhase(d_GameEngine.getD_StartupPhase());
                InputValidator.CURRENT_PHASE = InputValidator.Phase.STARTUP;
                d_GameEngine.getD_LogEntryBuffer().setD_log("Changing Phase from MapEditor Phase to Startup Phase");
                d_GameEngine.getD_LogEntryBuffer().notifyObservers(d_GameEngine.getD_LogEntryBuffer());

            }
        } catch (FileNotFoundException e) {
            System.out.println("Error: invalid filename");
        }
    }

    /**
     * adds player
     */
    @Override
    public void addPlayer(String p_Name) {
        invalidCommandMessage();
    }

    /**
     * removes player
     */
    @Override
    public void removePlayer(String p_Name) {
        invalidCommandMessage();
    }

    /**
     * assign countries
     */
    @Override
    public void assignCountries() {
        invalidCommandMessage();
    }

    /**
     * Command for showing current players cards. Invalid in this phase.
     */
    @Override
    public void showCards() {
        invalidCommandMessage();
    }

    /**
     * deploy order
     */
    @Override
    public void deploy() {
        invalidCommandMessage();
    }

    /**
     * advance order
     */
    @Override
    public void advance() {
        invalidCommandMessage();
    }

    /**
     * bomb order
     */
    @Override
    public void bomb() {
        invalidCommandMessage();
    }

    /**
     * blocakde order
     */
    @Override
    public void blockade() {
        invalidCommandMessage();
    }

    /**
     * airliftorder
     */
    @Override
    public void airlift() {
        invalidCommandMessage();
    }

    /**
     * diplomacy order
     */
    @Override
    public void diplomacy() {
        invalidCommandMessage();
    }
}
