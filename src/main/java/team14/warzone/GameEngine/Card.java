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
    private String BOMB = "bomb";
    /**
     * BLOCKADE type card
     */
    private String BLOCKADE = "blockade";
    /**
     * AIRLIFT type card
     */
    private String AIRLIFT = "airlift";
    /**
     * DIPLOMACY type card
     */
    private String DIPLOMACY = "diplomacy";

    /**
     * String type array storing types of cards
     */
    public String TYPES[] = {BOMB, BLOCKADE, AIRLIFT, DIPLOMACY};


    /**
     * Getter method for Card type
     * @return card type
     */
    public String getD_CardType() {
        return d_CardType;
    }

    /**
     * Getter method for all types
     * @return array of types
     */
    public String[] getTYPES() {
        return TYPES;
    }

    /**
     * Setter method for types
     * @param TYPES array of types
     */
    public void setTYPES(String[] TYPES) {
        this.TYPES = TYPES;
    }

    /**
     * Setter method for allotting a card type
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

    public Card(String p_CardType) {
        this.d_CardType = p_CardType;
    }
}
