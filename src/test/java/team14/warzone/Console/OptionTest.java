package team14.warzone.Console;

import org.junit.jupiter.api.Test;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertEquals;

class OptionTest {
    Option opt = new Option("-add", Arrays.asList("Africa", "6"));

    @Test
    void getD_Name() {
        assertEquals("-add", opt.getD_Name());
    }

    @Test
    void setD_Name() {
        opt.setD_Name("-remove");
        assertEquals("-remove", opt.getD_Name());
    }

    @Test
    void getD_Arguments() {
        assertEquals(Arrays.asList("Africa", "6"), opt.getD_Arguments());
    }

    @Test
    void setD_Arguments() {
        opt.setD_Arguments(Arrays.asList("Europe", "5"));
        assertEquals(Arrays.asList("Europe", "5"), opt.getD_Arguments());
    }
}