package controllers;

import java.util.List;

import database.DBManager;
import exceptions.PostException;
import models.Post;
import models.User;
import services.PostService;
import services.factory.ServiceFactory;
import services.factory.ServiceFactoryImpl;

/**
 * This controller manages operations related to posts. 
 * It acts as an intermediary between the view and the service layer.
 */	
public class PostController {
	
	// Dependency Injection
	private final PostService postService;
	
	/**
     * Constructor initializes the PostService object.
     *
     * @param dbManager The database manager instance for database operations.
     */
    public PostController(DBManager dbManager) {
		 ServiceFactory serviceFactory = new ServiceFactoryImpl(dbManager);
		 this.postService = serviceFactory.createPostService();
    }

    /**
     * Add a new post.
     * 
     * @param post The post to be added.
     * @return true if the post was added successfully, false otherwise.
     * @throws PostException if there's an error during the operation.
     */
    public boolean addPost(Post post) throws PostException {
        System.out.println("Attempting to add post: " + post.toString());
        return postService.addPost(post);
    }

    /**
     * Delete a post by its ID.
     * 
     * @param post The post to be deleted.
     * @return true if the post was deleted successfully, false otherwise.
     * @throws PostException if there's an error during the operation.
     */
    public boolean deletePost(Post post) throws PostException {
        System.out.println("Attempting to delete post with ID: " + post.getPostId());
        return postService.deletePost(post);
    }

    /**
     * Retrieve a post by its ID.
     * 
     * @param postID The ID of the post to be fetched.
     * @return the post object.
     * @throws PostException if there's an error during the operation.
     */
    public Post getPostByID(int postID) throws PostException {
        System.out.println("Fetching post with ID: " + postID);
        return postService.getPostByID(postID);
    }
    
    /**
     * Retrieve the top N posts by shares for a user.
     * 
     * @param n The number of top posts to retrieve.
     * @param user The user whose posts are to be fetched.
     * @return a list of top N posts.
     * @throws PostException if there's an error during the operation.
     */
    public List<Post> getTopNPostsByShares(int n, User user) throws PostException {
        System.out.println("Fetching top " + n + " posts by shares for user");
        return postService.getTopNPostsByShares(n, user);
    }

    /**
     * Retrieve the top N posts by likes for a user.
     * 
     * @param n The number of top posts to retrieve.
     * @param user The user whose posts are to be fetched.
     * @return a list of top N posts.
     * @throws PostException if there's an error during the operation.
     */
    public List<Post> getTopNPostsByLikes(int n, User user) throws PostException {
        System.out.println("Fetching top " + n + " posts by likes for user");
        return postService.getTopNPostsByLikes(n, user);
    }
    
    /**
     * Retrieve all posts by a specific user.
     * 
     * @param user The user whose posts are to be fetched.
     * @return a list of posts.
     * @throws PostException if there's an error during the operation.
     */
    public List<Post> getPostsByUser(User user) throws PostException {
        System.out.println("Fetching all posts for user: " + user.getUsername());
        return postService.getPostsByUser(user);
    }

    /**
     * Update an existing post.
     * 
     * @param post The post to be updated.
     * @return true if the post was updated successfully, false otherwise.
     * @throws PostException if there's an error during the operation.
     */
    public boolean updatePost(Post post) throws PostException {
        System.out.println("Attempting to update post with ID: " + post.getPostId());
        return postService.updatePost(post);
    }
    
    /**
     * Retrieve all posts.
     * 
     * @return a list of all posts.
     * @throws PostException if there's an error during the operation.
     */
    public List<Post> getAllPosts() throws PostException {
        System.out.println("Fetching all posts.");
        return postService.getAllPosts();
    }
    
    /**
     * Add multiple posts in bulk.
     * 
     * @param posts The list of posts to be added.
     * @return true if all posts were added successfully, false otherwise.
     * @throws PostException if there's an error during the operation.
     */
    public boolean addBulkPosts(List<Post> posts) throws PostException {
        System.out.println("Attempting to add " + posts.size() + " posts in bulk.");
        return postService.addBulkPosts(posts);
    }
}