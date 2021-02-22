package team14.warzone.GameEngine;

import team14.warzone.Console.Console;
import team14.warzone.Console.InputValidator;
import team14.warzone.MapModule.MapEditor;

public class Driver {
    public static void main(String[] args) {
        Console l_Console = new Console();
        MapEditor l_ME = new MapEditor();
        GameEngine l_GE = new GameEngine(l_Console, l_ME);

        System.out.println("Welcome to warzone game, please enter your command : ");
        while (InputValidator.CURRENT_PHASE != InputValidator.Phase.GAMEPLAY) {
            l_Console.readInput();
            l_Console.filterCommand(l_GE, l_ME);
        }
        while (true) {
            l_GE.gameLoop();
        }
    }
}
