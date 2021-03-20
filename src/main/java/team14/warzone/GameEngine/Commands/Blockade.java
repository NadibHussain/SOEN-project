package team14.warzone.GameEngine.Commands;
import team14.warzone.GameEngine.GameEngine;

import java.util.ArrayList;

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
