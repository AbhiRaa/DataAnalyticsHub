package views;

import controllers.PostController;
import controllers.UserController;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import models.User;

public class DashboardView {

    private Stage stage;
    private User user;
    private PostController postController;
    private UserController userController;
    private Label welcomeMessage;
    private Button addPostButton, viewPostsButton, editProfileButton, logoutButton, vipUpgradeButton, dataVisualization, importPosts;

    public DashboardView(Stage stage, User user, PostController postController, UserController userController) {
        this.user = user;
        this.postController = postController;
        this.userController = userController;
        this.stage = stage;
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
            vipUpgradeButton = new Button("VIP Features");
            vipUpgradeButton.setOnAction(e -> handleUpgradeToVIP());
        } else {
        	dataVisualization = new Button("Data Visualization");
        	dataVisualization.setOnAction(e -> handleVisualization());
        	
        	importPosts = new Button("Import Posts");
        	importPosts.setOnAction(e -> handleImports());
        }

        VBox layout = new VBox(10);
        layout.getChildren().addAll(welcomeMessage, addPostButton, viewPostsButton, editProfileButton, logoutButton);
        if (!user.isVIP()) {
            layout.getChildren().add(vipUpgradeButton);
        } else {
        	layout.getChildren().add(dataVisualization);
        	layout.getChildren().add(importPosts);
        }

        stage.setScene(new Scene(layout, 300, 250));
        stage.setTitle("Dashboard");
        stage.show();
    }

	private void handleAddPost() {
        // Switch to the PostFormView to allow the user to add a new post
    	System.out.println("DashboardView - User's ID: " + user.getUserId());

        new PostFormView(stage, postController, user);
        //stage.close();
    }

    private void handleViewPosts() {
        // Display a list of the user's posts, with options to view, edit, or delete each post
        new PostListView(stage, postController, user);
        //stage.close();
    }

    private void handleEditProfile() {
        // Switch to ProfileView to allow the user to edit their profile details
        new ProfileView(stage, userController, user);
        //stage.close();
    }

    private void handleLogout() {
        // Logout the user and return to the LoginView
        new LoginView(stage, userController, postController);
        //stage.close();
    }

    private void handleUpgradeToVIP() {
    	// Offer the user the option to upgrade to VIP status            
        new UpgradeToVIPView(stage, user, userController, postController);
           
    }
    
    private void handleVisualization() {
		new PieChartView(stage, user, userController, postController);
	}
    
    private void handleImports() {
		new BulkImportView(stage, user, postController, userController);
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

