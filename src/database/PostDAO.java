package database;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import models.Post;
import models.User;

public class PostDAO {

    private DBManager dbManager;
    
    private static DateTimeFormatter formatter = DateTimeFormatter.ofPattern("d/MM/yyyy HH:mm");
    //private static DateTimeFormatter formatter = DateTimeFormatter.ISO_LOCAL_DATE_TIME;


    public PostDAO(DBManager dbManager) {
        this.dbManager = dbManager;
    }

    // Method to add a new post to the database
    public void addPost(Post post) throws SQLException {
        String query = "INSERT INTO Post (Content, Author, Likes, Shares, DateTime, UserID, CreatedDate, UpdatedOn)"
        		+ " VALUES (?, ?, ?, ?, ?, ?, DATETIME('now', 'localtime'), DATETIME('now', 'localtime'))";
        
        PreparedStatement preparedStatement = dbManager.getConnection().prepareStatement(query);
        preparedStatement.setString(1, post.getContent());
        preparedStatement.setString(2, post.getAuthor());
        preparedStatement.setInt(3, post.getLikes());
        preparedStatement.setInt(4, post.getShares());
        preparedStatement.setString(5, post.getDateTime().toString());
        preparedStatement.setInt(6, post.getUserId());
        
        preparedStatement.executeUpdate();
    }

    // Method to delete a post from the database based on post ID
    public void deletePost(int postID) throws SQLException {
        String query = "DELETE FROM Post WHERE PostID = ?";
        
        PreparedStatement preparedStatement = dbManager.getConnection().prepareStatement(query);
        preparedStatement.setInt(1, postID);
        
        preparedStatement.executeUpdate();
    }

    // Method to retrieve a post based on its ID
    public Post getPostByID(int postID) throws SQLException {
        String query = "SELECT * FROM Post WHERE PostID = ?";
        
        PreparedStatement preparedStatement = dbManager.getConnection().prepareStatement(query);
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
        return null;  // Return null if post not found
    }

    // Method to retrieve the top N posts with the most likes
    public List<Post> getTopNPostsByLikes(int n, User user) throws SQLException {
        String query = "SELECT * FROM Post WHERE UserID = ? ORDER BY Likes DESC LIMIT ?";
        
        PreparedStatement preparedStatement = dbManager.getConnection().prepareStatement(query);
        preparedStatement.setInt(1, user.getUserId());
        preparedStatement.setInt(2, n);
        
        ResultSet resultSet = preparedStatement.executeQuery();
        List<Post> posts = new ArrayList<>();
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
        return posts;
    }

    public List<Post> getTopNPostsByLikes(int n) throws SQLException {
        String query = "SELECT * FROM Post ORDER BY Likes DESC LIMIT ?";
        
        PreparedStatement preparedStatement = dbManager.getConnection().prepareStatement(query);
        preparedStatement.setInt(1, n);
        
        ResultSet resultSet = preparedStatement.executeQuery();
        List<Post> posts = new ArrayList<>();
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
        return posts;
    }

    // Method to retrieve the last post ID from the database
    public int getLastPostId() throws SQLException {
        String query = "SELECT MAX(PostID) as LastID FROM Post";
        ResultSet resultSet = dbManager.executeQuery(query);
        if (resultSet.next()) {
            return resultSet.getInt("LastID");
        }
        return 	0; // return 0 if no posts are found
    }

    // Method to update an existing post in the database using PreparedStatement
    public void updatePost(Post post) throws SQLException {
        String query = "UPDATE Post SET Content = ?, Author = ?, Likes = ?, Shares = ?, DateTime = ?, UpdatedOn = DATETIME('now', 'localtime')"
        		+ " WHERE PostID = ? AND UserID = ?";

        PreparedStatement preparedStatement = dbManager.getConnection().prepareStatement(query);
        
        preparedStatement.setString(1, post.getContent());
        preparedStatement.setString(2, post.getAuthor());
        preparedStatement.setInt(3, post.getLikes());
        preparedStatement.setInt(4, post.getShares());
        preparedStatement.setString(5, post.getDateTime().format(formatter));
        preparedStatement.setInt(6, post.getPostId());
        preparedStatement.setInt(7, post.getUserId());

        preparedStatement.executeUpdate();
    }
    
    // Method to retrieve posts by a specific user
    public List<Post> getPostsByUser(User user) throws SQLException {
        String query = "SELECT * FROM Post WHERE UserID = ?";

        PreparedStatement preparedStatement = dbManager.getConnection().prepareStatement(query);
        preparedStatement.setInt(1, user.getUserId());

        ResultSet resultSet = preparedStatement.executeQuery();
        List<Post> posts = new ArrayList<>();
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
        return posts;
    }
    
    // Method to all retrieve posts
    public List<Post> getAllPosts() throws SQLException {
        String query = "SELECT * FROM Post";

        PreparedStatement preparedStatement = dbManager.getConnection().prepareStatement(query);

        ResultSet resultSet = preparedStatement.executeQuery();
        List<Post> posts = new ArrayList<>();
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
        return posts;
    }

    // Method to retrieve the top N posts with the most shares
    public List<Post> getTopNPostsByShares(int n, User user) throws SQLException {
        String query = "SELECT * FROM Post WHERE UserID = ? ORDER BY Shares DESC LIMIT ?";

        PreparedStatement preparedStatement = dbManager.getConnection().prepareStatement(query);
        preparedStatement.setInt(1, user.getUserId());
        preparedStatement.setInt(2, n);

        ResultSet resultSet = preparedStatement.executeQuery();
        List<Post> posts = new ArrayList<>();
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
        return posts;
    }
    
    public List<Post> getTopNPostsByShares(int n) throws SQLException {
        String query = "SELECT * FROM Post ORDER BY Shares DESC LIMIT ?";

        PreparedStatement preparedStatement = dbManager.getConnection().prepareStatement(query);
        preparedStatement.setInt(1, n);

        ResultSet resultSet = preparedStatement.executeQuery();
        List<Post> posts = new ArrayList<>();
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
        return posts;
    }
}

