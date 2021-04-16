package team14.warzone.GameEngine.Strategy;

import team14.warzone.GameEngine.GameEngine;
import team14.warzone.GameEngine.Player;

/**
 * Behavior interface
 */
public interface Behavior {
    /**
     * Issue order method
     *
     * @param p_GE     Context game engine
     * @param p_Player Player obejct
     */
    void issueOrder(GameEngine p_GE, Player p_Player);

    /**
     * Convert to string
     *
     * @return string
     */
    public String toString();
}
