package team14.warzone.GameEngine.Observer;

import java.util.ArrayList;
import java.util.List;

/**
 * Observable class
 */
public class Observable {
    /**
     * List of observers
     */
    public List<Observer> d_observers = new ArrayList<>();

    /**
     * We use this to attach observers to this observable
     *
     * @param p_observer the observer we want to attach to the observable
     */
    public void attach(Observer p_observer) {
        d_observers.add(p_observer);
    }

    /**
     * We use this to detach observers to this observable
     *
     * @param p_observer the observer we want to detach to the observable
     */
    public void detach(Observer p_observer) {
        if (!d_observers.isEmpty()) {
            d_observers.remove(p_observer);
        }
    }

    /**
     * Notify all observers that the state has changed
     *
     * @param p_observable the observable who's state has changed
     */
    public void notifyObservers(Observable p_observable) {
        for (Observer p_observer : d_observers) {
            p_observer.update(p_observable);
        }
    }

}
