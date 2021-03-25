package team14.warzone.Console;

import org.junit.BeforeClass;
import org.junit.Test;
import team14.warzone.GameEngine.Commands.AdminCommands;
import team14.warzone.GameEngine.Commands.Option;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Tests for the Command class
 */
public class AdminCommandsTest {
    /**
     * Static Options
     */
    public static Option d_Options;
    /**
     * Static Admin Commands
     */
    public static AdminCommands d_AdminCommands;

    /**
     * A method to initialize a command with options and arguments
     */
    @BeforeClass
    public static void setup() {
        d_Options = new Option("-add", Arrays.asList("Africa", "9"));
        d_AdminCommands = new AdminCommands("editContenent", d_Options);
    }

    /**
     * Test the getter for keyword
     */
    @Test
    public void getD_Keyword() {
        assertEquals("editContenent", d_AdminCommands.getD_Keyword());
    }

    /**
     * Test getter for options
     */
    @Test
    public void getD_Options() {
        Option l_Option = new Option("-add", Arrays.asList("Africa", "9"));
        assertTrue(l_Option.getD_Name().equals(d_AdminCommands.getD_Options().getD_Name()));
        assertEquals(l_Option.getD_Arguments(), d_AdminCommands.getD_Options().getD_Arguments());
    }
}
