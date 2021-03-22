package team14.warzone.GameEngine.State;

import team14.warzone.Console.Console;
import team14.warzone.GameEngine.Commands.Command;
import team14.warzone.GameEngine.GameEngine;
import team14.warzone.MapModule.Map;

import java.util.List;

public class StartupPhase extends GamePlayPhase {

    public StartupPhase(GameEngine p_GameEngine) {
        super(p_GameEngine);
    }

    @Override
    public void run() {
        if (d_GameEngine.getD_LoadedMap() == null) {
            System.out.println("Please load map first before starting Startup phase");
            d_GameEngine.setD_CurrentPhase(d_GameEngine.getD_PreMapLoadPhase());
        }
        issueCommands();
        executeCommands();
    }

    @Override
    public void addCountry(String p_CountryId, String p_ContinentId) {
        invalidCommandMessage();
    }

    @Override
    public void removeCountry(String p_CountryId) {
        invalidCommandMessage();
    }

    @Override
    public void addContinent(String p_ContinentId, int p_ControlValue) {
        invalidCommandMessage();
    }

    @Override
    public void removeContinent(String p_ContinentId) {
        invalidCommandMessage();
    }

    @Override
    public void addNeighbor(String p_CountryId, String p_NeighborId) {
        invalidCommandMessage();
    }

    @Override
    public void removeNeighbor(String p_CountryId, String p_NeighborId) {
        invalidCommandMessage();
    }

    @Override
    public void loadMap(String p_FileName) {
        invalidCommandMessage();
    }

    @Override
    public void saveMap(String p_FileName) {
        invalidCommandMessage();
    }

    @Override
    public void editMap(String p_FileName) {
        invalidCommandMessage();
    }

    @Override
    public void validateMap(Map p_Map) {
        invalidCommandMessage();
    }

    @Override
    public void addPlayer(String p_Name) {
        d_GameEngine.addPlayer(p_Name);
    }

    @Override
    public void removePlayer(String p_Name) {
        d_GameEngine.removePlayer(p_Name);
    }

    @Override
    public void assignCountries() {
        d_GameEngine.assignCountries();
        next();
    }

    @Override
    public void reinforce() {
        invalidCommandMessage();
    }

    @Override
    public void issueCommands() {

        System.out.println("Enter command:");
        List<List<String>> l_CommandStrList = Console.readInput();
        createAdminCommand(l_CommandStrList);
    }

    @Override
    public void executeCommands() {
        for (Command l_Command : d_GameEngine.getD_CommandBuffer()) {
            l_Command.execute();
        }
        d_GameEngine.clearCommandBuffer();
    }

    @Override
    public void deploy() {
        invalidCommandMessage();
    }

    @Override
    public void advance() {
        invalidCommandMessage();
    }

    @Override
    public void bomb() {
        invalidCommandMessage();
    }

    @Override
    public void blockade() {

    }

    @Override
    public void airlift() {
        invalidCommandMessage();
    }

    @Override
    public void diplomacy() {
        invalidCommandMessage();
    }

}
