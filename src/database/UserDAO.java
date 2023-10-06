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
        String query = "INSERT INTO User (UserID, Username, HashedPassword, Salt, FirstName, LastName, IsVIP) VALUES (?, ?, ?, ?, ?, ?, ?)";
        
        PreparedStatement preparedStatement = dbManager.getConnection().prepareStatement(query);
        preparedStatement.setInt(1, user.getUserId());
        preparedStatement.setString(2, user.getUsername());
        preparedStatement.setString(3, user.getHashedPassword());
        preparedStatement.setString(4, user.getSalt());
        preparedStatement.setString(5, user.getFirstName());
        preparedStatement.setString(6, user.getLastName());
        preparedStatement.setBoolean(7, user.isVIP());

        return preparedStatement.executeUpdate() > 0;
    }

    // Method to check if a username already exists in the database
    public boolean doesUsernameExist(String username) throws SQLException {
        String query = "SELECT COUNT(*) AS count FROM User WHERE Username = ?";
        PreparedStatement preparedStatement = dbManager.getConnection().prepareStatement(query);
        preparedStatement.setString(1, username);
        
        ResultSet resultSet = preparedStatement.executeQuery();
        if (resultSet.next()) {
            return resultSet.getInt("count") > 0;
        }
        return false;
    }

    // Method to fetch user details based on the username
    public User getUserByUsername(String username) throws SQLException {
        String query = "SELECT * FROM User WHERE Username = ?";
        PreparedStatement preparedStatement = dbManager.getConnection().prepareStatement(query);
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
        return null;  // Return null if user not found
    }

    // Method to update user profile details
    public void updateUserProfile(User user) throws SQLException {
        String query = String.format(
            "UPDATE User SET Username = '%s', HashedPassword = '%s', Salt = '%s', FirstName = '%s', LastName = '%s' WHERE UserID = %d",
            user.getUsername(), user.getHashedPassword(), user.getSalt(), user.getFirstName(), user.getLastName(), user.getUserId()
        );
        dbManager.executeUpdate(query);
    }

    // Method to upgrade a user to VIP status
    public void upgradeToVIP(User user) throws SQLException {
        String query = String.format(
            "UPDATE User SET IsVIP = true WHERE UserID = %d",
            user.getUserId()
        );
        dbManager.executeUpdate(query);
    }

    public int getLastUserId() {
        try {
            ResultSet rs = dbManager.executeQuery("SELECT MAX(UserID) as LastID FROM User");
            if (rs.next()) {
                return rs.getInt("LastID");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0; // return 0 if no users are found, so the next ID will be 1
    }

}
