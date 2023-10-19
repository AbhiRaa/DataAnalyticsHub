package views.facade;

import java.io.File;
import java.util.List;

import controllers.PostController;
import controllers.UserController;
import exceptions.CsvLoadingException;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;
import models.Post;
import models.User;
import utils.CSVUtils;
import utils.FileChooserUtils;
import utils.PasswordUtils;
import views.BulkImportView;
import views.DashboardView;
import views.LoginView;
import views.PieChartView;
import views.PostFormView;
import views.PostListView;
import views.ProfileView;
import views.SignupView;
import views.UpgradeToVIPView;

public class GUIViewFacade {

	private Stage currentStage;
    private UserController userController;
    private PostController postController;

    public GUIViewFacade(Stage stage, UserController userController, PostController postController) {
    	this.currentStage = stage;
        this.userController = userController;
        this.postController = postController;
    }
    
    // Navigation Methods
    public void navigateToLogin() {
    	new LoginView(currentStage, userController, postController);
    }

    public void navigateToSignup() {
        new SignupView(currentStage, userController, postController);
    }

    public void navigateToDashboard(User user) {
        new DashboardView(currentStage, user, postController, userController);
    }
    
    public void navigateToAddPost(User user) {
    	new PostFormView(currentStage, user, postController, userController);
    }
    
    public void navigateToMyPosts(User user) {
        new PostListView(currentStage, user, postController, userController);
    }
    
    public void navigateToEditUserProfile(User user) { 
    	new ProfileView(currentStage, user, userController, postController);
    }
    
    public void navigateToUpgradeToVIP(User user) { 
    	new UpgradeToVIPView(currentStage, user, userController, postController);
    }
    
    public void navigateToVisualization(User user) { 
    	new PieChartView(currentStage, user, userController, postController);
    }
    
    public void navigateToBulkImports(User user) { 
    	new BulkImportView(currentStage, user, postController, userController);
    }
 
    public void navigateToEditPost(User user, Post post) {
        new PostFormView(currentStage, user, postController, userController, post);
    }

    

    // Alerts & Messages
    public void showAlert(AlertType alertType, String title, String message) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    // Post-related methods
    public void addPost(String content, String author, int likes, int shares, String DateTime, User user) {
    	Post post = new Post(content, author, likes, shares, DateTime, user.getUserId());
        postController.addPost(post);
    }

    public void editPost(int postId, String content, String author, int likes, int shares, String DateTime, User user) {
        Post post = new Post(postId, content, author, likes, shares, DateTime, user.getUserId());
        postController.updatePost(post);
    }

    public void deletePost(Post post) {
        postController.deletePost(post);
    }
    
    public List<Post> getPostsByUser(User user){
    	return postController.getPostsByUser(user);
    }
    
    public List<Post> getAllPosts(){
    	return postController.getAllPosts();
    }
    
    public Post getPostByID(int postId) { 
    	return postController.getPostByID(postId);
    }
    
    public List<Post> getTopNPostsByLikes(int n, User user) {
    	return postController.getTopNPostsByLikes(n, user);
    }
    
    public List<Post> getTopNPostsByShares(int n, User user) {
    	return postController.getTopNPostsByShares(n, user);
    }
    
    public File handleExportPost() {
    	return FileChooserUtils.showSaveCSVFileDialog(currentStage);
    }
    
    public File handleImportPost() {
    	return FileChooserUtils.showOpenCSVFileDialog(currentStage);
    }
    
    public boolean savePostToFile(Post post, File file) {
    	return CSVUtils.savePostToFile(post, file);
    }
    
    public List<Post> previewImportedPosts(File file, User user) throws CsvLoadingException{ 
    	return CSVUtils.readPosts(file.getAbsolutePath(), user);
    }
    
    public boolean addBulkPosts(List<Post> posts) {
    	return postController.addBulkPosts(posts);
    }

    // User-related methods
    public User loginUser(String username, String password) {
        return userController.loginUser(username, password);
    }

    public boolean signupUser(String username, String password, String firstName, String lastName) {
        return userController.registerUser(username, password, firstName, lastName);
    }
    
    public boolean degradeToStandard(User user) {
    	return userController.degradeToStandard(user);
    }
    
    public boolean upgradeToVIP(User user) {
    	return userController.upgradeToVIP(user);
    }
    
    public boolean checkUsernameExist(String username) {
    	return userController.usernameExists(username);
    }
    
    public boolean isPasswordSameAsOld(int userId, String password, String salt) {
        String hashedOldPassword = userController.getHashedPassword(userId);
        return hashedOldPassword.equals(PasswordUtils.hashPassword(password, salt));
    }
    
    public boolean updateUserPassword(User user, String password) {
        return userController.updateUserPassword(user, password);
    }
    
    public boolean updateUserProfile(User user) { 
    	return userController.updateUserProfile(user);
    }

}

