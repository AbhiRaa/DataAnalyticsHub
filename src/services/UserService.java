package services;

import exceptions.UserException;
import models.User;

/**
 * An interface that defines the operations related to user management.
 */
public interface UserService {
	
	 /**
     * Registers a new user in the system.
     *
     * @param username  The username of the user.
     * @param password  The password of the user.
     * @param firstName The first name of the user.
     * @param lastName  The last name of the user.
     * @return true if the registration is successful, false otherwise.
	 * @throws UserException 
     */
    boolean registerUser(String username, String password, String firstName, String lastName) throws UserException;

    /**
     * Authenticates a user based on the provided username and password.
     *
     * @param username The username of the user.
     * @param password The password of the user.
     * @return The User object if authentication is successful, null otherwise.
     * @throws UserException 
     */
    User loginUser(String username, String password) throws UserException;

    /**
     * Updates the profile details of a user.
     *
     * @param user The User object containing updated details.
     * @return true if the update is successful, false otherwise.
     * @throws UserException 
     */
    boolean updateUserProfile(User user) throws UserException;

    /**
     * Updates the password of a user.
     *
     * @param user        The User object whose password needs to be updated.
     * @param newPassword The new password.
     * @return true if the password update is successful, false otherwise.
     * @throws UserException 
     */
    boolean updateUserPassword(User user, String newPassword) throws UserException;

    /**
     * Checks if a username already exists in the system.
     *
     * @param username The username to check.
     * @return true if the username exists, false otherwise.
     * @throws UserException 
     */
    boolean usernameExists(String username) throws UserException;

    /**
     * Fetches the hashed password for a given user ID.
     *
     * @param userId The ID of the user whose hashed password is to be fetched.
     * @return The hashed password if the user exists, null otherwise.
     * @throws UserException 
     */
    String getHashedPassword(int userId) throws UserException;

    /**
     * Upgrades a user to VIP status.
     *
     * @param user The User object to be upgraded.
     * @return true if the upgrade is successful, false otherwise.
     * @throws UserException 
     */
    boolean upgradeToVIP(User user) throws UserException;

    /**
     * Degrades a VIP user to standard status.
     *
     * @param user The VIP User object to be degraded.
     * @return true if the degrade operation is successful, false otherwise.
     * @throws UserException 
     */
    boolean degradeToStandard(User user) throws UserException;
}
