package team14.warzone.Console;

import team14.warzone.GameEngine.Commands.AdminCommands;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * This class is used to read commands from user console, validate it then send the commands to the appropriate classes
 */
public class Console implements Serializable {
    /**
     * A scanner to read user input
     */
    static Scanner d_Scanner = new Scanner(System.in);
    /**
     * temporarily store user commands
     */
    private List<AdminCommands> d_AdminCommandsBuffer;

    /**
     * Default constructor
     */
    public Console() {
        d_AdminCommandsBuffer = new ArrayList<>();
    }

    /**
     * A method to read input from user console
     *
     * @return returns commands list
     */
    public static List<List<String>> readInput() {
        String[] l_UserInput = d_Scanner.nextLine().split(" ");
        //if user wants to exit the game
        if (l_UserInput[0].equals("exit"))
            System.exit(0);
        //define keywords and argument for the command
        String l_Keyword = l_UserInput[0];
        String l_OptName = ""; //to store command option
        List<String> l_Arguments = new ArrayList<>();
        boolean l_ValidCommand = true;// to store the command validity
        List<List<String>> l_Commands_list = new ArrayList<>();// array list to store commands information
        //check one word command : a keyword only, no options or arguments
        if (l_UserInput.length == 1) {
            if (InputValidator.validateInput(l_Keyword, "noOption", l_Arguments)) {
                List<String> l_Command = new ArrayList<>(); // to store command information
                l_Command.add(l_Keyword);
                l_Command.add("");
                l_Command.add("");
                l_Commands_list.add(l_Command);//add command to command list
            }
        }
        //check two words command : : a keyword with an argument, no options
        else if (l_UserInput.length == 2) {
            l_Arguments.add(l_UserInput[1]);
            if (InputValidator.validateInput(l_Keyword, "noOption", l_Arguments)) {
                List<String> l_Command = new ArrayList<>(); // to store command information
                l_Command.add(l_Keyword);
                l_Command.add("");
                l_Command.add(l_Arguments.get(0));
                l_Commands_list.add(l_Command);//add command to command list
            }
        } else { //check three words or more command
            for (int i = 1; i < l_UserInput.length; i++) {
                if (l_UserInput[i].charAt(0) == '-') { //check if a word is an option
                    l_Arguments.clear();//reset arguments list
                    l_OptName = l_UserInput[i];
                    //store all arguments for that option
                    while ((i + 1) < l_UserInput.length && l_UserInput[i + 1].charAt(0) != '-') {
                        l_Arguments.add(l_UserInput[i + 1]);
                        i++;
                    }
                } else {
                    l_OptName = "noOption";
                    //if the word is not an option then add it to the arguments list
                    while (i < l_UserInput.length) {
                        l_Arguments.add(l_UserInput[i]);
                        i++;
                    }
                }
                //check command validity
                l_ValidCommand = InputValidator.validateInput(l_Keyword, l_OptName, l_Arguments);
                if (l_ValidCommand) {
                    ArrayList<String> l_TempArgs = new ArrayList<>();
                    l_TempArgs.addAll(l_Arguments);
                    List<String> l_Command = new ArrayList<>(); // to store command information
                    l_Command.add(l_Keyword);
                    l_Command.add(l_OptName);
                    l_Command.add(String.join(",", l_TempArgs));
                    l_Commands_list.add(l_Command);
                }
            }
        }
        return l_Commands_list;
    }

    /**
     * Method clears the command buffer
     */
    public void clearCommandBuffer() {
        d_AdminCommandsBuffer.clear();
    }

    /**
     * A method to store user Command object
     *
     * @param p_AdminCommands user Command object
     */
    public void setD_CommandBuffer(AdminCommands p_AdminCommands) {
        this.d_AdminCommandsBuffer.add(p_AdminCommands);
    }

    /**
     * Getter method to get the command stored in buffer
     *
     * @return command object stored in buffer
     */
    public AdminCommands getD_CommandBuffer() {
        return d_AdminCommandsBuffer.get(0);
    }

    /**
     * Getter method to get list of commands stored in buffer
     *
     * @return list of command objects
     */
    public List<AdminCommands> get_BufferCommands() {
        return d_AdminCommandsBuffer;
    }

    /**
     * A method to display a message to the user
     *
     * @param p_Msg the message to be displayed
     */
    public static void displayMsg(String p_Msg) {
        System.out.println(p_Msg);
    }
}
