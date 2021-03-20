package team14.warzone.GameEngine.Commands;

import team14.warzone.GameEngine.GameEngine;

import java.util.ArrayList;

public class Bomb {

    private GameEngine d_GameEngine;
    private ArrayList<Option> d_OptionList;

    /**
     * Constructor of Bomb
     *
     * @param p_OptionList
     * @author tanzia-ahmed
     */
    public Bomb(ArrayList<Option> p_OptionList) {
        d_OptionList = new ArrayList<>();
        d_OptionList = p_OptionList;

    }

    /**
     * Executes bomb command
     */
    public void execute() {

    }

}
