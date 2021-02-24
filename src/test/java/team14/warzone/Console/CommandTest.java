package team14.warzone.Console;

import org.junit.jupiter.api.BeforeEach;
import org.junit.Test;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class CommandTest {
    List<Option> options = new ArrayList<>();
    Command command = new Command("editContenent", (Option) options);

    @Test
    public void getD_Keyword() {
        assertEquals("editContenent", command.getD_Keyword());
    }

    @Test
    public void setD_Keyword() {
        command.setD_Keyword("editCountry");
        assertEquals("editCountry", command.getD_Keyword());
    }

    @Test
    public void getD_Options() {
        Option option1 = new Option("-add", Arrays.asList("Africa", "9"));
        Option option2 = new Option("-remove", Arrays.asList("Asia", "7"));
        options.add(option1);
        options.add(option2);
        command.setD_Options((Option) options);
        assertEquals(options, command.getD_Options());
    }

    @Test
    public void setD_Options() {
        List<Option> options = new ArrayList<>();
        options.add(new Option("-remove", Arrays.asList("Europe", "6")));
        command.setD_Options((Option) options);
        assertEquals(options, command.getD_Options());
    }
}