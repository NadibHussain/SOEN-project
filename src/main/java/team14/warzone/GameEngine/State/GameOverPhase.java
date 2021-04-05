package team14.warzone.GameEngine.State;

import team14.warzone.GameEngine.GameEngine;
import team14.warzone.MapModule.Map;

/**
 * This is a class for GameOverPhase
 */
public class GameOverPhase extends GamePlayPhase {
    /**
     * ExecuteOrdersPhase
     *
     * @param p_GameEngine GE
     */
    public GameOverPhase(GameEngine p_GameEngine) {
        super(p_GameEngine);
    }

    @Override
    public void run() {
        System.out.println("Game over!");
        System.out.println("Congratulations, winner: " + d_GameEngine.getD_CurrentPlayer().getD_Name());
        System.exit(0);
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
    public void addPlayer(String p_Name) {

    }

    @Override
    public void removePlayer(String p_Name) {

    }

    @Override
    public void assignCountries() {

    }

    @Override
    public void reinforce() {

    }

    @Override
    public void saveGame(String p_FileName) {
        invalidCommandMessage();
    }

    @Override
    public void loadGame(String p_FileName) {
        invalidCommandMessage();
    }

    @Override
    public void issueCommands() {

    }

    @Override
    public void executeCommands() {

    }

    @Override
    public void deploy() {

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
