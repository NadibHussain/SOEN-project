package team14.warzone.GameEngine.State;

import team14.warzone.GameEngine.GameEngine;
import team14.warzone.MapModule.Map;

public abstract class Phase {

    GameEngine d_GameEngine;

    public Phase(GameEngine p_GameEngine) {
        d_GameEngine = p_GameEngine;
    }

    // all phases
    abstract public void run();
    public void showMap() {
        if (d_GameEngine.getD_LoadedMap() == null && d_GameEngine.getD_MapEditor().getD_LoadedMap() == null)
            System.out.println("Please load a map first!");
        else if (d_GameEngine.getD_LoadedMap() != null)
            d_GameEngine.getD_LoadedMap().showMap();
        else
            d_GameEngine.getD_MapEditor().getD_LoadedMap().showMap();
    }

    public void invalidCommandMessage() {
        System.out.println("Error: command invalid for " + this.getClass().getSimpleName() + " phase");
    }

    public void next() {
        if (d_GameEngine.getD_CurrentPhase() instanceof MapEditorPhase)
            d_GameEngine.setD_CurrentPhase(d_GameEngine.getD_StartupPhase());
        else if (d_GameEngine.getD_CurrentPhase() instanceof StartupPhase)
            d_GameEngine.setD_CurrentPhase(d_GameEngine.getD_IssueOrdersPhase());
        else if (d_GameEngine.getD_CurrentPhase() instanceof IssueOrdersPhase)
            d_GameEngine.setD_CurrentPhase(d_GameEngine.getD_ExecuteOrdersPhase());
        else if (d_GameEngine.getD_CurrentPhase() instanceof ExecuteOrdersPhase)
            d_GameEngine.setD_CurrentPhase(d_GameEngine.getD_IssueOrdersPhase());
    }
    public void endGame() {}

    // mapeditor phase
    abstract public void addCountry(String p_CountryId, String p_ContinentId);
    abstract public void removeCountry(String p_CountryId);
    abstract public void addContinent(String p_ContinentId, int p_ControlValue);
    abstract public void removeContinent(String p_ContinentId);
    abstract public void addNeighbor(String p_CountryId, String p_NeighborId);
    abstract public void removeNeighbor(String p_CountryId, String p_NeighborId);
    abstract public void loadMap(String p_FileName);
    abstract public void saveMap(String p_FileName);
    abstract public void editMap(String p_FileName);
    abstract public void validateMap(Map p_Map);

    // gameplay phase: startup, reinforce, issue, execute
    // startup state
    abstract public void addPlayer(String p_Name);
    abstract public void removePlayer(String p_Name);
    abstract public void assignCountries();

    // reinforce phase
    abstract public void reinforce();

    // issue order phase
    abstract public void issueCommands(); // instant for map phase and round robin for play phase
    // execute order phase
    abstract public void executeCommands(); // instant for map phase and round robin for play phase
    abstract public void deploy();
    abstract public void advance();
    abstract public void bomb();
    abstract public void blockade();
    abstract public void airlift();
    abstract public void diplomacy();
}
