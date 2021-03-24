package team14.warzone.GameEngine.State;

import team14.warzone.GameEngine.GameEngine;
import team14.warzone.MapModule.Map;

public class PreMapLoadPhase extends MapEditorPhase {

    public PreMapLoadPhase(GameEngine p_GameEngine) {
        super(p_GameEngine);
    }

    public void errorLoadMapFirst() {
        System.out.println("Error: load a map first by using <editmap> <filename>");
    }

    @Override
    public void addCountry(String p_CountryId, String p_ContinentId) {
        errorLoadMapFirst();
    }

    @Override
    public void removeCountry(String p_CountryId) {
        errorLoadMapFirst();
    }

    @Override
    public void addContinent(String p_ContinentId, int p_ControlValue) {
        errorLoadMapFirst();
    }

    @Override
    public void removeContinent(String p_ContinentId) {
        errorLoadMapFirst();
    }

    @Override
    public void addNeighbor(String p_CountryId, String p_NeighborId) {
        errorLoadMapFirst();
    }

    @Override
    public void removeNeighbor(String p_CountryId, String p_NeighborId) {
        errorLoadMapFirst();
    }

    @Override
    public void saveMap(String p_FileName) {
        errorLoadMapFirst();
    }

    @Override
    public void editMap(String p_FileName) {
        d_GameEngine.getD_MapEditor().editMap(p_FileName);
        d_GameEngine.getD_LogEntryBuffer().setD_log("Opened map editor for:"+ p_FileName);
        d_GameEngine.getD_LogEntryBuffer().notifyObservers(d_GameEngine.getD_LogEntryBuffer());
    }

    @Override
    public void validateMap(Map p_Map) {
        errorLoadMapFirst();
    }
}
