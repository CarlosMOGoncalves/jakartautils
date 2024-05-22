/*
 * Copyright (c) 2024 Carlos Gonçalves (https://www.linkedin.com/in/carlosmogoncalves/)
 * Likely open-source, so copy at will, bugs will be yours as well.
 */
package pt.cmg.jakartautils.identity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Test;

/**
 * @author Carlos Gonçalves
 */
public class PasswordUtilsTests {

    @Test
    public void testSaltsAreDifferentEachTime() {

        String salt1 = PasswordUtils.generateSalt();
        String salt2 = PasswordUtils.generateSalt();

        // Assert that the generated salt is a hex string
        assertTrue(salt1.matches("[a-fA-F0-9]+"));

        // Assert that the length of the salt is 32 characters (SHA-256 hash length)
        assertEquals(64, salt1.length());

        // Assert that the generated salts are different
        assertNotEquals(salt1, salt2);
    }

    @Test
    public void testGeneratePassword() {

        String salt = PasswordUtils.generateSalt();
        System.out.println("Salt: " + salt);

        String saltedPassword = PasswordUtils.generateSaltedPassword(salt, "password");
        System.out.println("Password: " + saltedPassword);

        String saltedPassword2 = PasswordUtils.generateSaltedPassword(salt, "password");

        assertEquals(saltedPassword, saltedPassword2);

    }

}
