package database;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import models.User;

/**
 * Data Access Object (DAO) for User operations. 
 * Provides CRUD operations on the User table.
 */
public class UserDAO {

    private DBManager dbManager;
    
    /**
     * Constructor for the UserDAO class.
     *
     * @param dbManager The database manager instance.
     */
    public UserDAO(DBManager dbManager) {
        this.dbManager = dbManager;
    }

    /**
     * Add a new user to the database.
     *
     * @param user The user instance to be added.
     * @return true if the user was added successfully, false otherwise.
     * @throws SQLException if there's an error during the database operation.
     */
    public boolean addUser(User user) throws SQLException {
        String query = "INSERT INTO User (Username, HashedPassword, Salt, FirstName, LastName, IsVIP, CreatedDate, UpdatedOn) "
        		+ "VALUES (?, ?, ?, ?, ?, ?, DATETIME('now', 'localtime'), DATETIME('now', 'localtime'))";
        
        // Using try-with-resources to ensure the PreparedStatement is closed after use.
        try (PreparedStatement preparedStatement = dbManager.getConnection().prepareStatement(query)) {
            preparedStatement.setString(1, user.getUsername());
            preparedStatement.setString(2, user.getHashedPassword());
            preparedStatement.setString(3, user.getSalt());
            preparedStatement.setString(4, user.getFirstName());
            preparedStatement.setString(5, user.getLastName());
            preparedStatement.setBoolean(6, user.isVIP());

            int rowsAffected = preparedStatement.executeUpdate();
            return rowsAffected > 0;
        }
    }

    /**
     * Check if a username already exists in the database.
     *
     * @param username The username to be checked.
     * @return true if the username exists, false otherwise.
     * @throws SQLException if there's an error during the database operation.
     */
    public boolean doesUsernameExist(String username) throws SQLException {
        String query = "SELECT COUNT(*) AS count FROM User WHERE Username = ?";
        try (PreparedStatement preparedStatement = dbManager.getConnection().prepareStatement(query)) {
            preparedStatement.setString(1, username);
            
            ResultSet resultSet = preparedStatement.executeQuery();
            return resultSet.next() && resultSet.getInt("count") > 0;
        }
    }

    /**
     * Fetch user details based on the username.
     *
     * @param username The username to be used for fetching the user details.
     * @return A User instance if the username exists, null otherwise.
     * @throws SQLException if there's an error during the database operation.
     */
    public User getUserByUsername(String username) throws SQLException {
        String query = "SELECT * FROM User WHERE Username = ?";
        try (PreparedStatement preparedStatement = dbManager.getConnection().prepareStatement(query)) {
            preparedStatement.setString(1, username);
            
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                return new User(
                	resultSet.getInt("UserID"),
                    resultSet.getString("Username"),
                    resultSet.getString("HashedPassword"),
                    resultSet.getString("Salt"),
                    resultSet.getString("FirstName"),
                    resultSet.getString("LastName"),
                    resultSet.getBoolean("IsVIP")
                );
            }
        }
        return null;
    }

    /**
     * Update user profile details.
     *
     * @param user The updated User instance.
     * @throws SQLException if there's an error during the database operation.
     */
    public void updateUserProfile(User user) throws SQLException {
        String query = "UPDATE User SET Username = ?, HashedPassword = ?, Salt = ?, FirstName = ?, LastName = ?, UpdatedOn = DATETIME('now', 'localtime')"
        		+ " WHERE UserID = ?";
        
        try (PreparedStatement preparedStatement = dbManager.getConnection().prepareStatement(query)) {
            preparedStatement.setString(1, user.getUsername());
            preparedStatement.setString(2, user.getHashedPassword());
            preparedStatement.setString(3, user.getSalt());
            preparedStatement.setString(4, user.getFirstName());
            preparedStatement.setString(5, user.getLastName());
            preparedStatement.setInt(6, user.getUserId());

            preparedStatement.executeUpdate();
        }
    }
    
    /**
     * Fetch the hashed password for a given user ID.
     *
     * @param userId The ID of the user whose hashed password is to be fetched.
     * @return The hashed password if the user exists, null otherwise.
     * @throws SQLException if there's an error during the database operation.
     */
    public String getHashedPassword(int userId) throws SQLException {
        String query = "SELECT HashedPassword FROM User WHERE UserID = ?";
        
        try (PreparedStatement preparedStatement = dbManager.getConnection().prepareStatement(query)) {
            preparedStatement.setInt(1, userId);
            
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                return resultSet.getString("HashedPassword");
            }
        }
        return null;
    }
    
    /**
     * Update the password (hashed) and salt for a given user.
     *
     * @param user The User instance whose password and salt are to be updated.
     * @param newHashedPassword The new hashed password.
     * @param newSalt The new salt.
     * @return true if the password and salt were updated successfully, false otherwise.
     * @throws SQLException if there's an error during the database operation.
     */
    public boolean updateUserPassword(User user, String newHashedPassword, String newSalt) throws SQLException {
        String query = "UPDATE User SET HashedPassword = ?, Salt = ?, UpdatedOn = DATETIME('now', 'localtime') WHERE UserID = ?";
        
        try (PreparedStatement preparedStatement = dbManager.getConnection().prepareStatement(query)) {
            preparedStatement.setString(1, newHashedPassword);
            preparedStatement.setString(2, newSalt);
            preparedStatement.setInt(3, user.getUserId());
            
            int updatedRows = preparedStatement.executeUpdate();
            return updatedRows > 0;
        }
    }


    /**
     * Upgrade a user to VIP status.
     *
     * @param user The User instance to be upgraded.
     * @throws SQLException if there's an error during the database operation.
     */
    public void upgradeToVIP(User user) throws SQLException {
        String query = "UPDATE User SET IsVIP = true, updatedOn = DATETIME('now', 'localtime') WHERE UserID = ?";
        
        try (PreparedStatement preparedStatement = dbManager.getConnection().prepareStatement(query)) {
            preparedStatement.setInt(1, user.getUserId());
            preparedStatement.executeUpdate();
        }
    }
    
    /**
     * Degrade a user from VIP status to a standard user.
     *
     * @param user The User instance to be degraded.
     * @throws SQLException if there's an error during the database operation.
     */
    public void degradeToStandard(User user) throws SQLException {
        String query = "UPDATE User SET IsVIP = false, updatedOn = DATETIME('now', 'localtime') WHERE UserID = ?";
        
        try (PreparedStatement preparedStatement = dbManager.getConnection().prepareStatement(query)) {
            preparedStatement.setInt(1, user.getUserId());
            preparedStatement.executeUpdate();
        }
    }
    
    /**
     * For TEST Purpose
     * Deletes a user from the database based on the provided username.
     * 
     * @param username The username of the user to be deleted.
     * @return true if the user was successfully deleted, false otherwise.
     */
    public boolean deleteUserByUsername(String username) throws SQLException {
        String query = "DELETE FROM User WHERE Username = ?";
        
        PreparedStatement preparedStatement = dbManager.getConnection().prepareStatement(query);
        preparedStatement.setString(1, username);
        
        int affectedRows = preparedStatement.executeUpdate();
        return affectedRows > 0;
    }

}
