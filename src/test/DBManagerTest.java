package test;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.lang.reflect.Method;
import java.sql.Connection;
import java.sql.ResultSet;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import database.DBManager;
import exceptions.DatabaseException;

/**
 * A test suite for the DBManager class, ensuring its correctness in connecting to, querying, 
 * and updating the database, as well as its internal functionalities.
 */
public class DBManagerTest {

	private DBManager dbManager;

    @Before
    public void setUp() throws DatabaseException {
    	System.out.println("Setting up DBManager for test...");
        dbManager = DBManager.getInstance();
    }

    /**
     * Test to check if the DBManager can establish a connection to the database.
     */
    @Test
    public void testConnection() {
    	System.out.println("Running testConnection...");
        Connection connection = dbManager.getConnection();
        assertNotNull(connection);
    }

    /**
     * Test to check if the DBManager can execute a query and retrieve results.
     */
    @Test
    public void testExecuteQuery() throws DatabaseException {
    	System.out.println("Running testExecuteQuery...");
        ResultSet rs = dbManager.executeQuery("SELECT * FROM User");
        assertNotNull(rs);
    }

    /**
     * Test to check if the DBManager can execute an update on the database.
     */
    @Test
    public void testExecuteUpdate() throws DatabaseException {
    	System.out.println("Running testExecuteUpdate...");
        int result = dbManager.executeUpdate("INSERT INTO User (Username, HashedPassword, Salt, FirstName, LastName, IsVIP) VALUES ('testUser', 'hashedPassword', 'salt', 'John', 'Doe', 0)");
        assertTrue(result > 0);
    }

    /**
     * Test to check if the DBManager's private method "tableExists" works correctly.
     * This test uses the Reflection API to access the private method.
     */
    @Test
    public void testTableExistsUsingReflection() throws Exception {
    	System.out.println("Running testTableExistsUsingReflection...");
        Method method = DBManager.class.getDeclaredMethod("tableExists", String.class);
        method.setAccessible(true);
        boolean exists = (boolean) method.invoke(dbManager, "User");
        assertTrue(exists);
    }

    @After
    public void tearDown() throws DatabaseException {
    	System.out.println("Tearing down...");
        // Clean up any test data added to the database
        dbManager.executeUpdate("DELETE FROM User WHERE Username = 'testUser'");
        dbManager.close();
    }
}