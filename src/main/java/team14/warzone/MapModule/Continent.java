package team14.warzone.MapModule;

import team14.warzone.GameEngine.Player;

import java.util.ArrayList;

public class Continent {
    private String  NAME;
    private int ID;
    private int CONTROLVALUE;

    private ArrayList<Player> d_currentOwners;

    public Continent(){
    }

    public String getName() {
        return NAME;
    }

    public void setName(String p_name) {
        this.NAME = p_name;
    }

    public int getControlValue() {
        return CONTROLVALUE;
    }

    public void setControlValue(int p_controlValue) {
        this.CONTROLVALUE = p_controlValue;
    }
}
