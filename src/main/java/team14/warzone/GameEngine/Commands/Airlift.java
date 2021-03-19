package team14.warzone.GameEngine.Commands;
import java.util.ArrayList;

import team14.warzone.GameEngine.GameEngine;
import team14.warzone.Console.Option;

public class Airlift {
    private GameEngine d_GameEngine;
    private ArrayList<Option> d_OptionList;

    /**
     * Constructor of Airlift
     * @author tanzia-ahmed
     * @param p_OptionList
     */
    public Airlift (ArrayList<Option> p_OptionList) {
        d_OptionList = new ArrayList<>();
        d_OptionList = p_OptionList;

    }

    /**
     * Executes Airlift command
     */
    public void execute(){

    }
    
}
