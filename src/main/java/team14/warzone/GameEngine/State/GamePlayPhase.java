package team14.warzone.GameEngine.State;

import team14.warzone.GameEngine.Card;
import team14.warzone.GameEngine.GameEngine;

import java.util.List;

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
     * Show list of cards currently in possion of player
     */
    @Override
    public void showCards() {
        List<Card> l_Cards = d_GameEngine.getD_CurrentPlayer().getCardList();
        if (l_Cards.isEmpty()) {
            System.out.println(d_GameEngine.getD_CurrentPlayer().getD_Name() + " has no cards");
        } else {
            System.out.print(d_GameEngine.getD_CurrentPlayer().getD_Name() + " cards: [ ");
            for (Card l_Card : l_Cards) {
                System.out.print(l_Card.getD_CardType() + " ");
            }
            System.out.println("]");
        }
    }

    /**
     * Savegame method which is valid in gameplay phase
     *
     * @param p_FileName filename with which the game has to be saved
     */
    @Override
    public void saveGame(String p_FileName) {
        d_GameEngine.getD_GameSaveLoad().runSaveGame(p_FileName);
    }
}
