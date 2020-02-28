package by.epam.pavelshakhlovich.onlinepharmacy.util;

import java.util.Random;

/**
 * An utility class for generating random String that will be used as salt for user passwords
 */

public class SaltGenerator {

    private static final String CHAR_LIST =
            "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890";
    private static final int RANDOM_STRING_LENGTH = 12;

    private static SaltGenerator instance = new SaltGenerator();
    private static Random random = new Random();

    public static SaltGenerator getInstance() {
        return instance;
    }

    private SaltGenerator() {

    }

    public String getSalt (){
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < RANDOM_STRING_LENGTH; i++) {
            int number = random.nextInt(CHAR_LIST.length()-1);
            char ch = CHAR_LIST.charAt(number);
            stringBuilder.append(ch);
        }
        return stringBuilder.toString();
    }
}
