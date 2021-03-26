package team14.warzone.Console;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Class contains static methods that is used to check validity of user input
 *
 * @author Anagh Mehran
 * @version 1.0
 */
public class InputValidator {
    /**
     * Phase enum consists of MAPEDITOR, STARTUP, GAMEPLAY
     */
    public enum Phase {
        /**
         * constant MAPEDITOR
         */
        MAPEDITOR,
        /**
         * constant STARTUP
         */
        STARTUP,
        /**
         * constant GAMEPLAY
         */
        GAMEPLAY
    }

    /**
     * static variable CURRENT_PHASE of type Phase
     */
    public static Phase CURRENT_PHASE = Phase.MAPEDITOR;
    /**
     * valid gameplay commands arraylist
     */
    public static ArrayList<String> VALID_GAMEPLAY_COMMANDS = new ArrayList<>(
            Arrays.asList(
                    "deploy"
            )
    );

    /**
     * static variable VALID_MAPEDITOR_OPTIONS of type String
     */
    public static ArrayList<String> VALID_MAPEDITOR_OPTIONS = new ArrayList<>(
            Arrays.asList(
                    "-add",
                    "-remove"
            )
    );

    /**
     * Validates whether the user input is valid.
     * If the command is not valid it will print a error message to console.
     *
     * @param p_CommandName Name of the command entered
     * @param p_OptionName  Name of the option ("-add" or "-remove")
     * @param p_Arguments   List of arguments
     * @return true if the command is valid; otherwise return false
     */
    public static boolean validateInput(String p_CommandName, String p_OptionName, List<String> p_Arguments) {
        // switch to call method for each command
        switch (p_CommandName) {
            case "pass":
                return true;

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
                try {
                    return validateEditNeighbor(p_OptionName, p_Arguments);
                } catch (Exception e) {
                    System.out.println(e.getMessage());
                    return false;
                }

            case "savemap":
                try {
                    return validateSaveMap(p_Arguments);
                } catch (Exception e) {
                    System.out.println(e.getMessage());
                    return false;
                }

            case "editmap":
                try {
                    return validateEditMap(p_Arguments);
                } catch (Exception e) {
                    System.out.println(e.getMessage());
                    return false;
                }

            case "validatemap":
                try {
                    return validateMap(p_OptionName, p_Arguments);
                } catch (Exception e) {
                    System.out.println(e.getMessage());
                    return false;
                }

            case "loadmap":
                try {
                    return loadMap(p_OptionName, p_Arguments);
                } catch (Exception e) {
                    System.out.println(e.getMessage());
                    return false;
                }

            case "showmap":
                try {
                    return showMap(p_OptionName, p_Arguments);
                } catch (Exception e) {
                    System.out.println(e.getMessage());
                    return false;
                }

            case "showcards":
                try {
                    return showCards(p_OptionName, p_Arguments);
                } catch (Exception e) {
                    System.out.println(e.getMessage());
                    return false;
                }

            case "gameplayer":
                try {
                    return validateGamePlayer(p_OptionName, p_Arguments);
                } catch (Exception e) {
                    System.out.println(e.getMessage());
                    return false;
                }

            case "assigncountries":
                try {
                    return validateAssignCountries(p_OptionName, p_Arguments);
                } catch (Exception e) {
                    System.out.println(e.getMessage());
                    return false;
                }

            case "deploy":
                try {
                    return validateDeploy(p_OptionName, p_Arguments);
                } catch (Exception e) {
                    System.out.println(e.getMessage());
                    return false;
                }

            case "advance":
            case "airlift":
                try {
                    return validateAdvanceAirlift(p_OptionName, p_Arguments);
                } catch (Exception e) {
                    System.out.println(e.getMessage());
                    return false;
                }

            case "bomb":
            case "blockade":
            case "negotiate":
                try {
                    return validateBombBlockadeNegotiate(p_OptionName, p_Arguments);
                } catch (Exception e) {
                    System.out.println(e.getMessage());
                    return false;
                }

            default:
                System.out.println("Invalid command: " + p_CommandName);
                return false;
        }
    }

