package team14.warzone.Console;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class CommandTest {
    List<Option> options = new ArrayList<>();
    Command command = new Command("editContenent", options);

    @BeforeEach
    public void before() {

    }

    @Test
    void getD_Keyword() {
        assertEquals("editContenent", command.getD_Keyword());
    }

    @Test
    void setD_Keyword() {
        command.setD_Keyword("editCountry");
        assertEquals("editCountry", command.getD_Keyword());
    }

    @Test
    void getD_Options() {
        Option option1 = new Option("-add", Arrays.asList("Africa", "9"));
        Option option2 = new Option("-remove", Arrays.asList("Asia", "7"));
        options.add(option1);
        options.add(option2);
        command.setD_Options(options);
        assertEquals(options, command.getD_Options());
    }

    @Test
    void setD_Options() {
        List<Option> options = new ArrayList<>();
        options.add(new Option("-remove", Arrays.asList("Europe", "6")));
        command.setD_Options(options);
        assertEquals(options, command.getD_Options());
    }
}