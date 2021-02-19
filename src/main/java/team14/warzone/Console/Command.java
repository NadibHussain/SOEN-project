package team14.warzone.Console;

import java.util.ArrayList;
import java.util.List;

/**
 * This class is used to create objects representing the commands entered by user in the command line
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
     * Method to execute the command object
     */
    public void execute() {
        // execute the command
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
     * @param p_Options : options list of the command
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