    /**
     * This method checks the validity of "editcontinent" command.
     *
     * @param p_OptionName Name of the option ("-add" or "-remove")
     * @param p_Arguments  List of arguments passed
     * @return true if all checks are passed
     * @throws Exception throws exception with error message
     */
    private static boolean validateEditContinent(String p_OptionName, List<String> p_Arguments) throws Exception {
        // Validate command for current gamephase
        gamePhaseCheck(Phase.MAPEDITOR);

        // Validate option name
        optionNameCheck(p_OptionName);

        // Validate -add arguments and -remove arguments
        if (p_OptionName.equals("-add")) {
            if (p_Arguments.size() != 2 || !isAlphaNumeric(p_Arguments.get(0)) || !isNumeric(p_Arguments.get(1)))
                throw new Exception("Invalid arguments");
        } else if (p_OptionName.equals("-remove")) {
            if (p_Arguments.size() != 1 || !isAlphaNumeric(p_Arguments.get(0)))
                throw new Exception("Invalid arguments");
        }
        return true;
    }

    /**
     * This method checks the validity of "editcountry" command.
     *
     * @param p_OptionName Name of the option ("-add" or "-remove")
     * @param p_Arguments  List of arguments passed
     * @return true if all checks are passed
     * @throws Exception throws exception with error message
     */
    private static boolean validateEditCountry(String p_OptionName, List<String> p_Arguments) throws Exception {
        // Validate command for current gamephase
        gamePhaseCheck(Phase.MAPEDITOR);

        // Validate option name
        optionNameCheck(p_OptionName);

        // Validate -add arguments and -remove arguments
        if (p_OptionName.equals("-add")) {
            if (p_Arguments.size() != 2 || !isAlphaNumeric(p_Arguments.get(0)) || !isAlphaNumeric(p_Arguments.get(1)))
                throw new Exception("Invalid arguments");

        } else if (p_OptionName.equals("-remove")) {
            if (p_Arguments.size() != 1 || !isAlphaNumeric(p_Arguments.get(0)))
                throw new Exception("Invalid arguments");
        }
        return true;
    }

    /**
     * This method checks the validity of "editneighbor" command
     *
     * @param p_OptionName Name of the option ("-add" or "-remove")
     * @param p_Arguments  List of arguments passed
     * @return true if all checks passes
     * @throws Exception throws exception with error message
     */
    private static boolean validateEditNeighbor(String p_OptionName, List<String> p_Arguments) throws Exception {
        // Validate command for current gamephase
        gamePhaseCheck(Phase.MAPEDITOR);

        // Validate option name
        optionNameCheck(p_OptionName);

        // Validate -add arguments and -remove arguments
        if (p_Arguments.size() != 2 || !isAlphaNumeric(p_Arguments.get(0)) || !isAlphaNumeric(p_Arguments.get(1)))
            throw new Exception("Invalid arguments");

        return true;
    }

    /**
     * This method checks the validity of "savemap" command
     *
     * @param p_Arguments List of arguments passed
     * @return true if all checks passes
     * @throws Exception throws exception with error message
     */
    private static boolean validateSaveMap(List<String> p_Arguments) throws Exception {
        // Validate command for current gamephase
        gamePhaseCheck(Phase.MAPEDITOR);

        // Validate -add arguments and -remove arguments
        if (p_Arguments.size() != 1)
            throw new Exception("Invalid arguments");

        // Validate if filename is alphanumeric
        if (!isAlphaNumeric(p_Arguments.get(0)))
            throw new Exception("Invalid filename: should be alphanumberic");

        return true;
    }

    /**
     * This method checks the validity of "editmap" command
     *
     * @param p_Arguments Contains the filename of the map to be edited
     * @return true if all checks pass
     * @throws Exception throws exception with error message
     */
    private static boolean validateEditMap(List<String> p_Arguments) throws Exception {
        // Validate command for current gamephase
        gamePhaseCheck(Phase.MAPEDITOR);

        // Validate -add arguments and -remove arguments
        if (p_Arguments.size() != 1)
            throw new Exception("Invalid arguments");

        return true;
    }

