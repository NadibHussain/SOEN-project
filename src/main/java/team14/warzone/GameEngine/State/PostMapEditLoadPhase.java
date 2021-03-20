package team14.warzone.GameEngine.State;

import team14.warzone.GameEngine.GameEngine;
import team14.warzone.MapModule.Map;

public class PostMapEditLoadPhase extends MapEditorPhase{

    public PostMapEditLoadPhase(GameEngine p_GameEngine) {
        super(p_GameEngine);
    }

    @Override
    public void addCountry(String p_CountryId, String p_ContinentId) {
        d_GameEngine.getD_MapEditor().getD_LoadedMap().addCountry(p_CountryId, p_ContinentId);
    }

    @Override
    public void removeCountry(String p_CountryId) {
        d_GameEngine.getD_MapEditor().getD_LoadedMap().removeCountry(p_CountryId);
    }

    @Override
    public void addContinent(String p_ContinentId, int p_ControlValue) {
        d_GameEngine.getD_MapEditor().getD_LoadedMap().addContinent(p_ContinentId, p_ControlValue);
    }

    @Override
    public void removeContinent(String p_ContinentId) {
        d_GameEngine.getD_MapEditor().getD_LoadedMap().removeContinent(p_ContinentId);
    }

    @Override
    public void addNeighbor(String p_CountryId, String p_NeighborId) {
        d_GameEngine.getD_MapEditor().getD_LoadedMap().addNeighbour(p_CountryId, p_NeighborId);
    }

    @Override
    public void removeNeighbor(String p_CountryId, String p_NeighborId) {
        d_GameEngine.getD_MapEditor().getD_LoadedMap().removeNeighbour(p_CountryId, p_NeighborId);
    }

    @Override
    public void saveMap(String p_FileName) {
        d_GameEngine.getD_MapEditor().saveMap(p_FileName);
    }

    @Override
    public void editMap(String p_FileName) {
        d_GameEngine.getD_MapEditor().editMap(p_FileName);
    }

    @Override
    public void validateMap(Map p_Map) {
        d_GameEngine.getD_MapEditor().validateMap(p_Map);
    }
}
