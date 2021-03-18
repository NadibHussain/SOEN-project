package team14.warzone.GameEngine;

import team14.warzone.MapModule.Country;

import java.util.ArrayList;

public class Card {
    private Player player;
    private String cardType;
    private Country country;
    private String BOMB = "bomb";
    private String BLOCKADE = "blockade";
    private String AIRLIFT = "airlift";
    private String DIPLOMACY = "diplomacy";

    public String TYPES[] = {BOMB, BLOCKADE, AIRLIFT, DIPLOMACY};

    public Player getPlayer() {
        return player;
    }
    public void setPlayer(Player player) {
        this.player = player;
    }
    public String getCardType() {
        return cardType;
    }

    public String[] getTYPES() {
        return TYPES;
    }

    public void setTYPES(String[] TYPES) {
        this.TYPES = TYPES;
    }

    public void setCardType(String cardType) {
        this.cardType = cardType;
    }
    public Country getCountry() {
        return country;
    }
    public void setCountry() {
        this.country = country;
    }

    public Card() {

    }
}
