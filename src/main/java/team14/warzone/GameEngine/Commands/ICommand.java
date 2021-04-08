package team14.warzone.GameEngine.Commands;

import java.io.Serializable;

/**
 * A general interface for commands
 */
public interface ICommand extends Serializable {
    /**
     * Method to execute the command
     * @throws Exception throws exception
     */
    void execute() throws Exception;
}
