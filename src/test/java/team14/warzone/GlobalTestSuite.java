package team14.warzone;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;

import team14.warzone.Console.ConsoleTestSuite;
import team14.warzone.GameEngine.GameEngineTestSuite;
import team14.warzone.MapModule.MapModuleTestSuite;


@RunWith(Suite.class)
@Suite.SuiteClasses({
    MapModuleTestSuite.class,
    GameEngineTestSuite.class,
    ConsoleTestSuite.class
})

public class GlobalTestSuite {
    
}
