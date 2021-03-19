package team14.warzone.GameEngine.Commands;
import java.util.ArrayList;

import team14.warzone.GameEngine.GameEngine;
import team14.warzone.Console.Option;

public class Blockade {
    private GameEngine d_GameEngine;
    private ArrayList<Option> d_OptionList;

    /**
     * Constructor of Blockade
     * @author tanzia-ahmed
     * @param p_OptionList
     */
    public Blockade (ArrayList<Option> p_OptionList) {
        d_OptionList = new ArrayList<>();
        d_OptionList = p_OptionList;

    }

    /**
     * Executes Blockade command
     */
    public void execute(){

    }
    
}
