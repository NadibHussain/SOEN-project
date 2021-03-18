package team14.warzone.GameEngine.Commands;

import java.util.ArrayList;

import team14.warzone.GameEngine.GameEngine;
import team14.warzone.Console.Option;

public class Diplomacy {
    private GameEngine d_GameEngine;
    private ArrayList<Option> d_OptionList;

    /**
     * Constructor of Diplomacy
     * @author tanzia-ahmed
     * @param p_OptionList
     */
    public Diplomacy (ArrayList<Option> p_OptionList) {
        d_OptionList = new ArrayList<>();
        d_OptionList = p_OptionList;

    }

    /**
     * Executes Diplomacy command
     */
    public void execute(){

    }
    
}
