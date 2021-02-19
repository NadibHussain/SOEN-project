package team14.warzone.Console;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import static org.junit.Assert.*;

@RunWith(Parameterized.class)
public class InputValidatorTest {

    private String d_CommandName;
    private String d_OptionName;
    private List<String> d_Arguments;

    public InputValidatorTest(String p_CommandName, String p_OptionName, List<String> p_Arguments) {
        this.d_CommandName = p_CommandName;
        this.d_OptionName = p_OptionName;
        this.d_Arguments = p_Arguments;
    }

    @Parameterized.Parameters
    public static Collection<Object[]> testConditions() {
        return Arrays.asList(new Object[][]{
                {"showmap", "noOption", Arrays.asList(new String[]{})},
                {"editcountry", "-add", Arrays.asList(new String[]{"1", "1"})}
        });
    }

    @Test
    public void validateInput() {
        boolean l_IsValid = InputValidator.validateInput(d_CommandName, d_OptionName, d_Arguments);
        assertTrue("Invalid command name", l_IsValid);
    }
}