package team14.warzone.GameEngine;

import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.DisplayName;
import team14.warzone.Console.Console;
import team14.warzone.Console.InputValidator;
import team14.warzone.GameEngine.Observer.LogerOberver;
import team14.warzone.MapModule.MapEditor;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ObserverTest {

    GameEngine d_GE;
    /**
     * Method initializes the context under which test should run
     */
    @Before
    public void setup() {
        // object instantiation
        LogerOberver d_LogerObserver = new LogerOberver();
        d_GE = new GameEngine(new Console(), new MapEditor());
        d_GE.attach(d_LogerObserver);
    }

    /**
     * Checking if the  observer can detect country-list changes of game engine
     */
    @Test
    @DisplayName("Testing Armies Reinforcement")
    public void testCountryChange() {
     d_GE.loadMap("bigeurope.map");
     d_GE.notifyObservers(d_GE);
     d_GE.getD_LoadedMap().addCountry("Bangladesh","Britian");
     d_GE.notifyObservers(d_GE);
    }
}
