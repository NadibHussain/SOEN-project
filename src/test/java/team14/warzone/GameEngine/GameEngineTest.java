package team14.warzone.GameEngine;

import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.jupiter.api.DisplayName;
import team14.warzone.Console.Console;
import team14.warzone.MapModule.MapEditor;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class GameEngineTest {
    public static GameEngine d_GE;
    public static List<Player> d_PlayerList;
    public static Console d_Console;
    public static MapEditor d_MapEditor;

    @BeforeClass
    public static void initialize() {
        d_Console = new Console();
        d_MapEditor = new MapEditor();
        d_GE = new GameEngine(d_Console, d_MapEditor);
        d_GE.loadMap("bigeurope.map");
        d_GE.addPlayer("Player1");
        d_GE.addPlayer("Player2");
        d_GE.assignCountries();
    }

    @Test
    @DisplayName("Testing Reinforcement")
    public void testReinforcement() {
        assertEquals(20, d_GE.getD_PlayerList().get(0).getD_TotalNumberOfArmies());
        d_GE.reInforcement();
        assertEquals(50, d_GE.getD_PlayerList().get(0).getD_TotalNumberOfArmies());
    }
}