package team14.warzone.Console;

import java.util.ArrayList;
/**
 * This class is used to create objects representing the options entered by user in the command line
 */
public class Option {
    private String d_Name;
    private ArrayList<String> d_Arguments = new ArrayList<String>();

    /**
     * Class constructor
     * @param p_Name : Name of the option
     * @param p_Arguments : arguments of the option
     */
    public Option(String p_Name, ArrayList<String> p_Arguments) {
        this.d_Name = p_Name;
        this.d_Arguments = p_Arguments;
    }

    /**
     * A method to get the Name related to the current option
     * @return String : the Name of the option
     */
    public String getD_Name() {
        return d_Name;
    }

    /**
     * A method to set the Name related to the current option
     * @param p_Name : Name of the option
     */
    public void setD_Name(String p_Name) {
        this.d_Name = p_Name;
    }

    /**
     * A method to get a list of arguments related to the current option
     * @return d_Arguments : arguments list of the option
     */
    public ArrayList<String> getD_Arguments() {
        return d_Arguments;
    }

    /**
     * A method to set the arguments related to the current option
     * @param p_Arguments : arguments list of the option
     */
    public void setD_Arguments(ArrayList<String> p_Arguments) {
        this.d_Arguments = p_Arguments;
    }
}
