package team14.warzone.Utils;

import java.util.concurrent.ThreadLocalRandom;

/**
 * Class to use random number generation
 */
public class Randomizer {


    /**
     * Generates a random number between given range
     *
     * @param p_LowerLimit upper limit of number
     * @param p_UpperLimit lower limit of number
     * @return int
     */
    public static int generateRandomNumber(int p_LowerLimit, int p_UpperLimit) {
        if (p_LowerLimit >= p_UpperLimit) return 0;
        int l_RandomNum = ThreadLocalRandom.current().nextInt(p_LowerLimit, p_UpperLimit + 1);
        return l_RandomNum;
    }
}
