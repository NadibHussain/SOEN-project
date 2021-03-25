package team14.warzone.MapModule;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.jupiter.api.DisplayName;

import static junit.framework.TestCase.assertNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

/**
 * Tests for Map class
 */
public class TestMap {
    /**
     * static map
     */
    public static Map d_M1;

    /**
     * static initializing test
     */
    @BeforeClass
    public static void initialize() {
        d_M1 = new Map();
        d_M1.addContinent("Africa",6);
        d_M1.addCountry("South-Africa","Africa");
    }

    /**
     * tests adding continents
     */
    @Test
    @DisplayName("Testing Add Continent")
    public void testAddContinent() {
        int l_ContinentCount = d_M1.getD_Continents().size();
        d_M1.addContinent("Asia", 5);
        assertNotNull(d_M1.findContinent("Asia"));
        assertEquals(l_ContinentCount+1, d_M1.getD_Continents().size());
    }
    /**
     * tests adding countries
     */

    @Test
    @DisplayName("Testing Add Country")
    public void testAddCountry() {
        int l_CountryCount = d_M1.getD_Countries().size();
        d_M1.addCountry("Uganda", "Africa");
        assertNotNull(d_M1.findCountry("Uganda"));
        assertEquals(l_CountryCount+1, d_M1.getD_Countries().size());

    }

    /**
     * tests removing countries
     */

    @Test
    @DisplayName("Testing Remove Country")
    public void testRemoveCountry() {
        int l_CountryCount = d_M1.getD_Countries().size();
        d_M1.removeCountry("Uganda");
        assertNull(d_M1.findCountry("Uganda"));
        assertEquals(l_CountryCount-1, d_M1.getD_Countries().size());
    }

    /**
     * test removing continent
     */
    @Test
    @DisplayName("Testing Remove Continent")
    public void testRemoveContinent() {
        int l_ContinentCount = d_M1.getD_Continents().size();
        d_M1.removeContinent("Asia");
        assertNull(d_M1.findContinent("Asia"));
        assertEquals(l_ContinentCount-1, d_M1.getD_Continents().size());
    }

    /**
     * test find a continent
     */
    @Test
    @DisplayName("Testing find Continent")
    public void testFindContinent() {
        assertNotNull(d_M1.findContinent("Africa"));
    }

    /**
     * test find a country
     */
    @Test
    @DisplayName("Testing find Country")
    public void testFindCountry() {
        assertNotNull(d_M1.findCountry("South-Africa"));
    }

    /**
     * testing getting a country from a continent
     */
    @Test
    @DisplayName("Testing getting list of country of continent")
    public void testGetCountryListOfContinent() {
        d_M1.addContinent("Europe",10);
        d_M1.addCountry("Germany","Europe");
        d_M1.addCountry("Denmark","Europe");
        d_M1.addCountry("Iceland","Europe");
        d_M1.addCountry("England","Europe");
        assertEquals(d_M1.getCountryListOfContinent("Europe").size(),4);

    }



}
