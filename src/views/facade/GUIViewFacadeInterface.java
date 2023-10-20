package views.facade;

import java.io.File;
import java.util.List;

import exceptions.CsvLoadingException;
import exceptions.PostException;
import exceptions.UserException;
import javafx.scene.control.Alert.AlertType;
import models.Post;
import models.User;

/**
 * The GUIViewFacadeInterface serves as an abstraction layer for the view (GUI) components of the application.
 * By exposing a set of methods through this interface, you're ensuring that the GUI components can interact with
 * the underlying application logic in a decoupled and maintainable manner.
 */
public interface GUIViewFacadeInterface {
	
	// === Navigation Methods ===
	
    public void navigateToLogin();

    public void navigateToSignup();
    
    /**
     * Navigates to the user's dashboard.
     * @param user The logged-in user.
     */
    public void navigateToDashboard(User user);
    
    public void navigateToAddPost(User user);
    
    public void navigateToMyPosts(User user);
    
    public void navigateToEditUserProfile(User user);
    
    public void navigateToUpgradeToVIP(User user);
    
    public void navigateToVisualization(User user);
    
    public void navigateToBulkImports(User user);
 
    public void navigateToEditPost(User user, Post post);

    // === Alerts & Messages ===
    /**
     * Displays an alert to the user.
     * @param alertType The type of alert to show (e.g., INFO, ERROR).
     * @param title The title of the alert.
     * @param message The message content of the alert.
     */
    public void showAlert(AlertType alertType, String title, String message);

    // === Post-related methods ===
    
    public void addPost(String content, String author, int likes, int shares, String DateTime, User user) throws PostException;

    public void editPost(int postId, String content, String author, int likes, int shares, String DateTime, User user) throws PostException;

    public void deletePost(Post post) throws PostException;
    
    public List<Post> getPostsByUser(User user) throws PostException;
    
    public List<Post> getAllPosts() throws PostException;
    
    public Post getPostByID(int postId) throws PostException;
    
    public List<Post> getTopNPostsByLikes(int n, User user) throws PostException;
    
    public List<Post> getTopNPostsByShares(int n, User user) throws PostException;
    
    public File handleExportPost();
    
    public File handleImportPost();
    
    public boolean savePostToFile(Post post, File file);
    
    public List<Post> previewImportedPosts(File file, User user) throws CsvLoadingException;
    
    public boolean addBulkPosts(List<Post> posts) throws PostException;

    // === User-related methods ===
    
    public User loginUser(String username, String password) throws UserException;
    
    public boolean signupUser(String username, String password, String firstName, String lastName) throws UserException;
    
    public boolean degradeToStandard(User user) throws UserException;
    
    public boolean upgradeToVIP(User user) throws UserException;
    
    public boolean checkUsernameExist(String username) throws UserException;
    
    public boolean isPasswordSameAsOld(int userId, String password, String salt) throws UserException;
    
    public boolean updateUserPassword(User user, String password) throws UserException;
    
    public boolean updateUserProfile(User user) throws UserException;
}
