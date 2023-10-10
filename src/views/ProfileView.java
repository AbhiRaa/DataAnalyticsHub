package views;

import controllers.PostController;
import controllers.UserController;
import database.DBManager;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import models.User;
import utils.PasswordUtils;

public class ProfileView {

    private Stage stage;
    private UserController userController;
    private User user;
    private TextField usernameField, firstNameField, lastNameField;
    private PasswordField passwordField, confirmPasswordField;
    private Button saveButton, backButton;

    public ProfileView(UserController userController, User user) {
        this.userController = userController;
        this.user = user;
        this.stage = new Stage();
        initializeComponents();
    }

    private void initializeComponents() {
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

        VBox layout = new VBox(10);
        layout.getChildren().addAll(new Label("Username:"), usernameField, 
                new Label("First Name:"), firstNameField,
                new Label("Last Name:"), lastNameField,
                new Label("New Password:"), passwordField,
                new Label("Confirm Password:"), confirmPasswordField,
                saveButton, backButton);

        stage.setScene(new Scene(layout, 350, 400));
        stage.setTitle("Edit Profile");
        stage.show();
    }

    private void handleSave() {
        String username = usernameField.getText();
        String firstName = firstNameField.getText();
        String lastName = lastNameField.getText();
        String password = passwordField.getText();
        String confirmPassword = confirmPasswordField.getText();

        // Check if username has changed and if the new one is unique
        if (!user.getUsername().equals(username) && userController.usernameExists(username)) {
            showError("The username is already taken.");
            return;
        }

        if (!password.isEmpty()) {
            if (!password.equals(confirmPassword)) {
                showError("Passwords do not match!");
                return;
            }

            String hashedOldPassword = userController.getHashedPassword(username);
            if (hashedOldPassword.equals(PasswordUtils.hashPassword(password, user.getSalt()))) {
                showError("New password should be different from the previous one.");
                return;
            }
        }

        user.setUsername(username);
        user.setFirstName(firstName);
        user.setLastName(lastName);

        if (!password.isEmpty()) {
            userController.updateUserPassword(user, password);
        }

        boolean updated = userController.updateUserProfile(user);
        if (updated) {
            showInfo("Profile updated successfully.");
            new DashboardView(user, new PostController(new DBManager()), userController);
            stage.close();
        } else {
            showError("Error updating profile. Please try again.");
        }
    }


    private void handleBack() {
        new DashboardView(user, new PostController(new DBManager()), userController);
        stage.close();
    }

    private void showError(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
    
    private void showInfo(String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Info");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}

