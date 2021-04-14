package team14.warzone.GameEngine.State;

import team14.warzone.GameEngine.GameEngine;
import team14.warzone.MapModule.Map;

/**
 * This is a class for PreMapLoad phase
 */
public class PreMapLoadPhase extends MapEditorPhase {
    /**
     * PreMapLoadPhase
     * @param p_GameEngine GE
     */
    public PreMapLoadPhase(GameEngine p_GameEngine) {
        super(p_GameEngine);
    }

    /**
     * Error loadmap first
     */
    public void errorLoadMapFirst() {
        System.out.println("Error: load a map first by using <editmap> <filename>");
    }

    
    /** 
     * adds country
     * @param p_CountryId name of country
     * @param p_ContinentId name of continent
     */
    @Override
    public void addCountry(String p_CountryId, String p_ContinentId) {
        errorLoadMapFirst();
    }

    
    /** 
     * removes country
     * @param p_CountryId name of country
     */
    @Override
    public void removeCountry(String p_CountryId) {
        errorLoadMapFirst();
    }

    
    /** 
     * adds continent
     * @param p_ContinentId name of continent
     * @param p_ControlValue control value
     */
    @Override
    public void addContinent(String p_ContinentId, int p_ControlValue) {
        errorLoadMapFirst();
    }

    
    /** 
     * removes a continent
     * @param p_ContinentId name of continent
     */
    @Override
    public void removeContinent(String p_ContinentId) {
        errorLoadMapFirst();
    }

    
    /** 
     * adds a neighbor
     * @param p_CountryId name of country
     * @param p_NeighborId name of neighbor
     */
    @Override
    public void addNeighbor(String p_CountryId, String p_NeighborId) {
        errorLoadMapFirst();
    }

    
    /** 
     * removes a neighbor
     * @param p_CountryId name of country
     * @param p_NeighborId name of neighbor
     */
    @Override
    public void removeNeighbor(String p_CountryId, String p_NeighborId) {
        errorLoadMapFirst();
    }

    
    /** 
     * saves map
     * @param p_FileName map file name
     */
    @Override
    public void saveMap(String p_FileName, String p_MapType) {
        errorLoadMapFirst();
    }

    
    /** 
     * edits map
     * @param p_FileName map file name
     */
    @Override
    public void editMap(String p_FileName) {
        d_GameEngine.getD_MapEditor().editMap(p_FileName);
        next();
        d_GameEngine.getD_LogEntryBuffer().setD_log("Opened map editor for: " + p_FileName);
        d_GameEngine.getD_LogEntryBuffer().notifyObservers(d_GameEngine.getD_LogEntryBuffer());
    }

    
    /** 
     * validates map
     * @param p_Map map object
     */
    @Override
    public void validateMap(Map p_Map) {
        errorLoadMapFirst();
    }

    /**
     * Savegame method which is invalid in PreMapEditLoadPhase phase
     * @param p_FileName filename with which the game has to be saved
     */
    @Override
    public void saveGame(String p_FileName) {
        invalidCommandMessage();
    }
}
