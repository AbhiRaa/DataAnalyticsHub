package database;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import exceptions.PostException;
import models.Post;
import models.User;

/**
 * The PostDAO class provides data access methods related to the Post model.
 * It provides CRUD operations for posts and some additional query methods.
 */
public class PostDAO {

    private DBManager dbManager;
    
    private static DateTimeFormatter formatter = DateTimeFormatter.ofPattern("d/MM/yyyy HH:mm");
    
    /**
     * Constructs a new PostDAO with a DBManager instance.
     * 
     * @param dbManager The database manager to handle database operations.
     */
    public PostDAO(DBManager dbManager) {
        this.dbManager = dbManager;
    }

    /**
     * Adds a new post to the database.
     * 
     * @param post The post to be added.
     * @throws PostException if there's an error during the operation.
     */
    public void addPost(Post post) throws PostException {
        String query = "INSERT INTO Post (Content, Author, Likes, Shares, DateTime, UserID, CreatedDate, UpdatedOn)"
        		+ " VALUES (?, ?, ?, ?, ?, ?, DATETIME('now', 'localtime'), DATETIME('now', 'localtime'))";
        
        try {
        	PreparedStatement preparedStatement = dbManager.getConnection().prepareStatement(query);
            preparedStatement.setString(1, post.getContent());
            preparedStatement.setString(2, post.getAuthor());
            preparedStatement.setInt(3, post.getLikes());
            preparedStatement.setInt(4, post.getShares());
            preparedStatement.setString(5, post.getDateTime().toString());
            preparedStatement.setInt(6, post.getUserId());
            
            preparedStatement.executeUpdate();
            
            System.out.println("Post added successfully: " + post.getContent());

        } catch(SQLException e) {
            System.err.println("Error while adding post: " + e.getMessage());
            throw new PostException("Error while adding post.", e);
        }
        
    }

    /**
     * Deletes a post from the database based on its ID.
     * 
     * @param postID The ID of the post to be deleted.
     * @throws PostException if there's an error during the operation.
     */
    public void deletePost(int postID) throws PostException {
        String query = "DELETE FROM Post WHERE PostID = ?";
        
        try {
        	PreparedStatement preparedStatement = dbManager.getConnection().prepareStatement(query);
            preparedStatement.setInt(1, postID);
            
            int affectedRows = preparedStatement.executeUpdate();
            if (affectedRows > 0) {
                System.out.println("Post with ID " + postID + " deleted successfully.");
            } else {
                System.out.println("No post found with ID " + postID);
            }
            
        } catch(SQLException e) {
            System.err.println("Error while deleting post: " + e.getMessage());
            throw new PostException("Error while deleting post.", e);
        }
        
    }

