package database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class DBManager {
    private static final String DB_URL = "jdbc:sqlite:/Users/abhinav/Development/advance_programming/Assignment2/src/database/DataAnalyticsHub.db";
    private Connection connection;
    private static DBManager instance;

    public DBManager() {
        connect();
        initializeDB();
    }
    
    // Singleton pattern to manage the SQLite connection
    public static DBManager getInstance() throws SQLException {
        if (instance == null || instance.getConnection().isClosed()) {
            instance = new DBManager();
        }
        return instance;
    }
    
//    // Singleton pattern to manage the SQLite connection
//    public static DBManager getInstance() throws SQLException {
//        if (instance == null) {
//            instance = new DBManager();
//        } else if (instance.getConnection().isClosed()) {
//            instance.close(); // Close the connection if it's not closed already
//            instance = new DBManager(); // Then reinitialize
//        }
//        return instance;
//    }


    public Connection getConnection() {
        return this.connection;
    }

    // Establish a connection to the SQLite database
    private void connect() {
        try {
            connection = DriverManager.getConnection(DB_URL);
            System.out.println("Connection to Data Analytics Hub SQLite database established.");
        } catch (SQLException e) {
            System.err.println("Error while connecting to the SQLite database: " + e.getMessage());
            e.printStackTrace();

        }
    }

    // Set up the database tables if they don't exist
    private void initializeDB() {
        try {
            if (!tableExists("User")) {
                // Users table creation
                try (Statement stmt = connection.createStatement()) {
                    String createUsersTable = "CREATE TABLE User (" +
                                              "UserID INTEGER UNIQUE PRIMARY KEY AUTOINCREMENT," +
                                              "Username VARCHAR(100) UNIQUE," +
                                              "HashedPassword VARCHAR(100)," +
                                              "Salt VARCHAR(100)," +
                                              "FirstName VARCHAR(100)," +
                                              "LastName VARCHAR(100)," +
                                              "IsVIP BOOLEAN," +
                                              "CreatedDate DATETIME DEFAULT CURRENT_TIMESTAMP," +
                                              "UpdatedOn DATETIME DEFAULT CURRENT_TIMESTAMP)";
                    stmt.execute(createUsersTable);
                    System.out.println("User table created successfully.");
                }
            } else {
                System.out.println("User table already exists. Skipping initialization.");
            }

            if (!tableExists("Post")) {
                // Posts table creation
                try (Statement stmt = connection.createStatement()) {
                    String createPostsTable = "CREATE TABLE Post (" +
                                              "PostID INTEGER UNIQUE PRIMARY KEY AUTOINCREMENT," +
                                              "Content VARCHAR(100)," +
                                              "Author VARCHAR(100)," +
                                              "Likes INTEGER," +
                                              "Shares INTEGER," +
                                              "DateTime VARCHAR(100)," +
                                              "UserID INTEGER," +
                                              "CreatedDate DATETIME DEFAULT CURRENT_TIMESTAMP," +
                                              "UpdatedOn DATETIME DEFAULT CURRENT_TIMESTAMP," +
                                              "FOREIGN KEY(UserID) REFERENCES User(UserID))";
                    stmt.execute(createPostsTable);
                    System.out.println("Post table created successfully.");
                }
            } else {
                System.out.println("Post table already exists. Skipping initialization.");
            }

        } catch (SQLException e) {
            System.err.println("Error during database initialization: " + e.getMessage());
            e.printStackTrace();

        }
    }
    
    private boolean tableExists(String tableName) throws SQLException {
        try (ResultSet rs = connection.getMetaData().getTables(null, null, tableName, null)) {
            return rs.next();
        }
    }

    // Execute a SELECT query and return the result set
    public ResultSet executeQuery(String query) throws SQLException {
        Statement stmt = connection.createStatement();
        return stmt.executeQuery(query);
    }

    // Execute INSERT, UPDATE, or DELETE operations
    public int executeUpdate(String query) throws SQLException {
        Statement stmt = connection.createStatement();
        return stmt.executeUpdate(query);
    }

    // Close the database connection
    public void close() {
        try {
            if (connection != null) {
                connection.close();
                System.out.println("Connection to Data Analytics Hub SQLite database closed.");
            }
        } catch (SQLException e) {
            System.err.println("Error while closing the SQLite database connection: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
