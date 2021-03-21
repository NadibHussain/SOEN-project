package team14.warzone.Console;

import org.junit.BeforeClass;
import org.junit.Test;
import team14.warzone.GameEngine.Commands.Command;
import team14.warzone.GameEngine.Commands.Option;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Tests for the Command class
 */
public class CommandTest {
    public static Option d_Options;
    public static Command d_Command;

    /**
     * A method to initialize a command with options and arguments
     */
    @BeforeClass
    public static void setup() {
        d_Options = new Option("-add", Arrays.asList("Africa", "9"));
        d_Command = new Command("editContenent", d_Options);
    }

    /**
     * Test the getter for keyword
     */
    @Test
    public void getD_Keyword() {
        assertEquals("editContenent", d_Command.getD_Keyword());
    }

    /**
     * Test getter for options
     */
    @Test
    public void getD_Options() {
        Option l_Option = new Option("-add", Arrays.asList("Africa", "9"));
        assertTrue(l_Option.getD_Name().equals(d_Command.getD_Options().getD_Name()));
        assertEquals(l_Option.getD_Arguments(), d_Command.getD_Options().getD_Arguments());
    }
}
