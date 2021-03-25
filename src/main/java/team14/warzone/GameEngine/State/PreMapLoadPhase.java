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
     * @param p_CountryId
     * @param p_ContinentId
     */
    @Override
    public void addCountry(String p_CountryId, String p_ContinentId) {
        errorLoadMapFirst();
    }

    
    /** 
     * removes country
     * @param p_CountryId
     */
    @Override
    public void removeCountry(String p_CountryId) {
        errorLoadMapFirst();
    }

    
    /** 
     * adds continent
     * @param p_ContinentId
     * @param p_ControlValue
     */
    @Override
    public void addContinent(String p_ContinentId, int p_ControlValue) {
        errorLoadMapFirst();
    }

    
    /** 
     * removes a continent
     * @param p_ContinentId
     */
    @Override
    public void removeContinent(String p_ContinentId) {
        errorLoadMapFirst();
    }

    
    /** 
     * adds a neighbor
     * @param p_CountryId
     * @param p_NeighborId
     */
    @Override
    public void addNeighbor(String p_CountryId, String p_NeighborId) {
        errorLoadMapFirst();
    }

    
    /** 
     * removes a neighbor
     * @param p_CountryId
     * @param p_NeighborId
     */
    @Override
    public void removeNeighbor(String p_CountryId, String p_NeighborId) {
        errorLoadMapFirst();
    }

    
    /** 
     * saves map
     * @param p_FileName
     */
    @Override
    public void saveMap(String p_FileName) {
        errorLoadMapFirst();
    }

    
    /** 
     * edits map
     * @param p_FileName
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
     * @param p_Map
     */
    @Override
    public void validateMap(Map p_Map) {
        errorLoadMapFirst();
    }
}
