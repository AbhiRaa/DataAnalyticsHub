package views;

import controllers.UserController;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class SignupView {

    private Stage stage;
    private TextField usernameField, firstNameField, lastNameField;
    private PasswordField passwordField, confirmPasswordField;
    private Button signupButton, backButton;
    private UserController userController;

    public SignupView(UserController userController) {
        this.userController = userController;
        this.stage = new Stage();
        initializeComponents();
    }

    private void initializeComponents() {
    	// Initialize GUI components with color styling
        usernameField = createStyledTextField("Username");
        
        firstNameField = createStyledTextField("First Name");
        lastNameField = createStyledTextField("Last Name");

        HBox nameBox = new HBox(10, firstNameField, lastNameField); // Put first and last name side by side

        passwordField = (PasswordField) createStyledTextField("Password", new PasswordField());
        confirmPasswordField = (PasswordField) createStyledTextField("Confirm Password", new PasswordField());
        
        signupButton = new Button("Signup");
        signupButton.setOnAction(e -> handleSignup());
        
        backButton = new Button("Back");
        backButton.setOnAction(e -> handleBack());

        VBox layout = new VBox(10, usernameField, nameBox, passwordField, confirmPasswordField, signupButton, backButton);
        layout.setPadding(new Insets(20));

        stage.setScene(new Scene(layout, 400, 300));
        stage.setTitle("Signup");
        stage.show();
    }
    
    private TextField createStyledTextField(String promptText) {
        TextField textField = new TextField();
        textField.setPromptText(promptText);
        textField.setStyle("-fx-background-color: #ECECEC;"); // light gray background
        return textField;
    }

    private TextField createStyledTextField(String promptText, PasswordField passwordField) {
        passwordField.setPromptText(promptText);
        passwordField.setStyle("-fx-background-color: #ECECEC;");
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
                showError("All fields are required!");
                return;
            }

            if (password.length() < 6) {
                showError("Password should be at least 6 characters long!");
                return;
            }

            if (!password.equals(confirmPassword)) {
                showError("Passwords do not match!");
                return;
            }

            boolean registered = userController.registerUser(username, password, firstName, lastName);
            if (registered) {
                new LoginView(stage, userController);
                //stage.close();
            } else {
                showError("Username already exists!");
            }
        } catch (Exception e) {
            showError("An error occurred: " + e.getMessage());
        }
    }

    private void handleBack() {
        // Switch back to LoginView
        new LoginView(stage, userController);
       // stage.close();
    }

    private void showError(String message) {
        // Display error message to user using a JavaFX Alert or similar
    	 Alert alert = new Alert(AlertType.ERROR);
         alert.setTitle("Error");
         alert.setHeaderText(null);
         alert.setContentText(message);
         alert.showAndWait();
    }
}

