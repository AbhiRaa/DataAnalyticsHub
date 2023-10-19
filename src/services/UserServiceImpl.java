package services;

import java.sql.SQLException;

import database.DBManager;
import database.UserDAO;
import models.User;
import utils.PasswordUtils;

public class UserServiceImpl implements UserService {
	
	private UserDAO userDAO;

    public UserServiceImpl(DBManager dbManager) {
        this.userDAO = new UserDAO(dbManager);
    }

    @Override
    public boolean registerUser(String username, String password, String firstName, String lastName) {
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
            // Handle exception, e.g., log it or inform the user
            System.err.println("Error registering user: " + e.getMessage());
        }
        return false;
    }

    @Override
    public User loginUser(String username, String password) {
    	 try {
             User user = userDAO.getUserByUsername(username);
             if (user != null) {
                 String hashedPassword = PasswordUtils.hashPassword(password, user.getSalt());
                 if (hashedPassword.equals(user.getHashedPassword())) {
                     return user; // Successful login
                 }
             }
         } catch (SQLException e) {
             // Handle exception
         }
         return null; // Failed login
    }

    @Override
    public boolean updateUserProfile(User user) {
    	try {
            userDAO.updateUserProfile(user);
            return true;
        } catch (SQLException e) {
            // Handle exception
        }
        return false;
    }

    @Override
    public boolean updateUserPassword(User user, String newPassword) {
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
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean usernameExists(String username) {
    	try {
            User user = userDAO.getUserByUsername(username);
            return user != null;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public String getHashedPassword(int userId) {
    	try {
            String hashedPassword = userDAO.getHashedPassword(userId);
            if (hashedPassword != null) {
                return hashedPassword;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public boolean upgradeToVIP(User user) {
    	try {
            userDAO.upgradeToVIP(user);
            return true;
        } catch (SQLException e) {
            // Handle exception
        }
        return false;
    }

    @Override
    public boolean degradeToStandard(User user) {
    	try {
            userDAO.degradeToStandard(user);
            return true;
        } catch (SQLException e) {
            // Handle exception
        }
        return false;
    }
}
