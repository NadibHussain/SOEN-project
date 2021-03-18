package team14.warzone.GameEngine.State;

import team14.warzone.GameEngine.GameEngine;
import team14.warzone.MapModule.Map;

public class MapEditorPhase extends Phase {

    public MapEditorPhase(GameEngine p_GameEngine) {
        super(p_GameEngine);
    }

    @Override
    public void run() {
        while (true) {
            issueCommands();
            executeCommands();
        }
    }

    @Override
    public void addCountry(String p_CountryId, String p_ContinentId) {

    }

    @Override
    public void removeCountry(String p_CountryId) {

    }

    @Override
    public void addContinent(String p_ContinentId, int p_ControlValue) {

    }

    @Override
    public void removeContinent(String p_ContinentId) {

    }

    @Override
    public void addNeighbor(String p_CountryId, String p_NeighborId) {

    }

    @Override
    public void removeNeighbor(String p_CountryId, String p_NeighborId) {

    }

    @Override
    public void loadMap(String p_FileName) {

    }

    @Override
    public void saveMap(String p_FileName) {

    }

    @Override
    public void editMap(String p_FileName) {

    }

    @Override
    public void validateMap(Map p_Map) {

    }

    @Override
    public void addPlayer() {

    }

    @Override
    public void removePlayer() {

    }

    @Override
    public void assignCountries() {

    }

    @Override
    public void reinforce() {

    }

    @Override
    public void issueCommands() {
        // use console to prompt user to enter command
        // console.readMapInput(); return { { editcountry, -add, canada, northamerica }, { editcountry, -remove usa } }
        // return {{ deploy, canada, 5 }}

        // create command object
    }

    @Override
    public void executeCommands() {
        // execute the appropriate map editor command
    }

    @Override
    public void deploy() {
        invalidCommandMessage();
    }

    @Override
    public void advance() {

    }

    @Override
    public void bomb() {

    }

    @Override
    public void blockade() {

    }

    @Override
    public void airlift() {

    }

    @Override
    public void diplomacy() {

    }
}
