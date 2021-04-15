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
        if (d_GameEngine.isD_TournamentMode()){
            d_GameEngine.setD_GameOver(true);
//            d_GameEngine.setD_CurrentPhase(d_GameEngine.getD_IssueOrdersPhase());
        }
        else
            System.exit(0);
    }


    /**
     * @param p_CountryId Country ID to add
     * @param p_ContinentId Continent ID to add
     */
    @Override
    public void addCountry(String p_CountryId, String p_ContinentId) {

    }


    /**
     * @param p_CountryId Country ID to remove
     */
    @Override
    public void removeCountry(String p_CountryId) {

    }


    /**
     * @param p_ContinentId Continent ID to add
     * @param p_ControlValue Continents control value
     */
    @Override
    public void addContinent(String p_ContinentId, int p_ControlValue) {

    }


    /**
     * @param p_ContinentId Continent ID to remove
     */
    @Override
    public void removeContinent(String p_ContinentId) {

    }


    /**
     * @param p_CountryId Country ID
     * @param p_NeighborId Neighbour ID to add
     */
    @Override
    public void addNeighbor(String p_CountryId, String p_NeighborId) {

    }


    /**
     * @param p_CountryId Country ID
     * @param p_NeighborId Neighbour ID to remove
     */
    @Override
    public void removeNeighbor(String p_CountryId, String p_NeighborId) {

    }


    /**
     * @param p_FileName File Name to load map from
     */
    @Override
    public void loadMap(String p_FileName) {

    }


    /**
     * @param p_FileName save map with filename
     */
    @Override
    public void saveMap(String p_FileName, String p_MapType) {

    }


    /**
     * @param p_FileName file name of map to edit
     */
    @Override
    public void editMap(String p_FileName) {

    }


    /**
     * @param p_Map Map to be validated
     */
    @Override
    public void validateMap(Map p_Map) {

    }


    /**
     * @param p_Name Name of player
     * @param p_PlayerType type of player
     */
    @Override
    public void addPlayer(String p_Name, String p_PlayerType) {

    }


    /**
     * @param p_Name name of player
     */
    @Override
    public void removePlayer(String p_Name) {

    }

    @Override
    public void assignCountries() {

    }

    @Override
    public void reinforce() {

    }


    /**
     * @param p_FileName File name for game to be saved as
     */
    @Override
    public void saveGame(String p_FileName) {
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
