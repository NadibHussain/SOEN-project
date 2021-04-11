package team14.warzone.GameEngine;

import team14.warzone.Console.Console;
import team14.warzone.MapModule.MapEditorDomination;

/**
 * This is a driver class for the game engine
 */
public class Driver {
    /**
     * Main class to run driver
     *
     * @param args main args
     */
    public static void main(String[] args) {
        Console l_Console = new Console();
        MapEditorDomination l_ME = new MapEditorDomination();
        GameEngine l_GE = new GameEngine(l_Console, l_ME);

        System.out.println("Welcome to warzone game!");
        l_GE.getD_LogEntryBuffer().setD_log("Warzone game has started");
        l_GE.getD_LogEntryBuffer().notifyObservers(l_GE.getD_LogEntryBuffer());
        l_GE.gameLoop();
    }
}
