package team14.warzone.GameEngine;

/**
 * This class implements the Card model
 *
 * @author razashaik
 * @version 1.0
 */
public class Card {
    /**
     * Type of the Card
     */
    private String d_CardType;
    /**
     * BOMB type card
     */
    private String d_Bomb = "bomb";
    /**
     * BLOCKADE type card
     */
    private String d_Blockade = "blockade";
    /**
     * AIRLIFT type card
     */
    private String d_Airlift = "airlift";
    /**
     * DIPLOMACY type card
     */
    private String d_Diplomacy = "diplomacy";

    /**
     * String type array storing types of cards
     */
    public String TYPES[] = {d_Bomb, d_Blockade, d_Airlift, d_Diplomacy};

    /**
     * Check if card is used in current turn
     */
    private boolean d_Used;

    /**
     * Getter method for Card type
     *
     * @return card type
     */
    public String getD_CardType() {
        return d_CardType;
    }

    /**
     * Getter method for all types
     *
     * @return array of types
     */
    public String[] getTYPES() {
        return TYPES;
    }

    /**
     * Setter method for types
     *
     * @param TYPES array of types
     */
    public void setTYPES(String[] TYPES) {
        this.TYPES = TYPES;
    }

    /**
     * Setter method for allotting a card type
     *
     * @param d_CardType type of the card
     */
    public void setD_CardType(String d_CardType) {
        this.d_CardType = d_CardType;
    }


    /**
     * Default constructor for the card
     */
    public Card() {

    }

    /**
     * Card constructor
     *
     * @param p_CardType type of card
     */
    public Card(String p_CardType) {
        this.d_CardType = p_CardType;
        this.d_Used = false;
    }

    /**
     * Check if card is used
     *
     * @return card
     */
    public boolean isD_Used() {
        return d_Used;
    }

    /**
     * Change status of card used or unused
     *
     * @param p_Used used or not
     */
    public void setD_Used(boolean p_Used) {
        this.d_Used = p_Used;
    }
}