    /**
     * This method checks the validity of "validatemap" command
     *
     * @param p_OptionName Name of the option (Expect "noOption")
     * @param p_Arguments  Contains list of arguments to be passed. In this case this should be an empty list.
     * @return true if all checks pass
     * @throws Exception throws exception with error message
     */
    private static boolean validateMap(String p_OptionName, List<String> p_Arguments) throws Exception {
        // Validate command for current gamephase
        gamePhaseCheck(Phase.MAPEDITOR);

        // Validate no option was passed
        if (!p_OptionName.equals("noOption"))
            throw new Exception("Invalid option");

        // Validate no arguments were passed
        if (!p_Arguments.isEmpty())
            throw new Exception("Invalid arguments");

        return true;
    }

    /**
     * Method checks the validity of "loadMap" command
     *
     * @param p_OptionName Name of the option (Expect "noOption")
     * @param p_Arguments  Contains list of arguments to be passed. In this case this should be an empty list.
     * @return true if all checks pass
     * @throws Exception throws exception with error message
     */
    private static boolean loadMap(String p_OptionName, List<String> p_Arguments) throws Exception {
        // Validate command for current gamephase
        gamePhaseCheck(Phase.MAPEDITOR);

        // Validate -add arguments and -remove arguments
        if (p_Arguments.size() != 1)
            throw new Exception("Invalid arguments");

        return true;
    }

    /**
     * Method checks the validity of "showmap" command
     *
     * @param p_OptionName Name of the option (Expect "noOption")
     * @param p_Arguments  Contains list of arguments to be passed. In this case this should be an empty list.
     * @return true if all checks pass
     * @throws Exception throws exception with error message
     */
    private static boolean showMap(String p_OptionName, List<String> p_Arguments) throws Exception {
        // Validate no option was passed
        if (!p_OptionName.equals("noOption"))
            throw new Exception("Invalid option");

        // Validate no arguments were passed
        if (!p_Arguments.isEmpty())
            throw new Exception("Invalid arguments");

        return true;
    }

    /**
     * Method checks the validity of "showcards" command
     *
     * @param p_OptionName Name of the option (Expect "noOption")
     * @param p_Arguments  Contains list of arguments to be passed. In this case this should be an empty list.
     * @return true if all checks pass
     * @throws Exception throws exception with error message
     */
    private static boolean showCards(String p_OptionName, List<String> p_Arguments) throws Exception {
        // Validate no option was passed
        if (!p_OptionName.equals("noOption"))
            throw new Exception("Invalid option");

        // Validate no arguments were passed
        if (!p_Arguments.isEmpty())
            throw new Exception("Invalid arguments");

        return true;
    }

    /**
     * Method checks the validity of "gameplayer" command
     *
     * @param p_OptionName Name of the option ("-add" or "-remove")
     * @param p_Arguments  Name of the player
     * @return true if all checks pass
     * @throws Exception throws exception with error message
     */
    private static boolean validateGamePlayer(String p_OptionName, List<String> p_Arguments) throws Exception {
        // Validate command for current gamephase
        gamePhaseCheck(Phase.STARTUP);

        // Validate option name
        optionNameCheck(p_OptionName);

        // Validate -add arguments and -remove arguments
        if (p_Arguments.size() != 1 || !isAlphaNumeric(p_Arguments.get(0)))
            throw new Exception("Invalid arguments");

        return true;
    }

    /**
     * Method checks the validity of "assigncountries" command
     *
     * @param p_OptionName Name of the option (Expected "noOption")
     * @param p_Arguments  Arguments passed (Should be empty list)
     * @return true if all checks pass
     * @throws Exception throws exception with error message
     */
    private static boolean validateAssignCountries(String p_OptionName, List<String> p_Arguments) throws Exception {
        // Validate command for current gamephase
        gamePhaseCheck(Phase.STARTUP);

        // Validate no option was passed
        if (!p_OptionName.equals("noOption"))
            throw new Exception("Invalid option");

        // Validate no arguments were passed
        if (!p_Arguments.isEmpty())
            throw new Exception("Invalid arguments");

        return true;
    }

