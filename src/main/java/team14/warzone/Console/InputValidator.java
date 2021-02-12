package team14.warzone.Console;

import java.util.ArrayList;
import java.util.Arrays;

public class InputValidator {
    public enum Phase {
        MAPEDITOR,
        STARTUP,
        GAMEPLAY
    }

    public static Phase CURRENT_PHASE;
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

    public boolean validateInput(Command p_Command) throws Exception {
        // switch to call method for each command
        switch (p_Command.getD_Keyword()) {
            case "editcontinent":
                // method to validate editcontinent
                return validateEditContinent(p_Command);

            case "editcountry":
                // method
                break;

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
                throw new Exception("Invalid command: " + p_Command.getD_Keyword());
        }

        return true;
    }

    private boolean validateEditContinent(Command p_Command) {
        // editcontinent -add continentID continentvalie -remove continentID
        // check CURRENT_PHASE

        // validate list of options
            // p_Command.d_Options.forEach
                // validate option.d_Name exists in VALID_MAPEDITOR_OPTIONS
                // ADD: validate 2 arguments and value not null
                // REMOVE: validate 1 arg and continent exists
        return true;
    }
}