    /**
     * Retrieves a post based on its ID.
     * 
     * @param postID The ID of the post to be fetched.
     * @return The post corresponding to the given ID or null if not found.
     * @throws PostException if there's an error during the operation.
     */
    public Post getPostByID(int postID) throws PostException {
        String query = "SELECT * FROM Post WHERE PostID = ?";
        
        try (PreparedStatement preparedStatement = dbManager.getConnection().prepareStatement(query)) {
            preparedStatement.setInt(1, postID);
            
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                return new Post(
                    resultSet.getInt("PostID"),
                    resultSet.getString("Content"),
                    resultSet.getString("Author"),
                    resultSet.getInt("Likes"),
                    resultSet.getInt("Shares"),
                    resultSet.getString("DateTime"),
                    resultSet.getInt("UserID")
                );
            }
        } catch(SQLException e) {
            System.err.println("Error while fetching post with ID " + postID + ": " + e.getMessage());
            throw new PostException("Error while fetching post.", e);
        }
        return null;  // Return null if post not found
    }

    /**
     * Retrieves the top N posts with the most likes for a specific user.
     * 
     * @param n The number of top posts to be fetched.
     * @param user The user whose top N posts are to be fetched.
     * @return List of top N posts by the user.
     * @throws PostException if there's an error during the operation.
     */
    public List<Post> getTopNPostsByLikes(int n, User user) throws PostException {
        String query = "SELECT * FROM Post WHERE UserID = ? ORDER BY Likes DESC LIMIT ?";
        
        List<Post> posts = new ArrayList<>();
        try (PreparedStatement preparedStatement = dbManager.getConnection().prepareStatement(query)) {
            preparedStatement.setInt(1, user.getUserId());
            preparedStatement.setInt(2, n);

            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                posts.add(new Post(
                    resultSet.getInt("PostID"),
                    resultSet.getString("Content"),
                    resultSet.getString("Author"),
                    resultSet.getInt("Likes"),
                    resultSet.getInt("Shares"),
                    resultSet.getString("DateTime"),
                    resultSet.getInt("UserID")
                ));
            }
        } catch(SQLException e) {
            System.err.println("Error while fetching top " + n + " posts by likes for user " + user.getUsername() + ": " + e.getMessage());
            throw new PostException("Error while fetching top posts by likes for user.", e);
        }
        return posts;
    }
    
    /**
     * Retrieves the top N posts with the most likes from the entire database.
     * 
     * @param n The number of top posts to be fetched.
     * @return List of top N posts.
     * @throws PostException if there's an error during the operation.
     */
    public List<Post> getTopNPostsByLikes(int n) throws PostException {
        String query = "SELECT * FROM Post ORDER BY Likes DESC LIMIT ?";
        
        List<Post> posts = new ArrayList<>();
        try (PreparedStatement preparedStatement = dbManager.getConnection().prepareStatement(query)) {
            preparedStatement.setInt(1, n);

            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                posts.add(new Post(
                    resultSet.getInt("PostID"),
                    resultSet.getString("Content"),
                    resultSet.getString("Author"),
                    resultSet.getInt("Likes"),
                    resultSet.getInt("Shares"),
                    resultSet.getString("DateTime"),
                    resultSet.getInt("UserID")
                ));
            }
        } catch(SQLException e) {
            System.err.println("Error while fetching top " + n + " posts by likes: " + e.getMessage());
            throw new PostException("Error while fetching top posts by likes.", e);
        }
        return posts;
    }

    /**
     * Updates an existing post in the database.
     * 
     * @param post The post to be updated.
     * @throws PostException if there's an error during the update operation.
     */
    public void updatePost(Post post) throws PostException {
        String query = "UPDATE Post SET Content = ?, Author = ?, Likes = ?, Shares = ?, DateTime = ?, UpdatedOn = DATETIME('now', 'localtime')"
        		+ " WHERE PostID = ? AND UserID = ?";

        try (PreparedStatement preparedStatement = dbManager.getConnection().prepareStatement(query)) {
            preparedStatement.setString(1, post.getContent());
            preparedStatement.setString(2, post.getAuthor());
            preparedStatement.setInt(3, post.getLikes());
            preparedStatement.setInt(4, post.getShares());
            preparedStatement.setString(5, post.getDateTime().format(formatter));
            preparedStatement.setInt(6, post.getPostId());
            preparedStatement.setInt(7, post.getUserId());

            preparedStatement.executeUpdate();
            System.out.println("Post with ID " + post.getPostId() + " updated successfully.");
        } catch(SQLException e) {
            System.err.println("Error while updating post with ID " + post.getPostId() + ": " + e.getMessage());
            throw new PostException("Error while updating post.", e);
        }
    }
    
    /**
     * Retrieves posts created by a specific user.
     * 
     * @param user The user whose posts are to be fetched.
     * @return List of posts by the user.
     * @throws PostException if there's an error during the operation.
     */
    public List<Post> getPostsByUser(User user) throws PostException {
        String query = "SELECT * FROM Post WHERE UserID = ?";

        List<Post> posts = new ArrayList<>();
        try (PreparedStatement preparedStatement = dbManager.getConnection().prepareStatement(query)) {
            preparedStatement.setInt(1, user.getUserId());

            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                posts.add(new Post(
                    resultSet.getInt("PostID"),
                    resultSet.getString("Content"),
                    resultSet.getString("Author"),
                    resultSet.getInt("Likes"),
                    resultSet.getInt("Shares"),
                    resultSet.getString("DateTime"),
                    resultSet.getInt("UserID")
                ));
            }
        } catch(SQLException e) {
            System.err.println("Error while fetching posts for user " + user.getUsername() + ": " + e.getMessage());
            throw new PostException("Error while fetching posts for user.", e);
        }
        return posts;
    }
    
    /**
     * Retrieves all the posts from the database.
     * 
     * @return List of all posts.
     * @throws PostException if there's an error during the operation.
     */
    public List<Post> getAllPosts() throws PostException {
        String query = "SELECT * FROM Post";

        List<Post> posts = new ArrayList<>();
        try (PreparedStatement preparedStatement = dbManager.getConnection().prepareStatement(query)) {
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                posts.add(new Post(
                    resultSet.getInt("PostID"),
                    resultSet.getString("Content"),
                    resultSet.getString("Author"),
                    resultSet.getInt("Likes"),
                    resultSet.getInt("Shares"),
                    resultSet.getString("DateTime"),
                    resultSet.getInt("UserID")
                ));
            }
        } catch(SQLException e) {
            System.err.println("Error while fetching all posts: " + e.getMessage());
            throw new PostException("Error while fetching all posts.", e);
        }
        return posts;
    }

    /**
     * Retrieves the top N posts with the most shares by a specific user.
     * 
     * @param n The number of top posts to be fetched.
     * @param user The user whose top N posts are to be fetched.
     * @return List of top N posts by the user.
     * @throws PostException if there's an error during the operation.
     */
    public List<Post> getTopNPostsByShares(int n, User user) throws PostException {
        String query = "SELECT * FROM Post WHERE UserID = ? ORDER BY Shares DESC LIMIT ?";

        List<Post> posts = new ArrayList<>();
        try (PreparedStatement preparedStatement = dbManager.getConnection().prepareStatement(query)) {
            preparedStatement.setInt(1, user.getUserId());
            preparedStatement.setInt(2, n);

            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                posts.add(new Post(
                    resultSet.getInt("PostID"),
                    resultSet.getString("Content"),
                    resultSet.getString("Author"),
                    resultSet.getInt("Likes"),
                    resultSet.getInt("Shares"),
                    resultSet.getString("DateTime"),
                    resultSet.getInt("UserID")
                ));
            }
        } catch(SQLException e) {
            System.err.println("Error while fetching top " + n + " posts by shares for user " + user.getUsername() + ": " + e.getMessage());
            throw new PostException("Error while fetching top posts by shares for user.", e);
        }
        return posts;
    }

    
    /**
     * Retrieves the top N posts with the most shares from the entire database.
     * 
     * @param n The number of top posts to be fetched.
     * @return List of top N posts.
     * @throws PostException if there's an error during the operation.
     */
    public List<Post> getTopNPostsByShares(int n) throws PostException {
        String query = "SELECT * FROM Post ORDER BY Shares DESC LIMIT ?";

        List<Post> posts = new ArrayList<>();
        try (PreparedStatement preparedStatement = dbManager.getConnection().prepareStatement(query)) {
            preparedStatement.setInt(1, n);

            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                posts.add(new Post(
                    resultSet.getInt("PostID"),
                    resultSet.getString("Content"),
                    resultSet.getString("Author"),
                    resultSet.getInt("Likes"),
                    resultSet.getInt("Shares"),
                    resultSet.getString("DateTime"),
                    resultSet.getInt("UserID")
                ));
            }
        } catch(SQLException e) {
            System.err.println("Error while fetching top " + n + " posts by shares: " + e.getMessage());
            throw new PostException("Error while fetching top posts by shares.", e);
        }
        return posts;
    }
    
    /**
     * For TEST Purpose
     * Deletes a post from the database based on the provided author.
     * 
     * @param author The post with author to be deleted.
     * @return true if the post was successfully deleted, false otherwise.
     */
    public boolean deletePostByAuthor(String author) throws SQLException {
        String query = "DELETE FROM Post WHERE Author = ?";
        
        PreparedStatement preparedStatement = dbManager.getConnection().prepareStatement(query);
        preparedStatement.setString(1, author);
        
        int affectedRows = preparedStatement.executeUpdate();
        return affectedRows > 0;
    }
}

