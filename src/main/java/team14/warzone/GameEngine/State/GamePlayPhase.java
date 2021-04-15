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

    /**
     * Savegame method which is valid in gameplay phase
     * @param p_FileName filename with which the game has to be saved
     */
    @Override
    public void saveGame(String p_FileName) {
        d_GameEngine.getD_GameSaveLoad().runSaveGame(p_FileName);
    }
}
