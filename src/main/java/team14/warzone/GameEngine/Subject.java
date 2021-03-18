package team14.warzone.GameEngine;

import team14.warzone.MapModule.Country;

import java.util.ArrayList;

public interface Subject {

    public void register(Observer o);
    public void unregister(Observer o);
    public void notifyObserver();


}
