package team14.warzone.GameEngine.Commands;

import team14.warzone.GameEngine.GameEngine;

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

    public abstract void reset();
}
