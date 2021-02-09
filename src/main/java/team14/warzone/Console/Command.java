package team14.warzone.Console;

import java.util.ArrayList;

/**
 * This class is used to create objects representing the commands entered by user in the command line
 */
public class Command {
    private String d_Keyword;
    private ArrayList<Options> d_Options = new ArrayList<Options>();

    /**
     * Class constructor
     * @param p_Keyword : keyword of the command
     * @param p_Options : options of the command, if any
     */
    public Command(String p_Keyword, ArrayList<Options> p_Options) {
        this.d_Keyword = p_Keyword;
        this.d_Options = p_Options;
    }

    /**
     * A method to get the keyword related to the current command
     * @return String : the keyword of the command
     */
    public String getD_Keyword() {
        return d_Keyword;
    }

    /**
     * A method to set the keyword related to the current command
     * @param p_Keyword : keyword of the command
     */
    public void setD_Keyword(String p_Keyword) {
        this.d_Keyword = p_Keyword;
    }

    /**
     * A method to get a list of options related to the current command, if any
     * @return d_Options : options list of the command
     */
    public ArrayList<Options> getD_Options() {
        return d_Options;
    }

    /**
     * A method to set the options related to the current command
     * @param p_Options : options list of the command
     */
    public void setD_Options(ArrayList<Options> p_Options) {
        this.d_Options = p_Options;
    }
}
