package team14.warzone.GameEngine.Commands;

/**
 * A general interface for commands
 */
public interface ICommand {
    /**
     * Method to execute the command
     * @throws Exception throws exception
     */
    void execute() throws Exception;
}
