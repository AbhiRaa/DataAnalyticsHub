package views;

import java.io.File;
import java.util.List;

import controllers.PostController;
import controllers.UserController;
import exceptions.CsvLoadingException;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import models.Post;
import models.User;
import utils.CSVUtils;

public class BulkImportView {

    private Stage stage;
    private User user;
    private PostController postController;
    private UserController userController;
    private FileChooser fileChooser;
    private Button importButton, backButton, saveButton;
    private ListView<String> postListView;
    private List<Post> validPosts;

    public BulkImportView(Stage stage, User user, PostController postController, UserController userController) {
        this.user = user;
    	this.postController = postController;
    	this.userController = userController;
        this.stage = stage;
        initializeComponents();
    }

    private void initializeComponents() {
        fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("CSV Files", "*.csv"));
        
        postListView = new ListView<>();
        
        importButton = new Button("Select CSV");
        importButton.setOnAction(e -> {
            File selectedFile = fileChooser.showOpenDialog(stage);
            if (selectedFile != null) {
            	previewImportedPosts(selectedFile);
            }
        });
        
        saveButton = new Button("Save Posts");
        saveButton.setOnAction(e -> handleSave());
        saveButton.setDisable(true);  // Initially disabled until posts are previewed
        
        backButton = new Button("Back");
        backButton.setOnAction(e -> {
        	new DashboardView(stage, user, postController, userController);
        });

        VBox layout = new VBox(10);
        layout.getChildren().addAll(importButton, postListView, saveButton, backButton);

        stage.setScene(new Scene(layout, 500, 400));
        stage.setTitle("Bulk Import");
        stage.show();
    }
    
    private void previewImportedPosts(File selectedFile) {
        try {
            validPosts = CSVUtils.readPosts(selectedFile.getAbsolutePath(), user);
            postListView.getItems().clear();
            for (Post post : validPosts) {
                postListView.getItems().add(formatPostForDisplay(post));
            }
            saveButton.setDisable(false);
        } catch (CsvLoadingException e) {
            showAlert(AlertType.ERROR, "Error", "Error reading CSV file: " + e.getMessage());
        }
    }
    
    private String formatPostForDisplay(Post post) {
        return String.format("Content: %s | Likes: %d | Shares: %d", post.getContent(), post.getLikes(), post.getShares());
    }

    private void handleSave() {
        boolean success = postController.addBulkPosts(validPosts);
        if (success) {
            showAlert(AlertType.INFORMATION, "Success", "Posts imported successfully!");
            new DashboardView(stage, user, postController, userController);
        } else {
            showAlert(AlertType.ERROR, "Error", "There was an error importing posts.");
        }
    }
    
    private void showAlert(AlertType alertType, String title, String message) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}

