package team14.warzone.Console;

import org.junit.Test;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class OptionTest {
    Option opt = new Option("-add", Arrays.asList("Africa", "6"));

    @Test
    public void getD_Name() {
        assertEquals("-add", opt.getD_Name());
    }

    @Test
    public void setD_Name() {
        opt.setD_Name("-remove");
        assertEquals("-remove", opt.getD_Name());
    }

    @Test
    public void getD_Arguments() {
        assertEquals(Arrays.asList("Africa", "6"), opt.getD_Arguments());
    }

    @Test
    public void setD_Arguments() {
        opt.setD_Arguments(Arrays.asList("Europe", "5"));
        assertEquals(Arrays.asList("Europe", "5"), opt.getD_Arguments());
    }
}