package team14.warzone.GameEngine.State;

import team14.warzone.GameEngine.GameEngine;

/**
 * This is a class for GamePlayPhase
 */
public abstract class GamePlayPhase extends Phase {
    /**
     * GamePlayPhase
     *
     * @param p_GameEngine GE
     */
    public GamePlayPhase(GameEngine p_GameEngine) {
        super(p_GameEngine);
    }

    /**
     * Show current players cards
     */
    @Override
    public void showCards() {
        System.out.println("No cards in possession of player.");
    }
}
