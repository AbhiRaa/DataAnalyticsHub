package database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import exceptions.DatabaseException;

/**
 * DBManager is responsible for managing the database connection, 
 * initializing the database tables if they don't exist, 
 * and executing database queries and updates.
 * It follows the Singleton pattern to ensure only one instance 
 * exists throughout the application's lifetime.
 */
public class DBManager {
	
    private static final String DB_URL = "jdbc:sqlite:/Users/abhinav/Development/advance_programming/Assignment2/src/database/DataAnalyticsHub.db";
    private Connection connection;
    private static DBManager instance;
    
    // Private constructor ensures that no other class can directly instantiate it.
    private DBManager() {
        connect();
        initializeDB();
    }
    
    /* 
     * Lazy Initialization with Check, the instance is created only when it's needed. If you never call getInstance(), the instance will never be created.
     * By checking if the connection is closed, it attempt to ensure that the DB connection is always fresh when accessed.
     * Reference: https://www.digitalocean.com/community/tutorials/java-singleton-design-pattern-best-practices-examples
     * 
     * @return DBManager instance
     * @throws SQLException if there's an error during database operation
     */
    public static DBManager getInstance() throws DatabaseException {
    	try {
            if (instance == null || instance.getConnection().isClosed()) {
                instance = new DBManager();
            }
        } catch (SQLException e) {
            throw new DatabaseException("Error while initializing DBManager.", e);
        }
        return instance;
    }
    
    /*
     * Resource Handling in Singleton, every time getConnection is called, it checks if the connection is active. If not, it re-establishes the connection. 
     * This ensures that a healthy connection is always returned.
     * 
     * @return active database connection
     */
    public Connection getConnection() {
    	try {
            if (this.connection == null || this.connection.isClosed()) {
                connect();  // Reconnect if the connection is null or closed
            }
        } catch (SQLException e) {
            System.err.println("Error checking connection status: " + e.getMessage());
            e.printStackTrace();
        }
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

    // Initializes the database tables if they don't exist.
    private void initializeDB() {
        try {
            if (!tableExists("User")) {
                // User table creation
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
                // Post table creation
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
    
    // Checks if a table exists in the database.
    private boolean tableExists(String tableName) throws SQLException {
        try (ResultSet rs = connection.getMetaData().getTables(null, null, tableName, null)) {
            return rs.next();
        }
    }

    // Execute a SELECT query and return the result set
    public ResultSet executeQuery(String query) throws DatabaseException {
    	try {
            Statement stmt = connection.createStatement();
            return stmt.executeQuery(query);
        } catch (SQLException e) {
            throw new DatabaseException("Error executing query: " + query, e);
        }
    }

    // Execute INSERT, UPDATE, or DELETE operations
    public int executeUpdate(String query) throws DatabaseException {
    	try {
            Statement stmt = connection.createStatement();
            return stmt.executeUpdate(query);
        } catch (SQLException e) {
            throw new DatabaseException("Error executing update: " + query, e);
        }
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
