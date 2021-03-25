package team14.warzone.GameEngine.Observer;

public interface Observer {

    /**
     * used to update state changes of a observable
     * @param p_observable the observable who's state has changed
     */
    public void update(Observable p_observable);
}
