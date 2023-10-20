package test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

import models.User;

/**
 * A test class for the User model to ensure that its methods and
 * constructors are working correctly.
 */
public class UserTest {

	private User user;

    @Before
    public void setUp() {
        System.out.println("Setting up a user for UserTest...");
        user = new User("testuser", "hashedPass", "saltValue", "John", "Doe", true);
    }

    /**
     * Test the username getter and setter methods.
     */
    @Test
    public void testUsername() {
        System.out.println("Running testUsername...");
        assertEquals("testuser", user.getUsername());
        user.setUsername("newuser");
        assertEquals("newuser", user.getUsername());
    }

    /**
     * Test the hashed password getter and setter methods.
     */
    @Test
    public void testHashedPassword() {
        System.out.println("Running testHashedPassword...");
        assertEquals("hashedPass", user.getHashedPassword());
        user.setHashedPassword("newHashedPass");
        assertEquals("newHashedPass", user.getHashedPassword());
    }

    /**
     * Test the salt getter and setter methods.
     */
    @Test
    public void testSalt() {
        System.out.println("Running testSalt...");
        assertEquals("saltValue", user.getSalt());
        user.setSalt("newSaltValue");
        assertEquals("newSaltValue", user.getSalt());
    }

    /**
     * Test the first name getter and setter methods.
     */
    @Test
    public void testFirstName() {
        System.out.println("Running testFirstName...");
        assertEquals("John", user.getFirstName());
        user.setFirstName("Mike");
        assertEquals("Mike", user.getFirstName());
    }

    /**
     * Test the last name getter and setter methods.
     */
    @Test
    public void testLastName() {
        System.out.println("Running testLastName...");
        assertEquals("Doe", user.getLastName());
        user.setLastName("Smith");
        assertEquals("Smith", user.getLastName());
    }

    /**
     * Test the VIP status getter and setter methods.
     */
    @Test
    public void testIsVIP() {
        System.out.println("Running testIsVIP...");
        assertTrue(user.isVIP());
        user.setVIP(false);
        assertFalse(user.isVIP());
    }

    /**
     * Test the toString method of the User class.
     */
    @Test
    public void testToString() {
        System.out.println("Running testToString...");
        String expectedString = "User [username=testuser, hashedPassword=hashedPass, salt=saltValue, firstName=John, lastName=Doe, isVIP=true]";
        assertEquals(expectedString, user.toString());
    }
}
