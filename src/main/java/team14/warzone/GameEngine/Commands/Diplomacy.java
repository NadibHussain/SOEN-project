package team14.warzone.GameEngine.Commands;

import team14.warzone.GameEngine.GameEngine;

import java.util.ArrayList;

public class Diplomacy {
    private GameEngine d_GameEngine;
    private ArrayList<Option> d_OptionList;

    /**
     * Constructor of Diplomacy
     *
     * @param p_OptionList
     * @author tanzia-ahmed
     */
    public Diplomacy(ArrayList<Option> p_OptionList) {
        d_OptionList = new ArrayList<>();
        d_OptionList = p_OptionList;

    }

    /**
     * Executes Diplomacy command
     */
    public void execute() {

    }

}
