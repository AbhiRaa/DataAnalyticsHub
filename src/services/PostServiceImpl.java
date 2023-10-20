package services;

import java.util.List;

import database.DBManager;
import database.PostDAO;
import exceptions.PostException;
import models.Post;
import models.User;

/**
 * This class provides an implementation for the PostService interface.
 * It provides services related to the Post entity, such as adding, deleting, updating, and fetching posts.
 */
public class PostServiceImpl implements PostService {
	
	private PostDAO postDAO;
	
	/**
     * Constructor initializes the PostDAO object.
     *
     * @param dbManager The database manager instance for database operations.
     */
    public PostServiceImpl(DBManager dbManager) {
        this.postDAO = new PostDAO(dbManager);
    }

    @Override
    public boolean addPost(Post post) throws PostException {
    	try {
            postDAO.addPost(post);
            System.out.println("Successfully added post: " + post.toString());
            return true;
        } catch (Exception e) {
            System.err.println("Error adding post: " + e.getMessage());
            throw new PostException("Failed to add post.", e);
        }
    }

    @Override
    public boolean deletePost(Post post) throws PostException {
    	try {
            postDAO.deletePost(post.getPostId());
            System.out.println("Successfully deleted post with ID: " + post.getPostId());
            return true;
        } catch (Exception e) {
            System.err.println("Error deleting post: " + e.getMessage());
            throw new PostException("Failed to delete post.", e);
        }
    }

	@Override
	public Post getPostByID(int postID) throws PostException {
		try {
            return postDAO.getPostByID(postID);
        } catch (Exception e) {
            System.err.println("Error fetching post by ID: " + e.getMessage());
            throw new PostException("Failed to fetch post by ID.", e);
        }
	}
	
	@Override
	public List<Post> getTopNPostsByShares(int n, User user) throws PostException {
		try {
            if (user != null) {
                return postDAO.getTopNPostsByShares(n, user);
            } else {
                return postDAO.getTopNPostsByShares(n);
            }
        } catch (Exception e) {
            System.err.println("Error fetching top N posts by shares: " + e.getMessage());
            throw new PostException("Failed to fetch top posts by shares.", e);
        }
	}
	
	@Override
	public List<Post> getTopNPostsByLikes(int n, User user) throws PostException {
		try {
            if (user != null) {
                return postDAO.getTopNPostsByLikes(n, user);
            } else {
                return postDAO.getTopNPostsByLikes(n);
            }
        } catch (Exception e) {
            System.err.println("Error fetching top N posts by likes: " + e.getMessage());
            throw new PostException("Failed to fetch top posts by likes.", e);
        }
	}
	
	@Override
	public List<Post> getPostsByUser(User user) throws PostException {
		try {
            return postDAO.getPostsByUser(user);
        } catch (Exception e) {
            System.err.println("Error fetching posts by user: " + e.getMessage());
            throw new PostException("Failed to fetch posts by user.", e);
        }
	}
	
	@Override
	public boolean updatePost(Post post) throws PostException {
		try {
            postDAO.updatePost(post);
            System.out.println("Successfully updated post: " + post.toString());
            return true;
        } catch (Exception e) {
            System.err.println("Error updating post: " + e.getMessage());
            throw new PostException("Failed to update post.", e);
        }
	}
	
	@Override
	public List<Post> getAllPosts() throws PostException {
		try {
            return postDAO.getAllPosts();
        } catch (Exception e) {
            System.err.println("Error fetching all posts: " + e.getMessage());
            throw new PostException("Failed to fetch all posts.", e);
        }
	}
	
	@Override
	public boolean addBulkPosts(List<Post> posts) throws PostException {
		try {
            for (Post post : posts) {
                postDAO.addPost(post);
            }
            System.out.println("Successfully added bulk posts.");
            return true;
        } catch (Exception e) {
            System.err.println("Error adding bulk posts: " + e.getMessage());
            throw new PostException("Failed to add bulk posts.", e);
        }
	}
}
