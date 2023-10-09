package database;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import models.User;

public class UserDAO {

    private DBManager dbManager;

    public UserDAO(DBManager dbManager) {
        this.dbManager = dbManager;
    }

    // Method to add a new user to the database
    public boolean addUser(User user) throws SQLException {
        String query = "INSERT INTO User (Username, HashedPassword, Salt, FirstName, LastName, IsVIP) VALUES (?, ?, ?, ?, ?, ?)";
        
        PreparedStatement preparedStatement = dbManager.getConnection().prepareStatement(query);
        //preparedStatement.setInt(1, user.getUserId());
        preparedStatement.setString(1, user.getUsername());
        preparedStatement.setString(2, user.getHashedPassword());
        preparedStatement.setString(3, user.getSalt());
        preparedStatement.setString(4, user.getFirstName());
        preparedStatement.setString(5, user.getLastName());
        preparedStatement.setBoolean(6, user.isVIP());

        return preparedStatement.executeUpdate() > 0;
    }

    // Method to check if a username already exists in the database
    public boolean doesUsernameExist(String username) throws SQLException {
        String query = "SELECT COUNT(*) AS count FROM User WHERE Username = ?";
        try (PreparedStatement preparedStatement = dbManager.getConnection().prepareStatement(query)) {
            preparedStatement.setString(1, username);
            
            ResultSet resultSet = preparedStatement.executeQuery();
            return resultSet.next() && resultSet.getInt("count") > 0;
        }
    }

    // Method to fetch user details based on the username
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

    // Method to update user profile details using PreparedStatement
    public void updateUserProfile(User user) throws SQLException {
        String query = "UPDATE User SET Username = ?, HashedPassword = ?, Salt = ?, FirstName = ?, LastName = ? WHERE UserID = ?";
        
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

    // Method to upgrade a user to VIP status
    public void upgradeToVIP(User user) throws SQLException {
        String query = "UPDATE User SET IsVIP = true WHERE UserID = ?";
        
        try (PreparedStatement preparedStatement = dbManager.getConnection().prepareStatement(query)) {
            preparedStatement.setInt(1, user.getUserId());
            preparedStatement.executeUpdate();
        }
    }

    public int getLastUserId() throws SQLException {
        String query = "SELECT MAX(UserID) as LastID FROM User";
        
        try (PreparedStatement preparedStatement = dbManager.getConnection().prepareStatement(query);
             ResultSet rs = preparedStatement.executeQuery()) {
            if (rs.next()) {
                return rs.getInt("LastID");
            }
        }
        return 0;
    }

}
