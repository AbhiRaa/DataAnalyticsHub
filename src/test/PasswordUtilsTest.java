package test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import utils.PasswordUtils;

/**
 * A test suite for the PasswordUtils class, ensuring its correctness in generating salts,
 * hashing passwords, and verifying hashed passwords.
 */
public class PasswordUtilsTest {

    /**
     * Test to check if a salt is generated and is not null or empty.
     */
	@Test
    public void testGenerateSalt() {
    	System.out.println("Running testGenerateSalt...");
        String salt = PasswordUtils.generateSalt();
        assertNotNull(salt);
        assertFalse(salt.isEmpty());
    }

    /**
     * Test to check if hashing a password twice with the same salt produces consistent results.
     */
    @Test
    public void testHashPasswordConsistency() {
    	System.out.println("Running testHashPasswordConsistency...");
        String password = "testPassword";
        String salt = PasswordUtils.generateSalt();
        
        String hash1 = PasswordUtils.hashPassword(password, salt);
        String hash2 = PasswordUtils.hashPassword(password, salt);

        assertEquals(hash1, hash2);
    }

    /**
     * Test to verify if a password matches its hashed version using the correct salt.
     */
    @Test
    public void testVerifyPasswordSuccess() {
    	System.out.println("Running testVerifyPasswordSuccess...");
        String password = "testPassword";
        String salt = PasswordUtils.generateSalt();
        String hashedPassword = PasswordUtils.hashPassword(password, salt);

        assertTrue(PasswordUtils.verifyPassword(password, hashedPassword, salt));
    }

    /**
     * Test to verify if a wrong password does not match the hashed version of the correct password.
     */
    @Test
    public void testVerifyPasswordFailure() {
    	System.out.println("Running testVerifyPasswordFailure...");
        String password = "testPassword";
        String wrongPassword = "wrongPassword";
        String salt = PasswordUtils.generateSalt();
        String hashedPassword = PasswordUtils.hashPassword(password, salt);

        assertFalse(PasswordUtils.verifyPassword(wrongPassword, hashedPassword, salt));
    }
}
