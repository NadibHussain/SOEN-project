package team14.warzone.GameEngine;

import team14.warzone.Console.Console;

public class Driver {
    public static void main(String[] args) {
        Console console = new Console();

        System.out.println("Welcome to warzone game, please enter your command : ");
        console.readInput();
    }
}
