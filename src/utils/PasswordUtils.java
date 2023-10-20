package utils;

import java.security.SecureRandom;
import java.util.Base64;

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;

import exceptions.PasswordHashingException;

/**
 * Utility class for handling password-related operations like hashing, salt generation, and password verification.
 * <p>
 * This class provides methods to securely hash passwords using PBKDF2WithHmacSHA1, generate random salt for passwords,
 * and verify a password against its hashed version.
 * </p>
 */
public class PasswordUtils {
	
	/**
     * Generates a secure random salt.
     *
     * @return A Base64 encoded string representation of the generated salt.
     */
    public static String generateSalt() {
    	System.out.println("Generating salt...");
        SecureRandom random = new SecureRandom();
        byte[] salt = new byte[16];
        random.nextBytes(salt);
        return Base64.getEncoder().encodeToString(salt);
    }
    
    /**
     * Hashes a password using PBKDF2WithHmacSHA1 algorithm.
     *
     * @param password The password to be hashed.
     * @param salt The salt used for hashing.
     * @return The Base64 encoded hashed password.
     * @throws PasswordHashingException if an error occurs during password hashing.
     */
    public static String hashPassword(String password, String salt) {
        try {
        	System.out.println("Hashing password...");
            PBEKeySpec spec = new PBEKeySpec(password.toCharArray(), salt.getBytes(), 10000, 256);
            SecretKeyFactory skf = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");
            byte[] hashed = skf.generateSecret(spec).getEncoded();
            return Base64.getEncoder().encodeToString(hashed);
        } catch (Exception e) {
        	 System.err.println("Error hashing password: " + e.getMessage());
             throw new PasswordHashingException("Error hashing password", e);
        }
    }
    
    /**
     * Verifies if the provided password matches the hashed version.
     *
     * @param password The plain-text password to verify.
     * @param hashedPassword The hashed version of the password.
     * @param salt The salt used during hashing.
     * @return true if the password matches the hashed version, false otherwise.
     */
    public static boolean verifyPassword(String password, String hashedPassword, String salt) {
    	System.out.println("Verifying password...");
    	String computedHash = hashPassword(password, salt);
        return computedHash.equals(hashedPassword);
    }
}

