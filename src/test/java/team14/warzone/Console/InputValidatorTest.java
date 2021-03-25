package team14.warzone.Console;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Test for InputValidator class
 * Runs a parameterized test, that tests several validator methods
 */
@RunWith(Parameterized.class)
public class InputValidatorTest {

    /**
     * field to instantiate command obj
     */
    private String d_CommandName;
    /**
     * field to instantiate option name
     */
    private String d_OptionName;
    /**
     * field for arguments
     */
    private List<String> d_Arguments;

    /**
     * Contructor for the class
     *
     * @param p_CommandName name of the command
     * @param p_OptionName  name of the option ("-add" or "-remove" or "noOption")
     * @param p_Arguments   list of arguments
     */
    public InputValidatorTest(String p_CommandName, String p_OptionName, List<String> p_Arguments) {
        this.d_CommandName = p_CommandName;
        this.d_OptionName = p_OptionName;
        this.d_Arguments = p_Arguments;
    }

    /**
     * Methods returns a collection of objects that is used to initialize the class.
     * Test methods are run for each set of parameters defined here.
     *
     * @return collection of objects
     */
    @Parameterized.Parameters
    public static Collection<Object[]> testConditions_mapPhase() {
        return Arrays.asList(new Object[][]{
                {"showmap", "noOption", Arrays.asList(new String[]{})},
                {"editcountry", "-add", Arrays.asList(new String[]{"country1", "country2"})},
                {"editcontinent", "-add", Arrays.asList(new String[]{"asia", "10"})},
                {"editneighbor", "-add", Arrays.asList(new String[]{"Bangladesh", "India"})},
                {"showmap", "noOption", Arrays.asList(new String[]{})},
                {"editmap", "noOption", Arrays.asList(new String[]{"mymap"})},
                {"validatemap", "noOption", Arrays.asList(new String[]{})},
                {"loadmap", "noOption", Arrays.asList(new String[]{"mymap"})},
        });
    }

    /**
     * Method tests the validateInput method.
     * Tests are done for all the parameterized set of params
     */
    @Test
    public void validateInput() {
        InputValidator.CURRENT_PHASE = InputValidator.Phase.MAPEDITOR;
        boolean l_IsValid = InputValidator.validateInput(d_CommandName, d_OptionName, d_Arguments);
        assertTrue(l_IsValid);
    }
}