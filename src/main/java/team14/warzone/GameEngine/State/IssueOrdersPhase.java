package team14.warzone.GameEngine.State;

import team14.warzone.Console.Console;
import team14.warzone.GameEngine.Commands.AdminCommands;
import team14.warzone.GameEngine.GameEngine;
import team14.warzone.GameEngine.Player;
import team14.warzone.MapModule.Continent;
import team14.warzone.MapModule.Map;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class IssueOrdersPhase extends GamePlayPhase {

    public IssueOrdersPhase(GameEngine p_GameEngine) {
        super(p_GameEngine);
    }

    @Override
    public void run() {
        reinforce();
        issueCommands();
    }

    /**
     * This method calculates and assign the reinforcement at the beginning of each turn
     */
    @Override
    public void reinforce() {
        for (Player l_Player : d_GameEngine.getD_PlayerList()) {
            //1. # of territories owned divided by 3
            int l_PlayerEnforcement = l_Player.getD_CountriesOwned().size() / 3;
            //2. if the player owns all the territories of an entire continent the player is given
            // a control bonus value
            int l_ControlValueEnforcement = 0;
            for (Continent l_Continent : d_GameEngine.getD_LoadedMap().getD_Continents()) {
                //check if all countries belong to the l_Continent are owned by l_Player
                if (l_Player.getD_CountriesOwned().containsAll(d_GameEngine.getD_LoadedMap().getCountryListOfContinent(l_Continent.getD_ContinentID())))
                    l_ControlValueEnforcement += l_Continent.getD_ControlValue();
            }
            //3.the minimal number of reinforcement armies for any player is 3 + control values
            // of continents he owns
            l_PlayerEnforcement = Math.max(l_PlayerEnforcement, 3) + l_ControlValueEnforcement;
            //give reinforcement to the player
            d_GameEngine.getD_LogEntryBuffer().setD_log(l_Player.getD_Name() + " gets " + l_PlayerEnforcement +
                    "armies as reinforcement");
            d_GameEngine.getD_LogEntryBuffer().notifyObservers(d_GameEngine.getD_LogEntryBuffer());
            l_Player.setD_TotalNumberOfArmies(l_Player.getD_TotalNumberOfArmies() + l_PlayerEnforcement);
        }
    }

    @Override
    public void issueCommands() {
        ArrayList<Player> l_PlayerList = d_GameEngine.getD_PlayerList();
        ArrayList<Boolean> l_Flag = new ArrayList<Boolean>(Arrays.asList(new Boolean[l_PlayerList.size()]));
        Collections.fill(l_Flag, Boolean.FALSE);

        while (l_Flag.contains(Boolean.FALSE)) {
            int l_Counter = 0;
            while (l_Counter < l_PlayerList.size()) {
                // check if player has already passed
                if (!l_Flag.get(l_Counter)) {
                    // print current player's name and ask for command
                    d_GameEngine.setD_CurrentPlayer(l_PlayerList.get(l_Counter));
                    Player l_CurrentPlayer = l_PlayerList.get(l_Counter);
                    Console.displayMsg("Enter command, for " + d_GameEngine.getD_CurrentPlayer().getD_Name() +
                            ", number of undeployed armies : "
                            + (l_CurrentPlayer.getD_TotalNumberOfArmies() - l_CurrentPlayer.getD_ArmiesOrderedToBeDeployed()));
                    List<List<String>> l_CommandStrList = Console.readInput();
                    // passing the turn
                    if (!l_CommandStrList.isEmpty()) {
                        if (l_CommandStrList.get(0).get(0).equals("pass")) {
                            // check if all armies are issued to be deployed
                            if (l_CurrentPlayer.getD_ArmiesOrderedToBeDeployed() >= l_CurrentPlayer.getD_TotalNumberOfArmies()) {
                                // set flag to true
                                l_Flag.set(l_Counter, Boolean.TRUE);
                                l_Counter++;
                            } else {
                                Console.displayMsg("Error: cannot pass, still " + (l_CurrentPlayer.getD_TotalNumberOfArmies() - l_CurrentPlayer.getD_ArmiesOrderedToBeDeployed()) + " armies are undeployed");
                            }
                        }
                        // check if command is admin command
                        else if (AdminCommands.VALID_ADMIN_COMMANDS.contains(l_CommandStrList.get(0).get(0))) {
                            createAdminCommand(l_CommandStrList);
                            executeAdminCommands();
                        }
                        // for game orders use player to instantiate order
                        else {
                            d_GameEngine.setD_OrderStrBuffer(l_CommandStrList);
                            l_CurrentPlayer.issueOrder();
                            l_Counter++;
                        }
                    }
                } else {
                    l_Counter++;
                }
            }
        }
        next();
    }

    @Override
    public void executeCommands() {
        invalidCommandMessage();
    }

    /**
     * During order issuing if player uses showmap/admin commands, execute them instantly
     * Execute all admin commands appended to the command buffer
     */
    private void executeAdminCommands() {
        for (AdminCommands l_AdminCommands : d_GameEngine.getD_CommandBuffer()) {
            l_AdminCommands.execute();
        }
        d_GameEngine.clearCommandBuffer();
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
