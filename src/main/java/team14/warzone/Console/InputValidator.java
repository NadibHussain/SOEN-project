package team14.warzone.Console;

import java.util.ArrayList;
import java.util.Arrays;

public class InputValidator {
    public static enum Phase {
        MAPEDITOR,
        STARTUP,
        GAMEPLAY
    }

    public static Phase d_CurrentPhase;
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

    public boolean validateInput(Command p_Command) throws Exception {
        // switch to call method for each command
        switch (p_Command.getCommandType()) {
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
                throw new Exception("Invalid command: " + p_Command.getCommandType());
        }

        return true;
    }

    private boolean validateEditContinent(Command p_Command) {
        return true;
    }
}
