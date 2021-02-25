package team14.warzone.Console;

import org.junit.Test;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Tests for Option class
 */
public class OptionTest {
    /**
     * field to instantiate option object
     */
    Option opt = new Option("-add", Arrays.asList("Africa", "6"));

    /**
     * test getter for name
     */
    @Test
    public void getD_Name() {
        assertEquals("-add", opt.getD_Name());
    }

    /**
     * test setter for name
     */
    @Test
    public void setD_Name() {
        opt.setD_Name("-remove");
        assertEquals("-remove", opt.getD_Name());
    }

    /**
     * test getter for arguments
     */
    @Test
    public void getD_Arguments() {
        assertEquals(Arrays.asList("Africa", "6"), opt.getD_Arguments());
    }

    /**
     * test setter for arguments
     */
    @Test
    public void setD_Arguments() {
        opt.setD_Arguments(Arrays.asList("Europe", "5"));
        assertEquals(Arrays.asList("Europe", "5"), opt.getD_Arguments());
    }
}