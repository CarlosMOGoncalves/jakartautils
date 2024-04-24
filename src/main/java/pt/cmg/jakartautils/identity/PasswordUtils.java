/*
 * Copyright (c) 2024 Carlos Gonçalves (https://www.linkedin.com/in/carlosmogoncalves/)
 * Likely open-source, so copy at will, bugs will be yours as well.
 */
package pt.cmg.jakartautils.identity;

import java.security.SecureRandom;
import org.apache.commons.codec.digest.DigestUtils;

/**
 * @author Carlos Gonçalves
 */
public class PasswordUtils {

    private static final SecureRandom RANDOM = new SecureRandom();

    /**
     * Generates a salt string.
     */
    public static String generateSalt() {
        byte[] saltBytes = new byte[32];
        RANDOM.nextBytes(saltBytes);
        return DigestUtils.sha256Hex(saltBytes);
    }

    /**
     * Generates a hashed password by using a salt string and a plain text password.
     */
    public static String generateSaltedPassword(String salt, String password) {
        return DigestUtils.sha256Hex(salt + password);
    }

}
