package views;

import controllers.PostController;
import controllers.UserController;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import models.User;

public class DashboardView {

    private Stage stage;
    private User user;
    private PostController postController;
    private UserController userController;
    private Label welcomeMessage;
    private Button addPostButton, viewPostsButton, editProfileButton, logoutButton, vipUpgradeButton;

    public DashboardView(User user, PostController postController, UserController userController) {
        this.user = user;
        this.postController = postController;
        this.userController = userController;
        this.stage = new Stage();
        initializeComponents();
    }

    private void initializeComponents() {
        welcomeMessage = new Label("Welcome, " + user.getFirstName() + " " + user.getLastName() + "!");

        addPostButton = new Button("Add Post");
        addPostButton.setOnAction(e -> handleAddPost());

        viewPostsButton = new Button("View My Posts");
        viewPostsButton.setOnAction(e -> handleViewPosts());

        editProfileButton = new Button("Edit Profile");
        editProfileButton.setOnAction(e -> handleEditProfile());

        logoutButton = new Button("Logout");
        logoutButton.setOnAction(e -> handleLogout());

        if (!user.isVIP()) {
            vipUpgradeButton = new Button("Upgrade to VIP");
            vipUpgradeButton.setOnAction(e -> handleUpgradeToVIP());
        }

        VBox layout = new VBox(10);
        layout.getChildren().addAll(welcomeMessage, addPostButton, viewPostsButton, editProfileButton, logoutButton);
        if (!user.isVIP()) {
            layout.getChildren().add(vipUpgradeButton);
        }

        stage.setScene(new Scene(layout, 300, 250));
        stage.setTitle("Dashboard");
        stage.show();
    }

    private void handleAddPost() {
        // Switch to the PostFormView to allow the user to add a new post
    	System.out.println("DashboardView - User's ID: " + user.getUserId());

        new PostFormView(postController, user);
        stage.close();
    }

    private void handleViewPosts() {
        // Display a list of the user's posts, with options to view, edit, or delete each post
        new PostListView(postController, user);
        stage.close();
    }

    private void handleEditProfile() {
        // Switch to ProfileView to allow the user to edit their profile details
        new ProfileView(userController, user);
        stage.close();
    }

    private void handleLogout() {
        // Logout the user and return to the LoginView
        new LoginView(stage, userController);
        //stage.close();
    }

    private void handleUpgradeToVIP() {
        // Offer the user the option to upgrade to VIP status
        if (userController.upgradeToVIP(user)) {
            user.setVIP(true);
            new DashboardView(user, postController, userController); // Refresh the dashboard
            stage.close();
        } else {
            showError("Error upgrading to VIP. Please try again.");
        }
    }

    private void showError(String message) {
        // Display error message to user using a JavaFX Alert or similar
    }
}

