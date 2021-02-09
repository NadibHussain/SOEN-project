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

    public boolean validateInput() throws Exception {
        // check if p_InputMsg[0] exists in VALID_ORDER_LIST, if not throw exception

        // switch to call method for each order

        return true;
    }
}
