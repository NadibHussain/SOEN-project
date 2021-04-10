package team14.warzone.GameEngine.Strategy;

import team14.warzone.GameEngine.GameEngine;
import team14.warzone.GameEngine.Player;
import team14.warzone.MapModule.Country;

import java.util.ArrayList;
import java.util.Comparator;

public class Benevolent implements Behavior {
    @Override
    public void issueOrder(GameEngine p_GE, Player p_Player) {

    }

    // find weakest country
    private Country findWeakestCountry(Player p_Player) {
        ArrayList<Country> l_CountryList = p_Player.getD_CountriesOwned();
        Country l_WeakestCountry = null;
        l_CountryList.sort(Comparator.comparing(Country::getD_NumberOfArmies));
        l_WeakestCountry = l_CountryList.get(0);
        return l_WeakestCountry;
    }

    // reinforce weaker country - find weaker countries
}
