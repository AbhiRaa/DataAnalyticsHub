package views;

import controllers.PostController;
import controllers.UserController;
import database.DBManager;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import models.User;

public class LoginView {

    private Stage stage;
    private TextField usernameField;
    private PasswordField passwordField;
    private Button loginButton, signupButton;
    //private PostController postController;
    private UserController userController;

    public LoginView(Stage stage, UserController userController) {
        this.userController = userController;
//        this.postController = postController;
        this.stage = stage;
        initializeComponents();
    }

    private void initializeComponents() {
        // Initialize GUI components
    	// Logo or image at the top
        ImageView logoView = new ImageView(new Image("/image/lock.png"));
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
        
        loginButton = new Button("Login");
        loginButton.setOnAction(e -> handleLogin());
        
        signupButton = new Button("Signup");
        signupButton.setOnAction(e -> handleSignup());

        GridPane gridPane = new GridPane();
        gridPane.setVgap(10);
        gridPane.setHgap(10);
        gridPane.setPadding(new Insets(20, 20, 20, 20));
        gridPane.getColumnConstraints().addAll(col1, col2);

        gridPane.add(logoView, 1, 0, 2, 1);
        gridPane.add(new Label("Username:"), 0, 1);
        gridPane.add(usernameField, 1, 1);
        gridPane.add(new Label("Password:"), 0, 2);
        gridPane.add(passwordField, 1, 2);
        gridPane.add(loginButton, 0, 3);
        gridPane.add(signupButton, 1, 3);

        stage.setScene(new Scene(gridPane, 350, 250));
        stage.setTitle("Login");
        stage.show();
    }

    private void handleLogin() {
        // Get username and password from fields
        String username = usernameField.getText();
        String password = passwordField.getText();

        User user = userController.loginUser(username, password);
        if (user != null) {
            // Successful login, switch to DashboardView
            new DashboardView(user, new PostController(new DBManager()), userController);
            stage.close();
        } else {
            // Show error message
            showError("Invalid username or password");
        }
    }

    private void handleSignup() {
        // Switch to SignupView
        new SignupView(userController);
        stage.close();
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

