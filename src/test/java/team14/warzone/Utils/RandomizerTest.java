package team14.warzone.Utils;

import org.junit.Assert;
import org.junit.Test;

import static org.junit.Assert.*;

public class RandomizerTest {

    @Test
    public void generateRandomNumber() {
        int l_RandNum = Randomizer.generateRandomNumber(0, 10);
        Assert.assertTrue(l_RandNum >= 0);
        Assert.assertTrue(l_RandNum <= 10);
    }
}