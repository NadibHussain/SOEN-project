package team14.warzone.GameEngine.Commands;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * This class is used to create objects representing the options entered by user in the command line
 */
public class Option implements Serializable {
    /**
     * name of the option (-add or -remove)
     */
    private String d_Name;
    /**
     * list of arguments
     */
    private List<String> d_Arguments = new ArrayList<String>();

    /**
     * Default class constructor
     */
    public Option() {
    }

    /**
     * Class constructor, specifying name and arguments
     *
     * @param p_Arguments : arguments of the option
     */
    public Option(List<String> p_Arguments) {
        this("default", p_Arguments);
    }

    /**
     * Class constructor, specifying name and arguments
     *
     * @param p_Name      : Name of the option
     * @param p_Arguments : arguments of the option
     */
    public Option(String p_Name, List<String> p_Arguments) {
        this.d_Name = p_Name;
        this.d_Arguments = p_Arguments;
    }

    /**
     * A method to get the Name related to the current option
     *
     * @return String : the Name of the option
     */
    public String getD_Name() {
        return d_Name;
    }

    /**
     * A method to set the Name related to the current option
     *
     * @param p_Name : Name of the option
     */
    public void setD_Name(String p_Name) {
        this.d_Name = p_Name;
    }

    /**
     * A method to get a list of arguments related to the current option
     *
     * @return d_Arguments : arguments list of the option
     */
    public List<String> getD_Arguments() {
        return d_Arguments;
    }

    /**
     * A method to set the arguments related to the current option
     *
     * @param p_Arguments : arguments list of the option
     */
    public void setD_Arguments(List<String> p_Arguments) {
        this.d_Arguments = p_Arguments;
    }

    /**
     * A method to add argument to the option
     *
     * @param p_Arg : argument to be added
     */
    public void addArgument(String p_Arg) {
        this.d_Arguments.add(p_Arg);
    }
}
