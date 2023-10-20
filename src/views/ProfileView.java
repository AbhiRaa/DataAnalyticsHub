package views;

import controllers.PostController;
import controllers.UserController;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import models.User;
import views.facade.GUIViewFacade;
import views.facade.GUIViewFacadeInterface;
import views.interfaces.ProfileViewInterface;

/**
 * The ProfileView class provides a graphical interface for users to view and edit
 * their profiles. It allows for changing the username, first name, last name, and password.
 */
public class ProfileView extends BaseView implements ProfileViewInterface {

    private Stage stage;
    private User user;
    private TextField usernameField, firstNameField, lastNameField;
    private PasswordField passwordField, confirmPasswordField;
    private Button saveButton, backButton;
    private VBox mainLayout;
    
    private GUIViewFacadeInterface viewFacade;
    
    /**
     * Constructs the ProfileView.
     * 
     * @param stage The primary stage for this view.
     * @param user The current logged-in user.
     * @param userController The controller for user-related operations.
     * @param postController The controller for post-related operations.
     */
    public ProfileView(Stage stage, User user, UserController userController, PostController postController) {
        this.user = user;
        this.stage = stage;
        this.viewFacade = new GUIViewFacade(stage, userController, postController);
        
        initializeComponents();
        show();
        System.out.println("ProfileView initialized.");
    }
    
    @Override
    protected void initializeComponents() {
    	
    	ImageView logoView = new ImageView(new Image("/image/resume.png"));
        logoView.setFitWidth(100);
        logoView.setPreserveRatio(true);
        
        usernameField = new TextField();
        usernameField.setText(user.getUsername());

        firstNameField = new TextField();
        firstNameField.setText(user.getFirstName());

        lastNameField = new TextField();
        lastNameField.setText(user.getLastName());

        passwordField = new PasswordField();
        passwordField.setPromptText("New Password");

        confirmPasswordField = new PasswordField();
        confirmPasswordField.setPromptText("Confirm New Password");

        saveButton = new Button("Save");
        saveButton.setOnAction(e -> handleSave());

        backButton = new Button("Back");
        backButton.setOnAction(e -> handleBack());

        VBox userlayout = new VBox(10);
        userlayout.getChildren().addAll(new Label("Username"), usernameField, 
                new Label("First Name"), firstNameField,
                new Label("Last Name"), lastNameField,
                new Label("New Password"), passwordField,
                new Label("Confirm Password"), confirmPasswordField);
        userlayout.setAlignment(Pos.CENTER_LEFT);
        userlayout.setPadding(new Insets(20, 20, 30, 20));

        mainLayout = new VBox(10);
        mainLayout.getChildren().addAll(logoView, userlayout, saveButton, backButton);
        mainLayout.setAlignment(Pos.CENTER);
        mainLayout.setPadding(new Insets(20, 20, 30, 20));

    }
    
    @Override
    protected void show() {
    	stage.setScene(new Scene(mainLayout, 400, 600));
        stage.setTitle("Edit Profile");
        stage.show();
        System.out.println("ProfileView displayed.");
    }
    
    @Override
    public void handleSave() {
    	try {
    		String username = usernameField.getText();
            String firstName = firstNameField.getText();
            String lastName = lastNameField.getText();
            String password = passwordField.getText();
            String confirmPassword = confirmPasswordField.getText();

            // Check if username has changed and if the new one is unique
            if (!user.getUsername().equals(username) && viewFacade.checkUsernameExist(username)) {
            	viewFacade.showAlert(AlertType.ERROR, "Error", "The username is already taken.");
                return;
            }

            if (!password.isEmpty()) {
            	if (confirmPassword.isEmpty()) {
            		viewFacade.showAlert(AlertType.ERROR, "Error", "Confirm password is empty.");
                    return;
            	}
            	if (password.length() < 6) {
                    viewFacade.showAlert(AlertType.ERROR, "Error", "Password should be at least 6 characters long!");
                    return;
                }
                if (!password.equals(confirmPassword)) {
                    viewFacade.showAlert(AlertType.ERROR, "Error", "Passwords do not match.");
                    return;
                }
                if (viewFacade.isPasswordSameAsOld(user.getUserId(), password, user.getSalt())) {
                    viewFacade.showAlert(AlertType.ERROR, "Error", "New password should be different from the previous one.");
                    return;
                }
            }

            user.setUsername(username);
            user.setFirstName(firstName);
            user.setLastName(lastName);
            
            boolean isUpdated = true;
            if (!password.isEmpty() && !confirmPassword.isEmpty()) {
                isUpdated = viewFacade.updateUserPassword(user, password);
            }

            if (isUpdated) {
                isUpdated = viewFacade.updateUserProfile(user);
            }

            if (isUpdated) {
                viewFacade.showAlert(AlertType.INFORMATION, "Success", "Profile updated successfully.");
                viewFacade.navigateToDashboard(user);
            } else {
                viewFacade.showAlert(AlertType.ERROR, "Error", "Error updating profile. Please try again.");
            }
    		System.out.println("Profile saved.");

    	} catch (Exception e) {
    		System.err.println("Error saving profile: " + e.getMessage());
            viewFacade.showAlert(AlertType.ERROR, "Error", "An unexpected error occurred. Please try again.");
    	}
    }
    
    @Override
    public void handleBack() {
    	viewFacade.navigateToDashboard(user);
    	System.out.println("Navigating back to dashboard.");
    }
}