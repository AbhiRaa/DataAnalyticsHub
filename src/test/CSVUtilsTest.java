package test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import exceptions.CsvLoadingException;
import models.Post;
import models.User;
import utils.CSVUtils;

/**
 * A test suite for CSVUtils, ensuring its correctness in reading and writing CSV files 
 * and handling various data anomalies.
 */
public class CSVUtilsTest {
	
	private User testUser;

	@Before
	public void setUp() throws Exception {
		System.out.println("Setting up CSVUtilsTest test user...");
	    testUser = new User("testuser", "dummyhashedpassword", "dummysalt", "Test", "User", false);
	    // Set a user ID if necessary for your test cases.
	    testUser.setUserId(1);
	}
	
	/**
	 * Test reading a valid CSV file and retrieving posts.
	 */
	@Test
    public void testReadPostsValidCSV() {
		System.out.println("Running testReadPostsValidCSV...");
        try {
            List<Post> posts = CSVUtils.readPosts("src/csv/test.csv", testUser);
            assertFalse(posts.isEmpty());
        } catch (CsvLoadingException e) {
            System.err.println("Error in testReadPostsValidCSV: " + e.getMessage());
            fail(e.getMessage());
        }
    }

	/**
	 * Test reading a CSV file with missing fields.
	 * @throws CsvLoadingException 
	 */
    @Test(expected = CsvLoadingException.class)
    public void testReadPostsMissingFields() throws CsvLoadingException {
    	System.out.println("Running testReadPostsMissingFields...");
        CSVUtils.readPosts("src/csv/missingData.csv", testUser);
    }

	/**
	 * Test reading a CSV file with incorrect data types.
	 * @throws CsvLoadingException 
	 */
    @Test(expected = CsvLoadingException.class)
    public void testReadPostsIncorrectDataTypes() throws CsvLoadingException {
    	System.out.println("Running testReadPostsIncorrectDataTypes...");
        CSVUtils.readPosts("src/csv/incorrectData.csv", testUser);
    }
    
	/**
	 * Test reading a CSV file with extra columns.
	 * @throws CsvLoadingException 
	 */
    @Test(expected = CsvLoadingException.class)
    public void testReadPostsWithExtraColumns() throws CsvLoadingException {
    	System.out.println("Running testReadPostsWithExtraColumns...");
        CSVUtils.readPosts("src/csv/extraColumns.csv", testUser);
    }
    
	/**
	 * Test reading a CSV file with fewer columns than expected.
	 * @throws CsvLoadingException 
	 */
    @Test(expected = CsvLoadingException.class)
    public void testReadPostsWithFewerColumns() throws CsvLoadingException {
    	System.out.println("Running testReadPostsWithFewerColumns...");
        CSVUtils.readPosts("src/csv/fewerColumns.csv", testUser);
    }

	/**
	 * Test saving a post to a CSV file.
	 */
    @Test
    public void testSavePostToFile() {
    	System.out.println("Running testSavePostToFile...");
        Post post = new Post("testContent", "testuser", 10, 5, "12/01/2023 09:00", 1);
        File testFile = new File("src/csv/output.csv");
        boolean result = CSVUtils.savePostToFile(post, testFile);
        assertTrue(result);
        
        try {
            List<String> lines = Files.readAllLines(Paths.get(testFile.getAbsolutePath()));
            assertEquals("ID,Content,Author,Likes,Shares,DateTime", lines.get(0));
            assertTrue(lines.get(1).contains("testContent"));
        } catch (IOException e) {
            System.err.println("Error in testSavePostToFile: " + e.getMessage());
            fail("Failed to read the output file.");
        }
    }
}