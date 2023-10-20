package test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import database.DBManager;
import database.PostDAO;
import exceptions.DatabaseException;
import exceptions.PostException;
import models.Post;
import services.PostService;
import services.PostServiceImpl;

/**
 * A test suite for the PostService class, ensuring its correctness in
 * managing and querying post data from the database.
 */
public class PostServiceTest {

	private PostService postService;
    private DBManager dbManager;
    private PostDAO postDAO;

    @Before
    public void setUp() throws DatabaseException {
    	System.out.println("Setting up the PostServiceTest test environment...");
        dbManager = DBManager.getInstance();
        postService = new PostServiceImpl(dbManager);
        this.postDAO = new PostDAO(dbManager);
    }

    @Test
    public void testAddPost() throws PostException, SQLException {
    	System.out.println("Running testAddBulkPosts...");
        Post post = new Post("TestPostForAdd", "TestAuthorForAdd", 10, 5, "12/01/2023 09:00", 1);
        assertTrue(postService.addPost(post));
        
        // Clean up: Remove the test post from the database
        assertTrue(postDAO.deletePostByAuthor(post.getAuthor()));
    }

    @Test
    public void testDeletePost() throws SQLException, PostException {
    	System.out.println("Running testDeletePost...");
        Post post = new Post("TestPostForDelete", "TestAuthorForDelete", 10, 5, "12/01/2023 09:00", 1);
        assertTrue(postService.addPost(post));
        assertTrue(postDAO.deletePostByAuthor(post.getAuthor()));
    }

    @Test
    public void testGetTopNPostsByShares() throws SQLException, PostException {
    	System.out.println("Running testGetTopNPostsByShares...");
        Post post = new Post("TestPostForShares", "TestAuthorForShares", 10, 1000, "12/01/2023 09:00", 1);
        assertTrue(postService.addPost(post));
        List<Post> topPosts = postService.getTopNPostsByShares(5, null); // Get top 5 posts by shares
        assertFalse(topPosts.isEmpty());
        
        // Clean up: Remove the test post from the database
        assertTrue(postDAO.deletePostByAuthor(post.getAuthor()));
    }

    @Test
    public void testGetTopNPostsByLikes() throws SQLException, PostException {
    	System.out.println("Running testGetTopNPostsByLikes...");
        Post post = new Post("TestPostForLikes", "TestAuthorForLikes", 1000, 5, "12/01/2023 09:00", 1);
        assertTrue(postService.addPost(post));
        List<Post> topPosts = postService.getTopNPostsByLikes(5, null); // Get top 5 posts by likes
        assertFalse(topPosts.isEmpty());
        
        // Clean up: Remove the test post from the database
        assertTrue(postDAO.deletePostByAuthor(post.getAuthor()));
    }

    @Test
    public void testGetAllPosts() throws SQLException, PostException {
    	System.out.println("Running testGetAllPosts...");
        Post post = new Post("TestPostForGetAll", "TestAuthorForGetAll", 10, 5, "12/01/2023 09:00", 1);
        assertTrue(postService.addPost(post));
        List<Post> allPosts = postService.getAllPosts();
        assertFalse(allPosts.isEmpty());
        
        // Clean up: Remove the test post from the database
        assertTrue(postDAO.deletePostByAuthor(post.getAuthor()));
    }

    @Test
    public void testAddBulkPosts() throws PostException, SQLException {
        System.out.println("Running testAddBulkPosts...");
        List<Post> bulkPosts = new ArrayList<>();
        bulkPosts.add(new Post("BulkTest1", "BulkAuthor1", 10, 5, "12/01/2023 09:00", 1));
        bulkPosts.add(new Post("BulkTest2", "BulkAuthor2", 20, 10, "13/01/2023 09:00", 2));
        assertTrue(postService.addBulkPosts(bulkPosts));
        
        // Clean up: Remove the test posts from the database
        for (Post p : bulkPosts) {
            assertTrue(postDAO.deletePostByAuthor(p.getAuthor()));
        }
    }
}