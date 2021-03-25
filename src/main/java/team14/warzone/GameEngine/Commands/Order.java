package team14.warzone.GameEngine.Commands;

import team14.warzone.GameEngine.GameEngine;

/**
 * This class represents orders issued by players during game issue order phase
 * It also represents the "Command class" in command pattern
 */
public abstract class Order implements ICommand {
    /**
     * field stores option object
     */
    Option d_Option;

    /**
     * field stores instance of the game engine
     */
    GameEngine d_GameEngine;

    /**
     * Method to execute the command
     */
    abstract public void execute() throws Exception;

    /**
     * Reset method
     */
    public abstract void reset();
}