    /**
     * Method checks the validity of "deploy" command
     *
     * @param p_OptionName Name of the option ("noOption")
     * @param p_Arguments  number of armies to deploy
     * @return true if all checks pass
     * @throws Exception throws exception with error message
     */
    private static boolean validateDeploy(String p_OptionName, List<String> p_Arguments) throws Exception {
        // Validate command for current gamephase
        gamePhaseCheck(Phase.GAMEPLAY);

        // Validate no option was passed
        if (!p_OptionName.equals("noOption"))
            throw new Exception("Invalid option");

        // Validate arguments
        if (p_Arguments.size() != 2 || !isAlphaNumeric(p_Arguments.get(0)) || !isNumeric(p_Arguments.get(1)))
            throw new Exception("Invalid arguments");

        return true;
    }

    /**
     * Method checks the validity of the following commands:
     * "advance"
     * "airlift"
     *
     * @param p_OptionName Name of the option ("noOption")
     * @param p_Arguments  number of armies to deploy
     * @return true if all checks pass
     * @throws Exception throws exception with error message
     */
    private static boolean validateAdvanceAirlift(String p_OptionName, List<String> p_Arguments) throws Exception {
        // Validate command for current gamephase
        gamePhaseCheck(Phase.GAMEPLAY);

        // Validate no option was passed
        if (!p_OptionName.equals("noOption"))
            throw new Exception("Invalid option");

        // Validate arguments
        if (p_Arguments.size() != 3 || !isAlphaNumeric(p_Arguments.get(0)) || !isAlphaNumeric(p_Arguments.get(1))
                || !isNumeric(p_Arguments.get(2)))
            throw new Exception("Invalid arguments");

        return true;
    }

    /**
     * Method checks the validity of the following commands:
     * "bomb"
     * "blockade"
     * "negotiate"
     *
     * @param p_OptionName Name of the option ("noOption")
     * @param p_Arguments  number of armies to deploy
     * @return true if all checks pass
     * @throws Exception throws exception with error message
     */
    private static boolean validateBombBlockadeNegotiate(String p_OptionName, List<String> p_Arguments) throws Exception {
        // Validate command for current gamephase
        gamePhaseCheck(Phase.GAMEPLAY);

        // Validate no option was passed
        if (!p_OptionName.equals("noOption"))
            throw new Exception("Invalid option");

        // Validate arguments
        if (p_Arguments.size() != 1 || !isAlphaNumeric(p_Arguments.get(0)))
            throw new Exception("Invalid arguments");

        return true;
    }

    /**
     * This method checks the current gamephase with the expected phase
     *
     * @param p_ExpectedPhase Expected phase that should be compared with current phase
     * @throws Exception throws exception with error message when the current gamephase does not match with expected
     *                   phase
     */
    private static void gamePhaseCheck(Phase p_ExpectedPhase) throws Exception {
        if (CURRENT_PHASE != p_ExpectedPhase) {
            throw new Exception("Command not valid in current phase");
        }
    }

    /**
     * This methods checks that the option name is valid. Finds the option name from the list of valid options
     *
     * @param p_OptionName name of the option
     * @throws Exception throws exception with error message when option name is not valid
     */
    private static void optionNameCheck(String p_OptionName) throws Exception {
        if (!VALID_MAPEDITOR_OPTIONS.contains(p_OptionName)) {
            throw new Exception("Invalid option: " + p_OptionName);
        }
    }

    /**
     * Method checks if the string passed contains only numeric characters
     *
     * @param p_StrNum string to be checked
     * @return true if the string contains only numeric characters; else return false
     */
    private static boolean isNumeric(String p_StrNum) {
        if (p_StrNum == null) {
            return false;
        }
        try {
            double d = Double.parseDouble(p_StrNum);
        } catch (NumberFormatException nfe) {
            return false;
        }
        return true;
    }

    /**
     * Method checks if the string passed contains only alphanumeric characters
     *
     * @param p_Str string to be checked
     * @return true if the string contains only alphanumeric characters; else retur false
     */
    private static boolean isAlphaNumeric(String p_Str) {
        return p_Str != null && p_Str.matches("^[a-zA-Z0-9.]*$");
    }

    /**
     * Method sets the current phase
     *
     * @param p_CurrentPhase current phase to be set
     */
    public static void setCurrentPhase(String p_CurrentPhase) {
        CURRENT_PHASE = Phase.valueOf(p_CurrentPhase);
    }
}
