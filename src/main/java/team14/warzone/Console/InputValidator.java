package team14.warzone.Console;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class InputValidator {
    public enum Phase {
        MAPEDITOR,
        STARTUP,
        GAMEPLAY
    }

    public static Phase CURRENT_PHASE = Phase.MAPEDITOR;
    private static ArrayList<String> VALID_ORDER_LIST = new ArrayList<>(
            Arrays.asList(
                    "editcontinent",
                    "editcountry",
                    "editneighbor",
                    "savemap",
                    "editmap",
                    "validatemap",
                    "loadmap",
                    "showmap",
                    "gameplayer",
                    "assigncountries",
                    "deploy"
            )
    );
    public static ArrayList<String> VALID_MAPEDITOR_OPTIONS = new ArrayList<>(
            Arrays.asList(
                    "-add",
                    "-remove"
            )
    );

    public boolean validateInput(String p_CommandName, String p_OptionName, List<String> p_Arguments) throws Exception {
        // switch to call method for each command
        switch (p_CommandName) {
            case "editcontinent":
                try {
                    return validateEditContinent(p_OptionName, p_Arguments);
                } catch (Exception e) {
                    System.out.println(e.getMessage());
                    return false;
                }

            case "editcountry":
                try {
                    return validateEditCountry(p_OptionName, p_Arguments);
                } catch (Exception e) {
                    System.out.println(e.getMessage());
                    return false;
                }

            case "editneighbor":
                // method
                break;

            case "savemap":
                // method
                break;

            case "editmap":
                // method
                break;

            case "validatemap":
                //method
                break;

            case "loadmap":
                // method
                break;

            case "showmap":
                // method
                break;

            case "gameplayer":
                // method
                break;

            case "assigncountries":
                // method
                break;

            case "deploy":
                // method
                break;

            default:
                throw new Exception("Invalid command: " + p_CommandName);
        }

        return true;
    }

    private boolean validateEditContinent(String p_OptionName, List<String> p_Arguments) throws Exception {
        // Validate command for current gamephase
        gamePhaseCheck(Phase.MAPEDITOR);

        // Validate option name
        optionNameCheck(p_OptionName);

        // Validate -add arguments and -remove arguments
        if (p_OptionName.equals("-add")) {
            if (p_Arguments.size() != 2)
                throw new Exception("Invalid arguments");
        } else if (p_OptionName.equals("-remove")) {
            if (p_Arguments.size() != 1)
                throw new Exception("Invalid arguments");
        }
        return true;
    }

    private boolean validateEditCountry(String p_OptionName, List<String> p_Arguments) throws Exception {
        // Validate command for current gamephase
        gamePhaseCheck(Phase.MAPEDITOR);

        // Validate option name
        optionNameCheck(p_OptionName);

        // Validate -add arguments and -remove arguments
        if (p_OptionName.equals("-add")) {
            if (p_Arguments.size() != 2)
                throw new Exception("Invalid arguments");
        } else if (p_OptionName.equals("-remove")) {
            if (p_Arguments.size() != 1)
                throw new Exception("Invalid arguments");
        }
        return true;
    }

    private void gamePhaseCheck(Phase p_ExpectedPhase) throws Exception {
        if (CURRENT_PHASE != p_ExpectedPhase) {
            throw new Exception("Command not valid in current phase");
        }
    }

    private void optionNameCheck(String p_OptionName) throws Exception {
        if (!VALID_MAPEDITOR_OPTIONS.contains(p_OptionName)) {
            throw new Exception("Invalid option: " + p_OptionName);
        }
    }
}
