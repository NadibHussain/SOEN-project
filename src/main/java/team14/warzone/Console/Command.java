package team14.warzone.Console;

import team14.warzone.GameEngine.GameEngine;
import team14.warzone.MapModule.Map;
import team14.warzone.MapModule.MapEditor;

import java.util.List;

/**
 * This class is used to create objects representing the commands entered by user in the command line
 *
 * @author zeina
 * @version 1.0
 */
public class Command {
    private String d_Keyword;
    private Option d_Option = new Option();

    /**
     * Class default constructor
     */
    public Command() {
    }

    /**
     * Class constructor, specifying keyword and options
     *
     * @param p_Keyword : keyword of the command
     * @param p_Options : options of the command, if any
     */
    public Command(String p_Keyword, Option p_Options) {
        this.d_Keyword = p_Keyword;
        this.d_Option = p_Options;
    }

    /**
     * Method to execute the command object by calling the corresponding methods from
     * Map, MapEditor or the GameEngine classes
     */
    public void execute() {
        List<String> l_CommandArgs = this.getD_Options().getD_Arguments();
        String l_optionName = this.getD_Options().getD_Name();
        MapEditor l_MapEditor = new MapEditor();
        Map l_Map = new Map();
        GameEngine l_gameEng = new GameEngine();

        switch (this.getD_Keyword()) {
            case "editcontinent":
                if (l_optionName.equals("-add"))
                    l_MapEditor.getD_loadedMap().addContinent(l_CommandArgs.get(0), Integer.parseInt(l_CommandArgs.get(1)));
                else //-remove option
                    l_MapEditor.getD_loadedMap().removeContinent(l_CommandArgs.get(0));
            case "editcountry":
                if (l_optionName.equals("-add"))
                    l_MapEditor.getD_loadedMap().addCountry(l_CommandArgs.get(0), l_CommandArgs.get(1));
                else //-remove option
                    l_MapEditor.getD_loadedMap().removeCountry(l_CommandArgs.get(0));
            case "editneighbor":
                if (l_optionName.equals("-add"))
                    l_MapEditor.getD_loadedMap().addNeighbour(l_CommandArgs.get(0), l_CommandArgs.get(1));
                else //-remove option
                    l_MapEditor.getD_loadedMap().removeNeighbour(l_CommandArgs.get(0), l_CommandArgs.get(1));
            case "savemap":
                l_MapEditor.saveMap(l_CommandArgs.get(0));
            case "editmap":
                l_MapEditor.loadMap(l_CommandArgs.get(0));
//            case "validatemap":
//                l_MapEditor.validateMap(MapEditor.d_loadedMap);
//            case "loadmap":
//                l_MapEditor.loadMap(l_CommandArgs.get(0));
//            case "showmap":
//                l_Map.showMap();
//            case "gameplayer":
//                if (l_optionName.equals("-add"))
//                    l_gameEng.addPlayer(l_CommandArgs.get(0));
//                else //-remove option
//                    l_gameEng.removePlayer(l_CommandArgs.get(0));
//            case "assigncountries":
//                l_gameEng.assignCountries();
//            case "deploy":
//                l_gameEng.deploy(l_CommandArgs.get(0), Integer.parseInt(l_CommandArgs.get(1)));
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

    @Override
    public String toString() {
        return "Command{" +
                "d_Keyword='" + d_Keyword + '\'' + d_Option.getD_Name() +
                ", d_Options=" + d_Option.getD_Arguments() +
                '}';
    }
}
