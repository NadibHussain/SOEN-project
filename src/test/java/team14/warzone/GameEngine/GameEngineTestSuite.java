package team14.warzone.GameEngine;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import team14.warzone.GameEngine.Commands.*;
import team14.warzone.GameEngine.State.GameOverPhaseTest;
import team14.warzone.GameEngine.State.IssueOrdersPhaseTest;
import team14.warzone.GameEngine.State.PreMapLoadPhaseTest;
import team14.warzone.GameEngine.State.TournamentTest;
import team14.warzone.GameEngine.Strategy.AggressiveTest;
import team14.warzone.GameEngine.Strategy.BenevolentTest;
import team14.warzone.GameEngine.Strategy.CheaterTest;

@RunWith(Suite.class)
@Suite.SuiteClasses({
        GameEngineTest.class,
        AdvanceTest.class, AirliftTest.class, BlockadeTest.class, BombTest.class, DeployTest.class,
        DiplomacyTest.class, ObserverTest.class,
        IssueOrdersPhaseTest.class, PreMapLoadPhaseTest.class, GameOverPhaseTest.class,
        CardTest.class, AggressiveTest.class, BenevolentTest.class, CheaterTest.class,
        CardTest.class, GameSaveTest.class, GameLoadTest.class, GameOverPhaseTest.class, IssueOrdersPhaseTest.class,
        PreMapLoadPhaseTest.class, TournamentTest.class,
})

/**
 * Test Suite for GameEngine
 */
public class GameEngineTestSuite {
//place holder
}
