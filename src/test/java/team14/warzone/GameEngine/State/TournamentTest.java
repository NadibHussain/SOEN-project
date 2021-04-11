package team14.warzone.GameEngine.State;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.DisplayName;

import team14.warzone.Console.Console;
import team14.warzone.GameEngine.GameEngine;
import team14.warzone.MapModule.MapEditor;

public class TournamentTest {
     /**
     * console field
     */
    private Console d_Console;
    /**
     * map editor field
     */
    private MapEditor d_MapEditor;
    /**
     * game engine field
     */
    private GameEngine d_GE;

    @Before
    public void setup() {
        // object instantiation
        d_Console = new Console();
        d_MapEditor = new MapEditor();
        d_GE = new GameEngine(d_Console, d_MapEditor);
    }
    @Test
    @DisplayName("Testing Tournament")
    public void tournamentRun() {
        List<String> l_MapList = new ArrayList<>();
        l_MapList.add("bigeurope.map");
        List<String> l_PlayerList = new ArrayList<>();
        l_PlayerList.add("cheater");
        l_PlayerList.add("cheater");
        l_PlayerList.add("cheater");
        String l_NumofGame = "3";
        String l_NumofTurn = "4";

        Tournament t = new Tournament(d_GE);
        t.tournamentAddMaps(l_MapList);
        t.tournamentAddPlayersStrategies(l_PlayerList);
        t.tournamentNumOfGames(l_NumofGame);
        t.tournamentMaxNumOfTurns(l_NumofTurn);

        t.run();

    }
    
}
