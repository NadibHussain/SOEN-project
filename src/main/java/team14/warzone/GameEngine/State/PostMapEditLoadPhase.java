package team14.warzone.GameEngine.State;

import team14.warzone.GameEngine.GameEngine;
import team14.warzone.MapModule.Map;

/**
 * This is a class for PostMapEditLoadPhase
 */
public class PostMapEditLoadPhase extends MapEditorPhase{
    /**
     * PostMapEditLoadPhase
     * @param p_GameEngine GE
     */
    public PostMapEditLoadPhase(GameEngine p_GameEngine) {
        super(p_GameEngine);
    }

    
    /** 
     * add country
     * @param p_CountryId name of country
     * @param p_ContinentId name of continent
     */
    @Override
    public void addCountry(String p_CountryId, String p_ContinentId) {
        d_GameEngine.getD_MapEditor().getD_LoadedMap().addCountry(p_CountryId, p_ContinentId);
        d_GameEngine.getD_LogEntryBuffer().setD_log("Added Country:"+ p_CountryId);
        d_GameEngine.getD_LogEntryBuffer().notifyObservers(d_GameEngine.getD_LogEntryBuffer());
    }

    
    /** 
     * remove country
     * @param p_CountryId name of country
     */
    @Override
    public void removeCountry(String p_CountryId) {
        d_GameEngine.getD_MapEditor().getD_LoadedMap().removeCountry(p_CountryId);
        d_GameEngine.getD_LogEntryBuffer().setD_log("Removed Country:"+ p_CountryId);
        d_GameEngine.getD_LogEntryBuffer().notifyObservers(d_GameEngine.getD_LogEntryBuffer());
    }

    
    /** 
     * add continent
     * @param p_ContinentId name of continent
     * @param p_ControlValue control value
     */
    @Override
    public void addContinent(String p_ContinentId, int p_ControlValue) {
        d_GameEngine.getD_MapEditor().getD_LoadedMap().addContinent(p_ContinentId, p_ControlValue);
        d_GameEngine.getD_LogEntryBuffer().setD_log("Added Continent:"+ p_ContinentId);
        d_GameEngine.getD_LogEntryBuffer().notifyObservers(d_GameEngine.getD_LogEntryBuffer());
    }

    
    /** 
     * remove continent
     * @param p_ContinentId name of continent
     */
    @Override
    public void removeContinent(String p_ContinentId) {
        d_GameEngine.getD_MapEditor().getD_LoadedMap().removeContinent(p_ContinentId);
        d_GameEngine.getD_LogEntryBuffer().setD_log("Removed Continent:"+ p_ContinentId);
        d_GameEngine.getD_LogEntryBuffer().notifyObservers(d_GameEngine.getD_LogEntryBuffer());
    }

    
    /** 
     * add neighbor
     * @param p_CountryId name of country
     * @param p_NeighborId name of neighbor
     */
    @Override
    public void addNeighbor(String p_CountryId, String p_NeighborId) {
        d_GameEngine.getD_MapEditor().getD_LoadedMap().addNeighbour(p_CountryId, p_NeighborId);
        d_GameEngine.getD_LogEntryBuffer().setD_log("Made"+ p_CountryId+"and "+p_NeighborId+" neighbours");
        d_GameEngine.getD_LogEntryBuffer().notifyObservers(d_GameEngine.getD_LogEntryBuffer());
    }

    
    /** 
     * remove neighbor
     * @param p_CountryId name of country
     * @param p_NeighborId name of neighbor
     */
    @Override
    public void removeNeighbor(String p_CountryId, String p_NeighborId) {
        d_GameEngine.getD_MapEditor().getD_LoadedMap().removeNeighbour(p_CountryId, p_NeighborId);
        d_GameEngine.getD_LogEntryBuffer().setD_log("Removed"+ p_CountryId+"and "+p_NeighborId+"as neighbours");
        d_GameEngine.getD_LogEntryBuffer().notifyObservers(d_GameEngine.getD_LogEntryBuffer());
    }

    
    /** 
     * saves map
     * @param p_FileName name of map file
     */
    @Override
    public void saveMap(String p_FileName, String p_MapType) {
        d_GameEngine.getD_MapEditor().saveMap(p_MapType,p_FileName);
        d_GameEngine.getD_LogEntryBuffer().setD_log("Saved map:"+p_FileName);
        d_GameEngine.getD_LogEntryBuffer().notifyObservers(d_GameEngine.getD_LogEntryBuffer());
    }

    
    /** 
     * edits map
     * @param p_FileName name of map file
     */
    @Override
    public void editMap(String p_FileName) {
        d_GameEngine.getD_MapEditor().editMap(p_FileName);
        d_GameEngine.getD_LogEntryBuffer().setD_log("Edit map:"+p_FileName);
        d_GameEngine.getD_LogEntryBuffer().notifyObservers(d_GameEngine.getD_LogEntryBuffer());
    }

    
    /** 
     * validates map
     * @param p_Map map object
     */
    @Override
    public void validateMap(Map p_Map) {
        d_GameEngine.getD_MapEditor().validateMap(p_Map);
        d_GameEngine.getD_LogEntryBuffer().setD_log("Validate map:"+p_Map);
        d_GameEngine.getD_LogEntryBuffer().notifyObservers(d_GameEngine.getD_LogEntryBuffer());
    }

    /**
     * Savegame method which is invalid in PostMapEditLoadPhase phase
     * @param p_FileName filename with which the game has to be saved
     */
    @Override
    public void saveGame(String p_FileName) {
        invalidCommandMessage();
    }

}
