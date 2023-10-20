package services;

import java.util.List;

import exceptions.PostException;
import models.Post;
import models.User;

/**
 * Interface defining the contract for services related to the Post entity.
 * It provides methods for various operations like adding, deleting, updating, and fetching posts.
 */
public interface PostService {
	
	/**
     * Adds a new post.
     * 
     * @param post The post to be added.
     * @return true if the post was added successfully, false otherwise.
     * @throws PostException if there's an error during the addition.
     */
    boolean addPost(Post post) throws PostException;

    /**
     * Deletes a specific post.
     * 
     * @param post The post to be deleted.
     * @return true if the post was deleted successfully, false otherwise.
     * @throws PostException if there's an error during the deletion.
     */
    boolean deletePost(Post post) throws PostException;

    /**
     * Fetches a post based on its ID.
     * 
     * @param postID The ID of the post to be fetched.
     * @return The post with the given ID.
     * @throws PostException if there's an error during the fetch operation.
     */
    Post getPostByID(int postID) throws PostException;

    /**
     * Fetches the top N posts with the most shares for a specific user.
     * 
     * @param n The number of top posts to be fetched.
     * @param user The user whose top N posts are to be fetched.
     * @return List of top N posts by shares for the user.
     * @throws PostException if there's an error during the operation.
     */
    List<Post> getTopNPostsByShares(int n, User user) throws PostException;

    /**
     * Fetches the top N posts with the most likes for a specific user.
     * 
     * @param n The number of top posts to be fetched.
     * @param user The user whose top N posts are to be fetched.
     * @return List of top N posts by likes for the user.
     * @throws PostException if there's an error during the operation.
     */
    List<Post> getTopNPostsByLikes(int n, User user) throws PostException;

    /**
     * Fetches all posts created by a specific user.
     * 
     * @param user The user whose posts are to be fetched.
     * @return List of posts by the user.
     * @throws PostException if there's an error during the operation.
     */
    List<Post> getPostsByUser(User user) throws PostException;

    /**
     * Updates a specific post.
     * 
     * @param post The post to be updated.
     * @return true if the post was updated successfully, false otherwise.
     * @throws PostException if there's an error during the update operation.
     */
    boolean updatePost(Post post) throws PostException;

    /**
     * Fetches all posts from the system.
     * 
     * @return List of all posts.
     * @throws PostException if there's an error during the fetch operation.
     */
    List<Post> getAllPosts() throws PostException;

    /**
     * Adds multiple posts in bulk.
     * 
     * @param posts List of posts to be added.
     * @return true if all posts were added successfully, false otherwise.
     * @throws PostException if there's an error during the bulk addition.
     */
    boolean addBulkPosts(List<Post> posts) throws PostException;
}
