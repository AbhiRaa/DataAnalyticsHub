package views;

import controllers.PostController;
import controllers.UserController;
import exceptions.UserException;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import models.User;
import views.facade.GUIViewFacade;
import views.facade.GUIViewFacadeInterface;
import views.interfaces.LoginViewInterface;

/**
 * The LoginView class provides a graphical interface for the login screen.
 * Users can enter their username and password to log into the application.
 * The view also provides a toggle feature to view/hide the password.
 * Additionally, it provides a link for new users to navigate to the signup screen.
 */
public class LoginView extends BaseView implements LoginViewInterface {

    private Stage stage;
    private TextField usernameField;
    private PasswordField passwordField;
    private Button loginButton, exit;
    private Hyperlink signupHyperlink;
    private TextField visiblePasswordField;
    private ImageView togglePasswordVisibility;
    private GridPane gridPane;
    
    private GUIViewFacadeInterface viewFacade;
    
    /**
     * Constructs the LoginView.
     * 
     * @param stage The primary stage for this view.
     * @param userController The controller for user-related operations.
     * @param postController The controller for post-related operations.
     */
    public LoginView(Stage stage, UserController userController, PostController postController) {
        this.stage = stage;
        this.viewFacade = new GUIViewFacade(stage, userController, postController);
        
        initializeComponents();
        show();
        System.out.println("LoginView initialized.");
    }
    
    @Override
    protected void initializeComponents() {
        // Initialize GUI components
    	// Login logo at the top
        ImageView logoView = new ImageView(new Image("/image/key.png"));
        logoView.setFitWidth(100);
        logoView.setPreserveRatio(true);
        
    	// Create two columns with specific percentages of the width
        ColumnConstraints col1 = new ColumnConstraints();
        col1.setPercentWidth(40);  // this column takes up 40% of the grid width
        ColumnConstraints col2 = new ColumnConstraints();
        col2.setPercentWidth(60);  // this column takes up 60% of the grid width
        

        // Initialize GUI components
        usernameField = new TextField();
        usernameField.setPromptText("Username");
        
        passwordField = new PasswordField();
        passwordField.setPromptText("Password");
        
        // Create the visible password field (which will start as hidden)
        visiblePasswordField = new TextField();
        visiblePasswordField.setPromptText("Password");
        visiblePasswordField.setVisible(false);  // start as hidden

        // Create the eye toggle
        Image eyeImage = new Image("/image/eye.png"); // replace with your path to the eye image
        togglePasswordVisibility = new ImageView(eyeImage);
        togglePasswordVisibility.setFitWidth(12);
        togglePasswordVisibility.setPreserveRatio(true);
        togglePasswordVisibility.setOnMouseClicked(e -> handleTogglePasswordVisibility());

        // Place the eye toggle at the end of the password fields
        GridPane.setMargin(togglePasswordVisibility, new Insets(0, 10, 0, -25));  // adjust as needed

        loginButton = new Button("Login");
        loginButton.setOnAction(e -> {
			try {
				handleLogin();
			} catch (UserException e1) {
				e1.printStackTrace();
			}
		});
        
        signupHyperlink = new Hyperlink("New User? Signup here.");
        signupHyperlink.setOnAction(e -> handleSignup());
        
        exit = new Button("Exit Application");
        exit.setOnAction(e -> handleExit());
        
        Image exitImage = new Image("/image/exit.png");
        ImageView exitImageView = new ImageView(exitImage);
        exitImageView.setFitHeight(20);
        exitImageView.setFitWidth(30);
        exit.setGraphic(exitImageView);
        exit.setText("");  // Remove text

        gridPane = new GridPane();
        gridPane.setVgap(10);
        gridPane.setHgap(10);
        gridPane.setPadding(new Insets(20, 20, 20, 20));
        gridPane.getColumnConstraints().addAll(col1, col2);

        gridPane.add(logoView, 1, 0, 2, 1);
        gridPane.add(new Label("Username"), 0, 1);
        gridPane.add(usernameField, 1, 1);
        gridPane.add(new Label("Password"), 0, 2);
        gridPane.add(passwordField, 1, 2);
        gridPane.add(loginButton, 1, 3, 2, 1); // Spanning 2 columns
        
        // Add these components to the gridPane
        gridPane.add(visiblePasswordField, 1, 2);  // occupy the same grid cell
        gridPane.add(togglePasswordVisibility, 2, 2);
        
        // Create layout for bottom of the screen
        HBox bottomLayout = new HBox();
        bottomLayout.setAlignment(Pos.BOTTOM_CENTER);
        bottomLayout.setSpacing(20);
        bottomLayout.setPadding(new Insets(0, 20, 20, 20)); // padding for top, right, bottom, left
        bottomLayout.getChildren().addAll(exit, signupHyperlink);

        gridPane.add(bottomLayout, 0, 5, 2, 1); // Spanning over two columns
        System.out.println("LoginView components initialized.");
    }
    
    @Override
    protected void show() { 
    	stage.setScene(new Scene(gridPane, 400, 300));
        stage.setTitle("Login");
        stage.show();
    }
    
    @Override
    public void handleExit() {
		viewFacade.showAlert(AlertType.INFORMATION, "Message", "See you soon!");
		stage.close();
        System.out.println("User chose to exit the application.");
	}
    
    @Override
	public void handleLogin() throws UserException {
    	try {
    		// Get username and password from fields
            String username = usernameField.getText();
            String password = passwordField.getText();

            User user = viewFacade.loginUser(username, password);
            if (user != null) {
                // Successful login, switch to DashboardView
                viewFacade.navigateToDashboard(user);
            } else {
                // Show error message
                viewFacade.showAlert(AlertType.ERROR, "Error", "Invalid username or password");
            }
            System.out.println("Attempt to log in with username: " + username);
        } catch (Exception e) {
            System.err.println("Error during login: " + e.getMessage());
            viewFacade.showAlert(AlertType.ERROR, "Error", "An unexpected error occurred during login: " + e.getMessage());
        }
    }
    
    @Override
    public void handleSignup() {
        // Switch to SignupView
        viewFacade.navigateToSignup();
        System.out.println("Navigating to Signup view.");
    }
    
    @Override
    // Method to handle the visibility toggle
    public void handleTogglePasswordVisibility() {
        if (passwordField.isVisible()) {
            // copy the password from the password field to the plain text field
            visiblePasswordField.setText(passwordField.getText());
            passwordField.setVisible(false);
            visiblePasswordField.setVisible(true);
            System.out.println("Password visibility toggled: Hidden");

        } else {
            // copy the password from the plain text field to the password field
            passwordField.setText(visiblePasswordField.getText());
            passwordField.setVisible(true);
            visiblePasswordField.setVisible(false);
            System.out.println("Password visibility toggled: Visible");
        }
    }
}

