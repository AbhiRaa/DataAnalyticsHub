package test;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.sql.SQLException;

import org.junit.Before;
import org.junit.Test;

import database.DBManager;
import database.UserDAO;
import exceptions.DatabaseException;
import exceptions.UserException;
import models.User;
import services.UserService;
import services.UserServiceImpl;

/**
 * A test class for the UserService to ensure that its methods are 
 * working correctly with respect to user operations.
 */
public class UserServiceTest {
	
	private UserService userService;
    private DBManager dbManager;
    private UserDAO userDAO;

    @Before
    public void setUp() throws  DatabaseException {
        System.out.println("Setting up UserService for testing...");
        dbManager = DBManager.getInstance();
        userService = new UserServiceImpl(dbManager);
        this.userDAO = new UserDAO(dbManager);
    }
    
    /**
     * Test the registration of a user.
     */
    @Test
	public void testRegisterUser() throws SQLException, UserException {
	    System.out.println("Running testRegisterUser...");
	    boolean result = userService.registerUser("testUsername", "testPassword", "Test", "User");
	    assertTrue(result);
	    
	    // Clean up: Remove the test user from the database
	    assertTrue(userDAO.deleteUserByUsername("testUsername"));
	}

    /**
     * Test the login functionality for a user.
     */
	@Test
	public void testLoginUser() throws SQLException, UserException  {
	    System.out.println("Running testLoginUser...");
	    userService.registerUser("testLoginUsername", "testLoginPassword", "Test", "Login");
	    User user = userService.loginUser("testLoginUsername", "testLoginPassword");
	    assertNotNull(user);
	    
	    // Clean up
	    assertTrue(userDAO.deleteUserByUsername("testLoginUsername"));
	}

    /**
     * Test updating the profile of a user.
     */
	@Test
	public void testUpdateUserProfile() throws SQLException, UserException {
	    System.out.println("Running testUpdateUserProfile...");
	    userService.registerUser("testUpdateProfile", "testPassword", "Test", "Profile");
	    User user = userService.loginUser("testUpdateProfile", "testPassword");
	    user.setFirstName("UpdatedFirstName");
	    assertTrue(userService.updateUserProfile(user));
	    
	    // Clean up
	    assertTrue(userDAO.deleteUserByUsername("testUpdateProfile"));
	}

    /**
     * Test checking the existence of a username.
     */
	@Test
	public void testUsernameExists() throws SQLException, UserException {
	    System.out.println("Running testUsernameExists...");
	    userService.registerUser("testUsernameExists", "testPassword", "Test", "Exists");
	    assertTrue(userService.usernameExists("testUsernameExists"));
	    
	    // Clean up
	    assertTrue(userDAO.deleteUserByUsername("testUsernameExists"));
	}

    /**
     * Test fetching the hashed password for a user.
     */
	@Test
	public void testGetHashedPassword() throws SQLException, UserException  {
	    System.out.println("Running testGetHashedPassword...");
	    userService.registerUser("testHashedPassword", "testPassword", "Test", "Hashed");
	    User user = userService.loginUser("testHashedPassword", "testPassword");
	    String hashedPassword = userService.getHashedPassword(user.getUserId());
	    assertNotNull(hashedPassword);
	    
	    // Clean up
	    assertTrue(userDAO.deleteUserByUsername("testHashedPassword"));
	}

    /**
     * Test upgrading a user to VIP status.
     */
	@Test
	public void testUpgradeToVIP() throws SQLException, UserException  {
	    System.out.println("Running testUpgradeToVIP...");
	    userService.registerUser("testUpgradeVIP", "testPassword", "Test", "VIP");
	    User user = userService.loginUser("testUpgradeVIP", "testPassword");
	    assertTrue(userService.upgradeToVIP(user));
	    
	    // Clean up
	    assertTrue(userDAO.deleteUserByUsername("testUpgradeVIP"));
	}

    /**
     * Test downgrading a user from VIP status.
     */
	@Test
	public void testDegradeToStandard() throws SQLException, UserException  {
	    System.out.println("Running testDegradeToStandard...");
	    userService.registerUser("testDegradeStandard", "testPassword", "Test", "Standard");
	    User user = userService.loginUser("testDegradeStandard", "testPassword");
	    userService.upgradeToVIP(user);
	    assertTrue(userService.degradeToStandard(user));
	    
	    // Clean up
	    assertTrue(userDAO.deleteUserByUsername("testDegradeStandard"));
	}
}