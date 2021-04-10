package team14.warzone.GameEngine.Strategy;

import team14.warzone.GameEngine.GameEngine;
import team14.warzone.GameEngine.Player;

public interface Behavior {
    void issueOrder(GameEngine p_GE, Player p_Player);
    public String toString();
}
