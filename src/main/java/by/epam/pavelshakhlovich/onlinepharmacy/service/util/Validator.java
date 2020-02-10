package by.epam.pavelshakhlovich.onlinepharmacy.service.util;


import by.epam.pavelshakhlovich.onlinepharmacy.entity.User;
import com.google.common.annotations.VisibleForTesting;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Validates parameters given by user during registration
 */
public class Validator {
    private static final Pattern REGEX_LOGIN = Pattern.compile("[a-zA-Z0-9]{5,20}");
    private static final Pattern REGEX_PASSWORD = Pattern.compile(".{4,}");
    private static final Pattern REGEX_EMAIL = Pattern.compile("\\w{2,40}@\\w{2,20}\\.\\w{2,4}");

    /**
     * Checks if parameters are valid before insertion a new user into data storage
     *
     * @param user {@link User user} whose parameters are to check
     * @return true if parameters are valid
     */
    public static boolean isUserValid(User user) {
        return isLoginValid(user.getLogin())
                && isPasswordValid(user.getPassword())
                && isEmailValid(user.getEmail());
    }

    @VisibleForTesting
    static boolean isLoginValid(String login) {
        return checkString(login, REGEX_LOGIN);
    }

    @VisibleForTesting
    static boolean isPasswordValid(String password) {
        return checkString(password, REGEX_PASSWORD);
    }

    @VisibleForTesting
    static boolean isEmailValid(String email) {
        return checkString(email, REGEX_EMAIL);
    }

    private static boolean checkString(String sourceString, Pattern regex) {
        if (sourceString == null || sourceString.isEmpty() || regex == null) {
            return false;
        } else {
            Matcher matcher = regex.matcher(sourceString.trim());
            return matcher.matches();
        }
    }

}
