package team14.warzone.GameEngine;

import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.DisplayName;
import team14.warzone.Console.Console;
import team14.warzone.Console.InputValidator;
import team14.warzone.MapModule.MapEditor;
import static org.junit.Assert.assertTrue;


import java.io.*;

public class GameSaveLoadTest {

    private GameEngine d_GE;
    private MapEditor d_MapEditor;
    private Console d_Console;
    private GameSaveLoad d_GameSaveLoad;


    @Before
    public void setup() {
        d_MapEditor = new MapEditor();
        d_Console = new Console();
        d_GE = new GameEngine(d_Console, d_MapEditor);
        d_GE.getD_CurrentPhase().loadMap("bigeurope.map");
        InputValidator.CURRENT_PHASE = InputValidator.Phase.STARTUP;
        d_GE.getD_CurrentPhase().addPlayer("p1", "human");
        d_GE.getD_CurrentPhase().addPlayer("p2", "human");
        d_GE.getD_CurrentPhase().assignCountries();
        InputValidator.CURRENT_PHASE = InputValidator.Phase.GAMEPLAY;
        d_GE.setD_CurrentPlayer(d_GE.getD_PlayerList().get(0));
        d_GE.getD_LoadedMap().findCountry("b2").setD_NumberOfArmies(50);
        d_GE.setD_CurrentPlayer(d_GE.getD_PlayerList().get(1));
        d_GE.getD_LoadedMap().findCountry("b1").setD_NumberOfArmies(50);
        Player l_P1 = d_GE.getD_PlayerList().get(0);
        d_GE.setD_CurrentPlayer(l_P1);
        d_GameSaveLoad = new GameSaveLoad(d_GE);
    }

    @Test
    @DisplayName("Testing Save Game")
    public void saveTest() {
        assertTrue(d_GameSaveLoad.saveGame("SaveTest"));
    }

}
