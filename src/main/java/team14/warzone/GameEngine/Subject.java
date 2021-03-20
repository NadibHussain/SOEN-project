package team14.warzone.GameEngine;

import team14.warzone.MapModule.Country;

import java.util.ArrayList;

/**
 * This is the Subject Interface for implementing the Observer pattern
 */
public interface Subject {
    /**
     * This is used to register observers to a subject
     * @param o observer
     */
    public void register(Observer o);

    /**
     * This is used to unregister observers to a subject
     * @param o observer
     */
    public void unregister(Observer o);

    /**
     * This is used to notify observers to a subject
     */
    public void notifyObserver();


}
