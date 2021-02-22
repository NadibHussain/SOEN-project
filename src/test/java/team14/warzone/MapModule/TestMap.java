package team14.warzone.MapModule;
import static org.junit.jupiter.api.Assertions.assertEquals;


import java.util.ArrayList;
import java.util.Stack;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class TestMap {

    public static Map m1;
    public static Continent Asia, Africa;
    public static Country India, Kenya;

    @BeforeAll
    public static void initialize() {
        m1 = new Map();
        Asia = new Continent(1, "Asia", 5);
        Africa = new Continent(2, "Africa", 3);
        India = new Country(1, "India", "Asia", null, 0);
        Kenya = new Country(2, "Kenya", "Africa", null, 0);
    }

    @Test
    @DisplayName("Testing Add Continent")
    public void testAddContinent() {
        m1.addContinent("Asia", 5);
        assertEquals("Asia", Asia.getD_ContinentID());
    }

    @Test
    @DisplayName("Testing Add Country")
    public void testAddCountry() {
        m1.addCountry("India", "Asia");
        assertEquals("India", India.getD_CountryID());
    }

}
