package team14.warzone.GameEngine;

import team14.warzone.Console.Console;

public class Driver {
    public static void main(String[] args) {
        Console l_Console = new Console();
        GameEngine l_GE = new GameEngine(l_Console);

        System.out.println("Welcome to warzone game, please enter your command : ");
        while (true) {
            l_Console.readInput();
        }
    }
}
