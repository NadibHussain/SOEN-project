package team14.warzone.Utils;

import java.util.concurrent.ThreadLocalRandom;

public class Randomizer {

    public static int generateRandomNumber(int p_LowerLimit, int p_UpperLimit) {
        int l_RandomNum = ThreadLocalRandom.current().nextInt(p_LowerLimit, p_UpperLimit + 1);
        return l_RandomNum;
    }
}
