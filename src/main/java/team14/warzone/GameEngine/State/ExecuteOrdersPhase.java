package team14.warzone.GameEngine.State;

import team14.warzone.GameEngine.GameEngine;
import team14.warzone.GameEngine.Player;
import team14.warzone.MapModule.Map;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

public class ExecuteOrdersPhase extends GamePlayPhase {

    public ExecuteOrdersPhase(GameEngine p_GameEngine) {
        super(p_GameEngine);
    }

    @Override
    public void run() {
        executeCommands();
    }

    @Override
    public void executeCommands() {
        ArrayList<Player> l_PlayerList = d_GameEngine.getD_PlayerList();
        ArrayList<Boolean> l_Flag = new ArrayList<Boolean>(Arrays.asList(new Boolean[l_PlayerList.size()]));
        Collections.fill(l_Flag, Boolean.FALSE);

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
        // check if all countries owned by 1 player
        if (!gameOverCheck(l_PlayerList))
            next();
    }

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

    @Override
    public void issueCommands() {
        issueCommands();
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
        invalidCommandMessage();
    }

    @Override
    public void removePlayer(String p_Name) {
        invalidCommandMessage();
    }

    @Override
    public void assignCountries() {
        invalidCommandMessage();
    }

    @Override
    public void reinforce() {
        invalidCommandMessage();
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
        invalidCommandMessage();
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
