package views;

import java.io.File;
import java.util.List;

import controllers.PostController;
import controllers.UserController;
import exceptions.CsvLoadingException;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import models.Post;
import models.User;
import views.facade.GUIViewFacade;
import views.interfaces.BulkImportViewInterface;

public class BulkImportView extends BaseView implements BulkImportViewInterface {

    private Stage stage;
    private User user;
    private Button importButton, backButton, saveButton;
    private ListView<String> postListView;
    private List<Post> validPosts;
    private VBox mainLayout;
    
    private GUIViewFacade viewFacade;

    public BulkImportView(Stage stage, User user, PostController postController, UserController userController) {
        this.user = user;
        this.stage = stage;
        this.viewFacade = new GUIViewFacade(stage, userController, postController);
        initializeComponents();
        show();
    }
    
    @Override
    protected void initializeComponents() {
        
        postListView = new ListView<>();
        
        importButton = new Button("Select");
        importButton.setOnAction(e -> {
        	
            File selectedFile = viewFacade.handleImportPost();
            if (selectedFile != null) {
            	previewImportedPosts(selectedFile);
            } else {
            	viewFacade.showAlert(AlertType.ERROR, "Error", "No file selected!");
            }
        });
        
        saveButton = new Button("Save Posts");
        saveButton.setOnAction(e -> handleSave());
        saveButton.setDisable(true);  // Initially disabled until posts are previewed
        
        backButton = new Button("Back");
        backButton.setOnAction(e -> {
        	viewFacade.navigateToDashboard(user);
        });
        
        // Add label "Choose a CSV file"
        Label csvLabel = new Label("Choose a CSV file");
        
        HBox chooserLayout = new HBox(10);
        chooserLayout.getChildren().addAll(csvLabel, importButton);
        chooserLayout.setAlignment(Pos.TOP_LEFT);
        chooserLayout.setPadding(new Insets(20, 20, 30, 20));
       

        mainLayout = new VBox(10);
        mainLayout.getChildren().addAll(chooserLayout, postListView, saveButton, backButton);
        mainLayout.setAlignment(Pos.CENTER);
        mainLayout.setPadding(new Insets(20, 20, 30, 20));

    }
    
    @Override
    protected void show() {
    	stage.setScene(new Scene(mainLayout, 500, 400));
        stage.setTitle("Bulk Import");
        stage.show();
    }
    
    @Override
    public void previewImportedPosts(File selectedFile) {
        try {
            validPosts = viewFacade.previewImportedPosts(selectedFile, user);
            postListView.getItems().clear();
            for (Post post : validPosts) {
                postListView.getItems().add(formatPostForDisplay(post));
            }
            saveButton.setDisable(false);
        } catch (CsvLoadingException e) {
        	viewFacade.showAlert(AlertType.ERROR, "Error", "Error reading CSV file: " + e.getMessage());
        }
    }
    
    @Override
    public String formatPostForDisplay(Post post) {
        return String.format("Content: %s | Likes: %d | Shares: %d", post.getContent(), post.getLikes(), post.getShares());
    }
    
    @Override
    public void handleSave() {
        boolean success = viewFacade.addBulkPosts(validPosts);
        if (success) {
        	viewFacade.showAlert(AlertType.INFORMATION, "Success", "Posts imported successfully!");
            viewFacade.navigateToDashboard(user);
        } else {
        	viewFacade.showAlert(AlertType.ERROR, "Error", "There was an error importing posts.");
        }
    }
}