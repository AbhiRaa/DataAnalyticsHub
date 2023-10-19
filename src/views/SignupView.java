package views;

import controllers.PostController;
import controllers.UserController;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import views.facade.GUIViewFacade;

public class SignupView {

    private Stage stage;
    private TextField usernameField, firstNameField, lastNameField;
    private PasswordField passwordField, confirmPasswordField;
    private Button signupButton;
    private Hyperlink backButton;
    
    private GUIViewFacade viewFacade;

    public SignupView(Stage stage, UserController userController, PostController postController) {
        this.stage = stage;
        this.viewFacade = new GUIViewFacade(stage, userController, postController);

        initializeComponents();
    }

    private void initializeComponents() {
    	// Initialize GUI components with color styling
    	// Signup logo at the top
        ImageView logoView = new ImageView(new Image("/image/user.png"));
        logoView.setFitWidth(100);
        logoView.setPreserveRatio(true);
        
        usernameField = createStyledTextField("Username");
        
        firstNameField = createStyledTextField("First Name");
        lastNameField = createStyledTextField("Last Name");

        HBox nameBox = new HBox(10, firstNameField, lastNameField); // Put first and last name side by side

        passwordField = (PasswordField) createStyledTextField("Password", new PasswordField());
        confirmPasswordField = (PasswordField) createStyledTextField("Confirm Password", new PasswordField());
        
        signupButton = new Button("Signup");
        signupButton.setOnAction(e -> handleSignup());
        
        backButton = new Hyperlink("Already a user? Login here.");
        backButton.setOnAction(e -> handleBack());

        VBox layout = new VBox(10, logoView, usernameField, nameBox, passwordField, confirmPasswordField, signupButton, backButton);
        layout.setAlignment(Pos.CENTER);
        layout.setPadding(new Insets(20, 20, 30, 20));

        stage.setScene(new Scene(layout, 350, 400));
        stage.setTitle("Signup");
        stage.show();
    }
    
    private TextField createStyledTextField(String promptText) {
        TextField textField = new TextField();
        textField.setPromptText(promptText);
        return textField;
    }

    private TextField createStyledTextField(String promptText, PasswordField passwordField) {
        passwordField.setPromptText(promptText);
        return passwordField;
    }
    
    private void handleSignup() {
        // Get details from fields
    	try {
            String username = usernameField.getText();
            String firstName = firstNameField.getText();
            String lastName = lastNameField.getText();
            String password = passwordField.getText();
            String confirmPassword = confirmPasswordField.getText();

            if (username.isEmpty() || firstName.isEmpty() || lastName.isEmpty() || password.isEmpty()) {
                viewFacade.showAlert(AlertType.ERROR, "Error", "All fields are required!");
                return;
            }

            if (password.length() < 6) {
                viewFacade.showAlert(AlertType.ERROR, "Error", "Password should be at least 6 characters long!");
                return;
            }

            if (!password.equals(confirmPassword)) {
                viewFacade.showAlert(AlertType.ERROR, "Error", "Passwords do not match!");
                return;
            }

            boolean registered = viewFacade.signupUser(username, password, firstName, lastName);
            if (registered) {
                viewFacade.showAlert(AlertType.INFORMATION, "Success", "User successfully registered with '" + username + "' as username.");
                viewFacade.navigateToLogin();
                
            } else {
                viewFacade.showAlert(AlertType.ERROR, "Error", "Username already exists!");
            }
        } catch (Exception e) {
            viewFacade.showAlert(AlertType.ERROR, "Error", "All fields are required!");
        }
    }

    private void handleBack() {
        // Switch back to LoginView
        viewFacade.navigateToLogin();
    }
}

