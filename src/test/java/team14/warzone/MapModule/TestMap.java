package team14.warzone.MapModule;
import static junit.framework.TestCase.assertNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;


import org.junit.BeforeClass;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.Test;

public class TestMap {

    public static Map d_M1;
    public static Continent d_Asia, d_Africa;
    public static Country d_India, d_Kenya;
    @BeforeClass
    public static void initialize() {
        d_M1 = new Map();
        d_M1.addContinent("Africa",6);
        d_M1.addCountry("South-Africa","Africa");
    }

    @Test
    @DisplayName("Testing Add Continent")
    public void testAddContinent() {
        int l_ContinentCount = d_M1.getD_Continents().size();
        d_M1.addContinent("Asia", 5);
        assertNotNull(d_M1.findContinent("Asia"));
        assertEquals(l_ContinentCount+1, d_M1.getD_Continents().size());
    }

    @Test
    @DisplayName("Testing Add Country")
    public void testAddCountry() {
        int l_CountryCount = d_M1.getD_Countries().size();
        d_M1.addCountry("Uganda", "Africa");
        assertNotNull(d_M1.findCountry("Uganda"));
        assertEquals(l_CountryCount+1, d_M1.getD_Countries().size());

    }

    @Test
    @DisplayName("Testing Remove Country")
    public void testRemoveCountry() {
        int l_CountryCount = d_M1.getD_Countries().size();
        d_M1.removeCountry("Uganda");
        assertNull(d_M1.findCountry("Uganda"));
        assertEquals(l_CountryCount-1, d_M1.getD_Countries().size());
    }

    @Test
    @DisplayName("Testing Remove Continent")
    public void testRemoveContinent() {
        int l_ContinentCount = d_M1.getD_Continents().size();
        d_M1.removeContinent("Asia");
        assertNull(d_M1.findContinent("Asia"));
        assertEquals(l_ContinentCount-1, d_M1.getD_Continents().size());
    }

    @Test
    @DisplayName("Testing find Continent")
    public void testFindContinent() {
        assertNotNull(d_M1.findContinent("Africa"));
    }

    @Test
    @DisplayName("Testing find Country")
    public void testFindCountry() {
        assertNotNull(d_M1.findCountry("South-Africa"));
    }

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
