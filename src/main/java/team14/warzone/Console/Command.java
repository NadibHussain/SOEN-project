package team14.warzone.Console;

import team14.warzone.GameEngine.GameEngine;
import team14.warzone.MapModule.MapEditor;

import java.io.FileNotFoundException;
import java.util.List;

/**
 * This class is used to create objects representing the commands entered by user in the command line
 *
 * @author zeina
 * @author Anagh
 * @version 1.0
 */
public class Command {
    /**
     * field stores keyword for the command
     */
    private String d_Keyword;
    /**
     * field stores option object
     */
    private Option d_Option = new Option();

    /**
     * field stores instance of the game engine
     */
    private GameEngine d_GameEngine;
    /**
     * field stores instance of the map editor
     */
    private MapEditor d_MapEditor;

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
        String l_OptionName = this.getD_Options().getD_Name();

        switch (this.getD_Keyword()) {
            case "editcontinent":
                if (l_OptionName.equals("-add"))
                    d_MapEditor.getD_LoadedMap().addContinent(l_CommandArgs.get(0),
                            Integer.parseInt(l_CommandArgs.get(1)));
                else //-remove option
                    d_MapEditor.getD_LoadedMap().removeContinent(l_CommandArgs.get(0));
                break;

            case "editcountry":
                if (l_OptionName.equals("-add"))
                    d_MapEditor.getD_LoadedMap().addCountry(l_CommandArgs.get(0), l_CommandArgs.get(1));
                else //-remove option
                    d_MapEditor.getD_LoadedMap().removeCountry(l_CommandArgs.get(0));
                break;

            case "editneighbor":
                if (l_OptionName.equals("-add"))
                    d_MapEditor.getD_LoadedMap().addNeighbour(l_CommandArgs.get(0), l_CommandArgs.get(1));
                else //-remove option
                    d_MapEditor.getD_LoadedMap().removeNeighbour(l_CommandArgs.get(0), l_CommandArgs.get(1));
                break;

            case "savemap":
                d_MapEditor.saveMap(l_CommandArgs.get(0));
                break;

            case "editmap":
                d_MapEditor.editMap(l_CommandArgs.get(0));
                break;

            case "validatemap":
                d_MapEditor.validateMap(d_MapEditor.getD_LoadedMap());
                break;

            case "loadmap":
                d_GameEngine.loadMap(l_CommandArgs.get(0));
                break;

            case "showmap":
                if (d_GameEngine.getD_LoadedMap() == null)
                    System.out.println("Please load a map first!");
                else
                    d_GameEngine.getD_LoadedMap().showMap();
                break;

            case "gameplayer":
                if (l_OptionName.equals("-add"))
                    d_GameEngine.addPlayer(l_CommandArgs.get(0));
                else //-remove option
                    d_GameEngine.removePlayer(l_CommandArgs.get(0));
                break;

            case "assigncountries":
                d_GameEngine.assignCountries();
                break;

            case "deploy":
                try {
                    d_GameEngine.deploy(l_CommandArgs.get(0), Integer.parseInt(l_CommandArgs.get(1)));
                } catch (Exception e) {
                    System.out.println(e.getMessage());
                }
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
     * A method to set the MapEditor
     *
     * @param p_MapEditor MapEditor param
     */
    public void setD_MapEditor(MapEditor p_MapEditor) {
        d_MapEditor = p_MapEditor;
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
