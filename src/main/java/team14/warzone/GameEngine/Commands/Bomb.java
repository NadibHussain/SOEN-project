package team14.warzone.GameEngine.Commands;

import java.util.ArrayList;

import team14.warzone.GameEngine.GameEngine;
import team14.warzone.Console.Option;

public class Bomb {

    private GameEngine d_GameEngine;
    private ArrayList<Option> d_OptionList;

    /**
     * Constructor of Bomb
     * @author tanzia-ahmed
     * @param p_OptionList
     */
    public Bomb (ArrayList<Option> p_OptionList) {
        d_OptionList = new ArrayList<>();
        d_OptionList = p_OptionList;

    }

    /**
     * Executes bomb command
     */
    public void execute(){

    }
    
}
