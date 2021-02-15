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

    public static Phase CURRENT_PHASE = Phase.STARTUP;
    public static ArrayList<String> VALID_MAPEDITOR_OPTIONS = new ArrayList<>(
            Arrays.asList(
                    "-add",
                    "-remove"
            )
    );

    public static boolean validateInput(String p_CommandName, String p_OptionName, List<String> p_Arguments) {
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

            default:
                System.out.println("Invalid command: " + p_CommandName);
                return false;
        }
    }

    private static boolean validateEditContinent(String p_OptionName, List<String> p_Arguments) throws Exception {
        // Validate command for current gamephase
        gamePhaseCheck(Phase.MAPEDITOR);

        // Validate option name
        optionNameCheck(p_OptionName);

        // Validate -add arguments and -remove arguments
        if (p_OptionName.equals("-add")) {
            if (p_Arguments.size() != 2 || !isNumeric(p_Arguments.get(0)))
                throw new Exception("Invalid arguments");
        } else if (p_OptionName.equals("-remove")) {
            if (p_Arguments.size() != 1 || !isNumeric(p_Arguments.get(0)))
                throw new Exception("Invalid arguments");
        }
        return true;
    }

    private static boolean validateEditCountry(String p_OptionName, List<String> p_Arguments) throws Exception {
        // Validate command for current gamephase
        gamePhaseCheck(Phase.MAPEDITOR);

        // Validate option name
        optionNameCheck(p_OptionName);

        // Validate -add arguments and -remove arguments
        if (p_OptionName.equals("-add")) {
            if (p_Arguments.size() != 2 || !isNumeric(p_Arguments.get(0)) || !isNumeric(p_Arguments.get(1)))
                throw new Exception("Invalid arguments");

        } else if (p_OptionName.equals("-remove")) {
            if (p_Arguments.size() != 1 || !isNumeric(p_Arguments.get(0)))
                throw new Exception("Invalid arguments");
        }
        return true;
    }

    private static boolean validateEditNeighbor(String p_OptionName, List<String> p_Arguments) throws Exception {
        // Validate command for current gamephase
        gamePhaseCheck(Phase.MAPEDITOR);

        // Validate option name
        optionNameCheck(p_OptionName);

        // Validate -add arguments and -remove arguments
        if (p_Arguments.size() != 2 || !isNumeric(p_Arguments.get(0)) || !isNumeric(p_Arguments.get(1)))
            throw new Exception("Invalid arguments");

        return true;
    }

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

    private static boolean validateEditMap(List<String> p_Arguments) throws Exception {
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

    private static boolean loadMap(String p_OptionName, List<String> p_Arguments) throws Exception {
        // Validate command for current gamephase
        gamePhaseCheck(Phase.MAPEDITOR);

        // Validate -add arguments and -remove arguments
        if (p_Arguments.size() != 1)
            throw new Exception("Invalid arguments");

        return true;
    }

    private static boolean showMap(String p_OptionName, List<String> p_Arguments) throws Exception {
        // Validate no option was passed
        if (!p_OptionName.equals("noOption"))
            throw new Exception("Invalid option");

        // Validate no arguments were passed
        if (!p_Arguments.isEmpty())
            throw new Exception("Invalid arguments");

        return true;
    }

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

    private static boolean validateDeploy(String p_OptionName, List<String> p_Arguments) throws Exception {
        // Validate command for current gamephase
        gamePhaseCheck(Phase.GAMEPLAY);

        // Validate no option was passed
        if (!p_OptionName.equals("noOption"))
            throw new Exception("Invalid option");

        // Validate arguments
        if (p_Arguments.size() != 2 || !isNumeric(p_Arguments.get(0)) || !isNumeric(p_Arguments.get(1)))
            throw new Exception("Invalid arguments");

        return true;
    }

    private static void gamePhaseCheck(Phase p_ExpectedPhase) throws Exception {
        if (CURRENT_PHASE != p_ExpectedPhase) {
            throw new Exception("Command not valid in current phase");
        }
    }

    private static void optionNameCheck(String p_OptionName) throws Exception {
        if (!VALID_MAPEDITOR_OPTIONS.contains(p_OptionName)) {
            throw new Exception("Invalid option: " + p_OptionName);
        }
    }

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

    private static boolean isAlphaNumeric(String p_Str) {
        return p_Str != null && p_Str.matches("^[a-zA-Z0-9]*$");
    }
}
