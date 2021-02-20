package team14.warzone.GameEngine;

import team14.warzone.Console.Console;

public class Driver {
    public static void main(String[] args) {
        GameEngine ge = new GameEngine();
        Console console = new Console(ge);

        System.out.println("Welcome to warzone game, please enter your command : ");
        console.readInput();
    }
}
