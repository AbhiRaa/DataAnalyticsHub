package views.facade;

import java.io.File;
import java.util.List;

import exceptions.CsvLoadingException;
import javafx.scene.control.Alert.AlertType;
import models.Post;
import models.User;

public interface GUIViewFacadeInterface {
	
	// Navigation Methods
    public void navigateToLogin();

    public void navigateToSignup();

    public void navigateToDashboard(User user);
    
    public void navigateToAddPost(User user);
    
    public void navigateToMyPosts(User user);
    
    public void navigateToEditUserProfile(User user);
    
    public void navigateToUpgradeToVIP(User user);
    
    public void navigateToVisualization(User user);
    
    public void navigateToBulkImports(User user);
 
    public void navigateToEditPost(User user, Post post);

    // Alerts & Messages
    public void showAlert(AlertType alertType, String title, String message);

    // Post-related methods
    public void addPost(String content, String author, int likes, int shares, String DateTime, User user);

    public void editPost(int postId, String content, String author, int likes, int shares, String DateTime, User user);

    public void deletePost(Post post);
    
    public List<Post> getPostsByUser(User user);
    
    public List<Post> getAllPosts();
    
    public Post getPostByID(int postId);
    
    public List<Post> getTopNPostsByLikes(int n, User user);
    
    public List<Post> getTopNPostsByShares(int n, User user);
    
    public File handleExportPost();
    
    public File handleImportPost();
    
    public boolean savePostToFile(Post post, File file);
    
    public List<Post> previewImportedPosts(File file, User user) throws CsvLoadingException;
    
    public boolean addBulkPosts(List<Post> posts);

    // User-related methods
    public User loginUser(String username, String password);
    
    public boolean signupUser(String username, String password, String firstName, String lastName);
    
    public boolean degradeToStandard(User user);
    
    public boolean upgradeToVIP(User user);
    
    public boolean checkUsernameExist(String username);
    
    public boolean isPasswordSameAsOld(int userId, String password, String salt);
    
    public boolean updateUserPassword(User user, String password);
    
    public boolean updateUserProfile(User user);
}
