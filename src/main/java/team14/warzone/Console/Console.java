package team14.warzone.Console;

import java.util.Arrays;
import java.util.Scanner;

public class Console {

    public static void main(String[] p_Args) throws Exception {
        System.out.println("Welcome to warzone game, please enter your command : ");
        readInput();
    }

    public static void readInput() throws Exception {
        Scanner l_Scanner = new Scanner(System.in);
        InputValidator l_InputValidator = new InputValidator();
        while (true) {
            String[] l_UserInput = l_Scanner.nextLine().split(" ");
            Command l_UserCommand = new Command();
            if (l_UserInput[0].equals("exit"))
                break;
            l_UserCommand.setD_Keyword(l_UserInput[0]);
            if (l_UserInput.length == 2) {
                Option l_Opt = new Option("", Arrays.asList(l_UserInput[1]));
                l_UserCommand.setD_Options(Arrays.asList(l_Opt));
            } else {
                for (int i = 1; i < l_UserInput.length; i++) {
                    Option l_Opt = new Option();
                    if (l_UserInput[i].charAt(0) == '-') {
                        l_Opt.setD_Name(l_UserInput[i]);
                        while ((i + 1) < l_UserInput.length && l_UserInput[i + 1].charAt(0) != '-') {
                            l_Opt.addArgument(l_UserInput[i + 1]);
                            i++;
                        }
                    } else {
                        while (i < l_UserInput.length) {
                            l_Opt.addArgument(l_UserInput[i]);
                            i++;
                        }
                    }
                    l_UserCommand.addOption(l_Opt);
                }
            }
            System.out.println(l_UserCommand);
            if(l_InputValidator.validateInput(l_UserCommand)){
                System.out.println("command received");
                filterCommand(l_UserCommand);
            }
        }
    }

    public static void filterCommand(Command p_UserCommand){

    }
}
