package team14.warzone.MapModule;
import static org.junit.jupiter.api.Assertions.assertEquals;



import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class TestMap {

    public static Map d_M1;
    public static Continent d_Asia, d_Africa;
    public static Country d_India, d_Kenya;

    @BeforeAll
    public static void initialize() {
        d_M1 = new Map();
        d_Asia = new Continent(1, "Asia", 5);
        d_Africa = new Continent(2, "Africa", 3);
        d_India = new Country(1, "India", "Asia", null, 0);
        d_Kenya = new Country(2, "Kenya", "Africa", null, 0);
    }

    @Test
    @DisplayName("Testing Add Continent")
    public void testAddContinent() {
        d_M1.addContinent("Asia", 5);
        assertEquals("Asia", d_Asia.getD_ContinentID());
    }

    @Test
    @DisplayName("Testing Add Country")
    public void testAddCountry() {
        d_M1.addCountry("India", "Asia");
        assertEquals("India", d_India.getD_CountryID());
    }



}
