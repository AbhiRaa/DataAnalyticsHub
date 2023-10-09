package controllers;

import java.sql.SQLException;
import java.util.List;

import database.DBManager;
import database.PostDAO;
import models.Post;

public class PostController {

    private PostDAO postDAO;

    public PostController(DBManager dbManager) {
        this.postDAO = new PostDAO(dbManager);
    }

    // Add a new post
    public boolean addPost(Post post) {
        try {
            // Set the next available post ID for the new post
            //post.setPostId(getNextPostId());
            postDAO.addPost(post);
            return true;
        } catch (SQLException e) {
            // Handle exception, e.g., log it or inform the user
            e.printStackTrace();

        }
        return false;
    }


 // Delete a post by ID
    public boolean deletePost(int postID) {
        try {
            postDAO.deletePost(postID);
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

    // Retrieve the top N posts by likes
    public List<Post> getTopNPostsByLikes(int n) {
        try {
            return postDAO.getTopNPostsByLikes(n);
        } catch (SQLException e) {
            // Handle exception
        }
        return null;
    }
    
//    // Get the next available post ID
//    public int getNextPostId() {
//        try {
//            return postDAO.getLastPostId() + 1;
//        } catch (SQLException e) {
//            // Handle exception
//        }
//        return 1; // default to 1 if no posts are found or an error occurs
//    }

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

}

