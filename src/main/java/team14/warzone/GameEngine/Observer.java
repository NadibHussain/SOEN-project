package team14.warzone.GameEngine;

import java.util.ArrayList;

/**
 * This is the Observer Interface for implementing the Observer pattern
 */
public interface Observer {
    /**
     * This method updates the observer in case of any changes
     * @param d_CountriesOwned The countries owned by a player
     */
    public void update(ArrayList d_CountriesOwned);
}
