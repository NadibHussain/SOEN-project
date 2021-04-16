package team14.warzone.GameEngine.State;

import team14.warzone.GameEngine.GameEngine;
import team14.warzone.GameEngine.Player;
import team14.warzone.MapModule.Country;
import team14.warzone.MapModule.Map;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Scanner;

/**
 * This is a class for ExecuteOrdersPhase
 */
public class ExecuteOrdersPhase extends GamePlayPhase {
    /**
     * ExecuteOrdersPhase
     *
     * @param p_GameEngine GE
     */
    public ExecuteOrdersPhase(GameEngine p_GameEngine) {
        super(p_GameEngine);
    }

    /**
     * Run execute order phase
     */
    @Override
    public void run() {
        executeCommands();
//        promptEnterKey();
    }

    /**
     * execute commands
     */
    @Override
    public void executeCommands() {
        ArrayList<Player> l_PlayerList = d_GameEngine.getD_PlayerList();
        ArrayList<Boolean> l_Flag = new ArrayList<Boolean>(Arrays.asList(new Boolean[l_PlayerList.size()]));
        Collections.fill(l_Flag, Boolean.FALSE);

        // reset received reinforcement flags
        for (Player l_Player : l_PlayerList) {
            l_Player.resetReceivedReinforcement();
        }

        while (l_Flag.contains(Boolean.FALSE)) {
            for (int i = 0; i < l_PlayerList.size(); i++) {
                d_GameEngine.setD_CurrentPlayer(l_PlayerList.get(i));
                l_PlayerList.get(i).nextOrder();
                if (l_PlayerList.get(i).getD_OrderList().isEmpty())
                    l_Flag.set(i, Boolean.TRUE);
            }
        }
        // reset all the 2 step orders
        d_GameEngine.resetOrderBuffer();
        // reset players card received flags
        for (Player l_Player : l_PlayerList) {
            l_Player.resetCardReceivedFlag();
            for (Country l_Country : l_Player.getD_CountriesOwned()) {
                l_Country.setD_UsedCountry(false);
            }
        }

        // check if all countries owned by 1 player
        if (!gameOverCheck(l_PlayerList))
            next();
    }

    /**
     * checks if game is over; if player conquers all countries
     *
     * @param p_Players Players
     * @return true if game over
     */
    private boolean gameOverCheck(ArrayList<Player> p_Players) {
        for (Player l_Player : p_Players) {
            if (l_Player.getD_CountriesOwned().containsAll(d_GameEngine.getD_LoadedMap().getD_Countries())) {
                // set GE to game over phase
                d_GameEngine.setD_CurrentPhase(d_GameEngine.getD_GameOverPhase());
                // set current player to winning player
                d_GameEngine.setD_CurrentPlayer(l_Player);
                return true;
            }
        }
        return false;
    }

    /**
     * Wait until enter key is pressed
     */
    public void promptEnterKey() {
        System.out.println("\nPress \"ENTER\" to continue...");
        Scanner scanner = new Scanner(System.in);
        scanner.nextLine();
    }

    /**
     * issues commands
     */
    @Override
    public void issueCommands() {
        invalidCommandMessage();
    }

    /**
     * Add to country list
     *
     * @param p_CountryId   name of country
     * @param p_ContinentId name of continent
     */
    @Override
    public void addCountry(String p_CountryId, String p_ContinentId) {
        invalidCommandMessage();
    }

    /**
     * Remove from country list
     *
     * @param p_CountryId name of country
     */
    @Override
    public void removeCountry(String p_CountryId) {
        invalidCommandMessage();
    }

    /**
     * Add to continent list
     *
     * @param p_ContinentId  name of continent
     * @param p_ControlValue control value
     */
    @Override
    public void addContinent(String p_ContinentId, int p_ControlValue) {
        invalidCommandMessage();
    }

    /**
     * REmove from Continent list
     *
     * @param p_ContinentId name of continent
     */
    @Override
    public void removeContinent(String p_ContinentId) {
        invalidCommandMessage();
    }

    /**
     * Add neighbor
     *
     * @param p_CountryId  name of country
     * @param p_NeighborId name of neighbor country
     */
    @Override
    public void addNeighbor(String p_CountryId, String p_NeighborId) {
        invalidCommandMessage();
    }

    /**
     * Remove neighbor
     *
     * @param p_CountryId  name of country
     * @param p_NeighborId name of neighbor
     */
    @Override
    public void removeNeighbor(String p_CountryId, String p_NeighborId) {
        invalidCommandMessage();
    }

    /**
     * Loads map
     *
     * @param p_FileName name of map file
     */
    @Override
    public void loadMap(String p_FileName) {
        invalidCommandMessage();
    }

    /**
     * Saves map
     *
     * @param p_FileName name of map file
     */
    @Override
    public void saveMap(String p_FileName, String p_MapType) {
        invalidCommandMessage();
    }

    /**
     * Edit map
     *
     * @param p_FileName name of map file
     */
    @Override
    public void editMap(String p_FileName) {
        invalidCommandMessage();
    }

    /**
     * Validates a map
     *
     * @param p_Map map object
     */
    @Override
    public void validateMap(Map p_Map) {
        invalidCommandMessage();
    }

    /**
     * Methods adds new player to playerlist
     *
     * @param p_Name Name of the player
     */
    @Override
    public void addPlayer(String p_Name, String p_PlayerType) {
        invalidCommandMessage();
    }

    /**
     * Method remove a player from the player list
     *
     * @param p_Name String name of the player
     */
    @Override
    public void removePlayer(String p_Name) {
        invalidCommandMessage();
    }

    /**
     * Method assigns all countries randomly between the players
     */
    @Override
    public void assignCountries() {
        invalidCommandMessage();
    }

    /**
     * Reinforcement beginning of game play
     */
    @Override
    public void reinforce() {
        invalidCommandMessage();
    }

    /**
     * Deploy command
     */
    @Override
    public void deploy() {
        invalidCommandMessage();
    }

    /**
     * Advance order
     */
    @Override
    public void advance() {
        invalidCommandMessage();
    }

    /**
     * Bomb order
     */
    @Override
    public void bomb() {
        invalidCommandMessage();
    }

    /**
     * Blockade order
     */
    @Override
    public void blockade() {
        invalidCommandMessage();
    }

    /**
     * Airlift order
     */
    @Override
    public void airlift() {
        invalidCommandMessage();
    }

    /**
     * Diplomacy order
     */
    @Override
    public void diplomacy() {
        invalidCommandMessage();
    }
}
