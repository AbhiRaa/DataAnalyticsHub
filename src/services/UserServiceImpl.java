package services;

import java.sql.SQLException;

import database.DBManager;
import database.UserDAO;
import exceptions.UserException;
import models.User;
import utils.PasswordUtils;

/**
 * This class provides an implementation for the UserService interface.
 * It provides services related to the User entity, such as adding, deleting, updating, and fetching posts.
 */
public class UserServiceImpl implements UserService {
	
	private UserDAO userDAO;
	
	/**
     * Constructor initializes the UserDAO object.
     *
     * @param dbManager The database manager instance for database operations.
     */
    public UserServiceImpl(DBManager dbManager) {
        this.userDAO = new UserDAO(dbManager);
    }

    @Override
    public boolean registerUser(String username, String password, String firstName, String lastName) throws UserException {
    	try {
            // Check if the username already exists
            if (!userDAO.doesUsernameExist(username)) {
                
                // Generate salt for the password
                String salt = PasswordUtils.generateSalt();
                
                // Hash the password using the salt
                String hashedPassword = PasswordUtils.hashPassword(password, salt);
                
                // Create a new user object
                User newUser = new User(username, hashedPassword, salt, firstName, lastName, false);
                
                // Add the user to the database
                return userDAO.addUser(newUser);
            }
        } catch (SQLException e) {
        	System.err.println("Error registering user: " + e.getMessage());
            throw new UserException("Error registering user: " + e.getMessage(), e);
        }
        return false;
    }

    @Override
    public User loginUser(String username, String password) throws UserException {
    	 try {
             User user = userDAO.getUserByUsername(username);
             if (user != null) {
                 String hashedPassword = PasswordUtils.hashPassword(password, user.getSalt());
                 if (hashedPassword.equals(user.getHashedPassword())) {
                     return user; // Successful login
                 }
             }
         } catch (SQLException e) {
        	 System.err.println("Error logging in user: " + e.getMessage());
             throw new UserException("Error logging in user: " + e.getMessage(), e);
         }
         return null; // Failed login
    }

    @Override
    public boolean updateUserProfile(User user) throws UserException {
    	try {
            userDAO.updateUserProfile(user);
            return true;
        } catch (SQLException e) {
        	 System.err.println("Error updating user profile: " + e.getMessage());
            throw new UserException("Error updating user profile: " + e.getMessage(), e);
        }
    }

    @Override
    public boolean updateUserPassword(User user, String newPassword) throws UserException {
    	try {
        	// Check if a new password is provided
            if (newPassword != null && !newPassword.isEmpty()) {
                // Generate a new salt
                String newSalt = PasswordUtils.generateSalt();
                // Hash the new password with the new salt
                String newHashedPassword = PasswordUtils.hashPassword(newPassword, newSalt);
                
                // Update the user object
                user.setSalt(newSalt);
                user.setHashedPassword(newHashedPassword);
                
                
            }
            return userDAO.updateUserPassword(user, user.getHashedPassword(), user.getSalt());
        } catch (SQLException e) {
        	System.err.println("Error updating user password: " + e.getMessage());
            throw new UserException("Error updating user password: " + e.getMessage(), e);
        }
    }

    @Override
    public boolean usernameExists(String username) throws UserException {
    	try {
            User user = userDAO.getUserByUsername(username);
            return user != null;
        } catch (SQLException e) {
        	System.err.println("Error checking username existence: " + e.getMessage());
            throw new UserException("Error checking username existence: " + e.getMessage(), e);
        }
    }

    @Override
    public String getHashedPassword(int userId) throws UserException {
    	try {
            String hashedPassword = userDAO.getHashedPassword(userId);
            if (hashedPassword != null) {
                return hashedPassword;
            }
        } catch (SQLException e) {
        	System.err.println("Error retrieving hashed password: " + e.getMessage());
            throw new UserException("Error retrieving hashed password: " + e.getMessage(), e);
        }
        return null;
    }

    @Override
    public boolean upgradeToVIP(User user) throws UserException {
    	try {
            userDAO.upgradeToVIP(user);
            return true;
        } catch (SQLException e) {
        	System.err.println("Error upgrading user to VIP: " + e.getMessage());
            throw new UserException("Error upgrading user to VIP: " + e.getMessage(), e);
        }
    }

    @Override
    public boolean degradeToStandard(User user) throws UserException {
    	try {
            userDAO.degradeToStandard(user);
            return true;
        } catch (SQLException e) {
            throw new UserException("Error degrading user from VIP: " + e.getMessage(), e);
        }
    }
}
