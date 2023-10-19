package controllers;

import java.sql.SQLException;
import java.util.List;

import database.DBManager;
import database.PostDAO;
import models.Post;
import models.User;

public class PostController {

    private PostDAO postDAO;

    public PostController(DBManager dbManager) {
        this.postDAO = new PostDAO(dbManager);
    }

    // Add a new post
    public boolean addPost(Post post) {
        try {
            
            postDAO.addPost(post);
            return true;
        } catch (SQLException e) {
            // Handle exception, e.g., log it or inform the user
            e.printStackTrace();

        }
        return false;
    }


 // Delete a post by ID
    public boolean deletePost(Post post) {
        try {
            postDAO.deletePost(post.getPostId());
            return true;
        } catch (SQLException e) {
            // Handle exception
        }
        return false;
    }

 // Retrieve a post by its ID
    public Post getPostByID(int postID) {
        try {
            return postDAO.getPostByID(postID);
        } catch (SQLException e) {
            // Handle exception
        }
        return null;
    }
    
    // Retrieve the top N posts by shares
    public List<Post> getTopNPostsByShares(int n, User user) {
        try {
            if (user != null)
            	return postDAO.getTopNPostsByShares(n, user);
            else
            	return postDAO.getTopNPostsByShares(n);
        } catch (SQLException e) {
            // Handle exception
        }
        return null;
    }

    // Retrieve the top N posts by likes
    public List<Post> getTopNPostsByLikes(int n, User user) {
        try {
        	if (user != null)
        		return postDAO.getTopNPostsByLikes(n, user);
        	else 
        		return postDAO.getTopNPostsByLikes(n);
        } catch (SQLException e) {
            // Handle exception
        }
        return null;
    }
    
    public List<Post> getPostsByUser(User user) {
    	try {
    		return postDAO.getPostsByUser(user);
        } catch (SQLException e) {
            // Handle exception
        }
        return null;
        
    }

    // Update an existing post
    public boolean updatePost(Post post) {
        try {
            postDAO.updatePost(post);
            return true;
        } catch (SQLException e) {
            // Handle exception
            e.printStackTrace();
        	
        }
        return false;
    }
    
    public List<Post> getAllPosts() {
    	try {
    		return postDAO.getAllPosts();
        } catch (SQLException e) {
            // Handle exception
        }
        return null;
        
    }
    
    public boolean addBulkPosts(List<Post> posts) {
        try {
            for (Post post : posts) {
                postDAO.addPost(post);
            }
            return true;
        } catch (Exception e) {
            System.err.println("Error adding bulk posts: " + e.getMessage());
            return false;
        }
    }

}

