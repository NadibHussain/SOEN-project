package team14.warzone.GameEngine;
import team14.warzone.MapModule.Country;

/**
 * This class implements the Card model
 *
 * @author razashaik
 * @version 1.0
 */
public class Card {
    /**
     * Player holding the card
     */
    private Player player;
    /**
     * Type of the Card
     */
    private String cardType;
    /**
     * Country associated with the card
     */
    private Country country;
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
     * Getter method for player
     * @return player holding the card
     */
    public Player getPlayer() {
        return player;
    }

    /**
     * Setter method for player
     * @param player player holding the card
     */
    public void setPlayer(Player player) {
        this.player = player;
    }

    /**
     * Getter method for Card type
     * @return card type
     */
    public String getCardType() {
        return cardType;
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
     * @param cardType type of the card
     */
    public void setCardType(String cardType) {
        this.cardType = cardType;
    }

    /**
     * Get country method
     * @return country
     */
    public Country getCountry() {
        return country;
    }

    /**
     * Set country method
     */
    public void setCountry() {
        this.country = country;
    }

    /**
     * Default constructor for the card
     */
    public Card() {

    }
}
