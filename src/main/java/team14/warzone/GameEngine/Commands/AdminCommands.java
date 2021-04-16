package team14.warzone.GameEngine.Commands;

import team14.warzone.GameEngine.GameEngine;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * This class is used to create objects representing the commands entered by user in the command line
 *
 * @author zeina
 * @author Anagh
 * @version 1.0
 */
public class AdminCommands implements ICommand, Serializable {
    /**
     * field stores keyword for the command
     */
    private String d_Keyword;
    /**
     * field stores option object
     */
    private Option d_Option;
    /**
     * field stores instance of the game engine
     */
    private GameEngine d_GameEngine;
    /**
     * valid admin commands arraylist
     */
    public static ArrayList<String> VALID_ADMIN_COMMANDS = new ArrayList<>(
            Arrays.asList(
                    "showmap",
                    "showcards",
                    "exit",
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
                    "tournament",
                    "savegame",
                    "loadgame"
            )
    );

    /**
     * Class constructor, specifying keyword and options
     *
     * @param p_Keyword : keyword of the command
     * @param p_Options : options of the command, if any
     */
    public AdminCommands(String p_Keyword, Option p_Options) {
        this.d_Keyword = p_Keyword;
        this.d_Option = p_Options;
    }

    /**
     * AdminCommands constructor
     *
     * @param p_Keyword Keyword param
     * @param p_Options Options param
     * @param p_GE      GameEngine param
     */
    public AdminCommands(String p_Keyword, Option p_Options, GameEngine p_GE) {
        this.d_Keyword = p_Keyword;
        this.d_Option = p_Options;
        d_GameEngine = p_GE;
    }

    /**
     * Method to execute the command object by calling the corresponding methods from
     * Map, MapEditor or the GameEngine classes
     */
    public void execute() {
        List<String> l_CommandArgs = this.getD_Options().getD_Arguments();
        String l_OptionName = this.getD_Options().getD_Name();

        switch (this.getD_Keyword()) {
            case "editcontinent":
                if (l_OptionName.equals("-add"))
                    d_GameEngine.getD_CurrentPhase().addContinent(reformatName(l_CommandArgs.get(0)),
                            Integer.parseInt(l_CommandArgs.get(1)));
                else //-remove option
                    d_GameEngine.getD_CurrentPhase().removeContinent(reformatName(l_CommandArgs.get(0)));
                break;

            case "editcountry":
                if (l_OptionName.equals("-add"))
                    d_GameEngine.getD_CurrentPhase().addCountry(reformatName(l_CommandArgs.get(0)),
                            reformatName(l_CommandArgs.get(1)));
                else //-remove option
                    d_GameEngine.getD_CurrentPhase().removeCountry(reformatName(l_CommandArgs.get(0)));
                break;

            case "editneighbor":
                if (l_OptionName.equals("-add"))
                    d_GameEngine.getD_CurrentPhase().addNeighbor(reformatName(l_CommandArgs.get(0)),
                            reformatName(l_CommandArgs.get(1)));
                else //-remove option
                    d_GameEngine.getD_CurrentPhase().removeNeighbor(reformatName(l_CommandArgs.get(0)),
                            reformatName(l_CommandArgs.get(1)));
                break;

            case "savemap":
                d_GameEngine.getD_CurrentPhase().saveMap(l_CommandArgs.get(0), l_CommandArgs.get(1));
                break;

            case "savegame":
                d_GameEngine.getD_CurrentPhase().saveGame(l_CommandArgs.get(0));
                break;

            case "loadgame":
                d_GameEngine.loadGame(l_CommandArgs.get(0));
                break;

            case "editmap":
                d_GameEngine.getD_CurrentPhase().editMap(l_CommandArgs.get(0));
                break;

            case "validatemap":
                d_GameEngine.getD_CurrentPhase().validateMap(d_GameEngine.getD_MapEditor().getD_LoadedMap());
                break;

            case "loadmap":
                d_GameEngine.getD_CurrentPhase().loadMap(l_CommandArgs.get(0));
                break;

            case "showmap":
                d_GameEngine.getD_CurrentPhase().showMap();
                break;

            case "showcards":
                d_GameEngine.getD_CurrentPhase().showCards();
                break;

            case "gameplayer":
                if (l_OptionName.equals("-add"))
                    d_GameEngine.getD_CurrentPhase().addPlayer(l_CommandArgs.get(0), l_CommandArgs.get(1));
                else //-remove option
                    d_GameEngine.getD_CurrentPhase().removePlayer(l_CommandArgs.get(0));
                break;

            case "assigncountries":
                d_GameEngine.getD_CurrentPhase().assignCountries();
                break;

            case "tournament":
                d_GameEngine.setD_CurrentPhase(d_GameEngine.getD_TournamentModePhase());
                if (l_OptionName.equals("-M") || l_OptionName.equals("-m"))
                    d_GameEngine.getD_CurrentPhase().tournamentAddMaps(l_CommandArgs);
                else if (l_OptionName.equals("-P") || l_OptionName.equals("-p"))
                    d_GameEngine.getD_CurrentPhase().tournamentAddPlayersStrategies(l_CommandArgs);
                else if (l_OptionName.equals("-G") || l_OptionName.equals("-g"))
                    d_GameEngine.getD_CurrentPhase().tournamentNumOfGames(l_CommandArgs.get(0));
                else if (l_OptionName.equals("-D") || l_OptionName.equals("-d"))
                    d_GameEngine.getD_CurrentPhase().tournamentMaxNumOfTurns(l_CommandArgs.get(0));
                break;
        }
    }

    /**
     * A method to get the keyword related to the current command
     *
     * @return String : the keyword of the command
     */
    public String getD_Keyword() {
        return d_Keyword;
    }

    /**
     * A method to set the keyword related to the current command
     *
     * @param p_Keyword : keyword of the command
     */
    public void setD_Keyword(String p_Keyword) {
        this.d_Keyword = p_Keyword;
    }

    /**
     * A method to set the GameEngine
     *
     * @param p_GameEngine GameEngine param
     */
    public void setD_GameEngine(GameEngine p_GameEngine) {
        d_GameEngine = p_GameEngine;
    }

    /**
     * A method to get a list of options related to the current command, if any
     *
     * @return d_Options : options list of the command
     */
    public Option getD_Options() {
        return d_Option;
    }

    /**
     * A method to set the options related to the current command
     *
     * @param p_Option : options list of the command
     */
    public void setD_Options(Option p_Option) {
        this.d_Option = p_Option;
    }

    /**
     * A method replace "_" with space in country and continent name
     *
     * @param p_Name name of format
     * @return reformatted name
     */
    public String reformatName(String p_Name) {
        return p_Name.replace('_', ' ');
    }

    /**
     * @return String
     */
    @Override
    public String toString() {
        return "Command{" +
                "d_Keyword='" + d_Keyword + '\'' + d_Option.getD_Name() +
                ", d_Options=" + d_Option.getD_Arguments() +
                '}';
    }
}
