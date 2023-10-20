package controllers;

import database.DBManager;
import exceptions.UserException;
import models.User;
import services.UserService;
import services.factory.ServiceFactory;
import services.factory.ServiceFactoryImpl;
	
/**
 * The UserController class provides an interface for the front-end components
 * to interact with the user-related services. It serves as an intermediary 
 * between the service layer and the front-end, ensuring that operations related
 * to users, such as registration, login, and profile updates, are handled 
 * appropriately and securely.
 */
public class UserController {
	
	// Dependency Injection
	private final UserService userService;
	
	/**
     * Constructs a UserController with a given DBManager.
     * It initializes the UserService using the ServiceFactory.
     * 
     * @param dbManager The database manager to be used for database operations.
     */
    public UserController(DBManager dbManager) {
    	ServiceFactory serviceFactory = new ServiceFactoryImpl(dbManager);
        this.userService = serviceFactory.createUserService();
    }

    /**
     * Registers a new user in the system.
     * 
     * @param username User's desired username.
     * @param password User's desired password.
     * @param firstName User's first name.
     * @param lastName User's last name.
     * @return true if registration succeeds, false otherwise.
     * @throws UserException if there's an error during registration.
     */
    public boolean registerUser(String username, String password, String firstName, String lastName) throws UserException {
        System.out.println("Attempting to register user: " + username);
        return userService.registerUser(username, password, firstName, lastName);
    }

    /**
     * Authenticates a user's login request.
     * 
     * @param username The user's username.
     * @param password The user's password.
     * @return User object if authentication succeeds, null otherwise.
     * @throws UserException if there's an error during login.
     */
    public User loginUser(String username, String password) throws UserException {
    	System.out.println("Attempting to log in user: " + username);
    	return userService.loginUser(username, password);
    }

    /**
     * Updates the profile of a user.
     * 
     * @param user The user object with updated information.
     * @return true if update succeeds, false otherwise.
     * @throws UserException if there's an error during profile update.
     */
    public boolean updateUserProfile(User user) throws UserException {
    	System.out.println("Attempting to update profile for user ID: " + user.getUserId());
    	return userService.updateUserProfile(user);
    }

    /**
     * Updates the password of a user.
     * 
     * @param user The user whose password needs to be updated.
     * @param newPassword The new password.
     * @return true if password update succeeds, false otherwise.
     * @throws UserException if there's an error during password update.
     */
    public boolean updateUserPassword(User user, String newPassword) throws UserException {
    	System.out.println("Attempting to update password for user ID: " + user.getUserId());
    	return userService.updateUserPassword(user, newPassword);
    }
    
    /**
     * Checks if a username already exists in the system.
     * 
     * @param username The username to check.
     * @return true if username exists, false otherwise.
     * @throws UserException if there's an error during the check.
     */
    public boolean usernameExists(String username) throws UserException {
    	System.out.println("Checking existence of username: " + username);
    	return userService.usernameExists(username);
    }
    
    /**
     * Retrieves the hashed password for a user based on user ID.
     * 
     * @param userId The ID of the user.
     * @return Hashed password string.
     * @throws UserException if there's an error during retrieval.
     */
    public String getHashedPassword(int userId) throws UserException {
    	System.out.println("Retrieving hashed password for user ID: " + userId);
    	return userService.getHashedPassword(userId);
    }
    
    /**
     * Upgrades a user to VIP status.
     * 
     * @param user The user to be upgraded.
     * @return true if upgrade succeeds, false otherwise.
     * @throws UserException if there's an error during the upgrade.
     */
    public boolean upgradeToVIP(User user) throws UserException {
    	System.out.println("Upgrading user ID: " + user.getUserId() + " to VIP status.");
    	return userService.upgradeToVIP(user);
    }
    
    /**
     * Downgrades a user from VIP status.
     * 
     * @param user The user to be downgraded.
     * @return true if downgrade succeeds, false otherwise.
     * @throws UserException if there's an error during the downgrade.
     */
    public boolean degradeToStandard(User user) throws UserException {
    	System.out.println("Downgrading user ID: " + user.getUserId() + " from VIP status.");
    	return userService.degradeToStandard(user);
    }
}