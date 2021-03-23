package team14.warzone.Console;

import team14.warzone.GameEngine.Commands.AdminCommands;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * This class is used to read commands from user console, validate it then send the commands to the appropriate classes
 */
public class Console {
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
//                System.out.println("valid command");
//                Option l_opt = new Option();
//                Command l_UserCommand = new Command(l_Keyword, l_opt);
//                setD_CommandBuffer(l_UserCommand);
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
//                System.out.println("valid command");
//                //Create Command object, passing keyword and option
//                Option l_opt = new Option();
//                l_opt.addArgument(l_Arguments.get(0));
//                Command l_UserCommand = new Command(l_Keyword, l_opt);
//                setD_CommandBuffer(l_UserCommand);
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
//                    System.out.println("valid command");
                    //Set options for the user command
                    ArrayList<String> l_TempArgs = new ArrayList<>();
                    l_TempArgs.addAll(l_Arguments);
//                    Option l_opt = new Option(l_OptName, l_TempArgs);
//                    //Create Command object, passing keyword and option
//                    Command l_UserCommand = new Command(l_Keyword, l_opt);
//                    setD_CommandBuffer(l_UserCommand);
                    List<String> l_Command = new ArrayList<>(); // to store command information
                    l_Command.add(l_Keyword);
                    l_Command.add(l_OptName);
                    l_Command.add(String.join(",", l_TempArgs));
                    l_Commands_list.add(l_Command);
                }
            }
        }
//        System.out.println(Arrays.deepToString(l_Commands_list.toArray()));
        return l_Commands_list;
    }

    /**
     * A method to filter user commands depending on the current game phase
     *
     * @param p_GameEngine GameEngine param
     * @param p_MapEditor  MapEditor param
     */
//    public void filterCommand(GameEngine p_GameEngine, MapEditor p_MapEditor) {
//        if (!d_CommandBuffer.isEmpty()) {
//            for (int l_I = 0; l_I < d_CommandBuffer.size(); l_I++) {
//                d_CommandBuffer.get(l_I).setD_GameEngine(p_GameEngine);
//                d_CommandBuffer.get(l_I).setD_MapEditor(p_MapEditor);
//                if (d_CommandBuffer.get(l_I).getD_Keyword().equals("showmap")) {
//                    d_CommandBuffer.get(l_I).execute();
//                } else {
//                    switch (InputValidator.CURRENT_PHASE) {
//                        case MAPEDITOR:
//                        case STARTUP:
//                            d_CommandBuffer.get(l_I).execute();
//                            break;
//
//                        case GAMEPLAY:
//                            p_GameEngine.receiveCommand(d_CommandBuffer.get(l_I));
//                            break;
//                    }
//                }
//            }
//            clearCommandBuffer();
//        }
//    }

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
