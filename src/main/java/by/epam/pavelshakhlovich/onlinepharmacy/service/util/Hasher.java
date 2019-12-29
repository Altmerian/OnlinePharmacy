package by.epam.pavelshakhlovich.onlinepharmacy.service.util;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Utility that helps to get md5 hash of incoming string
 */
public class Hasher {
    /**
     * Calculates a hash value of a given string with md5 algorithm
     *
     * @param value String to hash
     * @return md5 hash value of the string
     */
    public static String md5Hash(String value) throws NoSuchAlgorithmException, UnsupportedEncodingException {
        String hashValue = null;
        if (value != null) {
            MessageDigest md = MessageDigest.getInstance("MD5");
            byte[] bytesValue = value.getBytes("UTF-8");
            byte[] messageDigest = md.digest(bytesValue);
            StringBuilder sb = new StringBuilder();
            for (byte b : messageDigest) {
                sb.append(String.format("%02x", b));
            }
            hashValue = sb.toString();
        }
        return hashValue;
    }
}
