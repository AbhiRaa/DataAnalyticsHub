package views;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import controllers.PostController;
import controllers.UserController;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import models.Post;
import models.User;
import views.facade.GUIViewFacade;

public class PostFormView {

    private Stage stage;
    private User user;
    private TextField contentField, likesField, sharesField;
    private DatePicker datePicker;
    private Button saveButton, cancelButton;
    
    private GUIViewFacade viewFacade;
    
    private static DateTimeFormatter formatter = DateTimeFormatter.ofPattern("d/MM/yyyy HH:mm");


    public PostFormView(Stage stage, User user, PostController postController, UserController userController, Post existingPost) {
        this.user = user;
        this.stage = stage;
        
        viewFacade = new GUIViewFacade(stage, userController, postController);
        
        initializeComponents(existingPost);
    }
    
    public PostFormView(Stage stage, User user, PostController postController, UserController userController) {
        this(stage, user, postController, userController, null);
    }
    
    private void initializeComponents(Post existingPost) {
    	
    	ImageView logoView = new ImageView(new Image("/image/new-post.png"));
        logoView.setFitWidth(100);
        logoView.setPreserveRatio(true);
        
        contentField = new TextField();
        contentField.setPromptText("Content");
        if (existingPost != null) contentField.setText(existingPost.getContent());

        likesField = new TextField();
        likesField.setPromptText("Likes");
        if (existingPost != null) likesField.setText(String.valueOf(existingPost.getLikes()));

        sharesField = new TextField();
        sharesField.setPromptText("Shares");
        if (existingPost != null) sharesField.setText(String.valueOf(existingPost.getShares()));

        datePicker = new DatePicker();
        if (existingPost != null) datePicker.setValue(existingPost.getDateTime().toLocalDate());

        saveButton = new Button("Save");
        saveButton.setOnAction(e -> handleSave(existingPost));

        cancelButton = new Button("Cancel");
        cancelButton.setOnAction(e -> handleCancel());

        VBox postLayout = new VBox(10);
        postLayout.getChildren().addAll(new Label("Content"), contentField, 
        		new Label("Likes"), likesField, 
        		new Label("Shares"), sharesField, 
        		new Label("Date Time"), datePicker, 
        		saveButton, 
        		cancelButton);
        postLayout.setAlignment(Pos.CENTER_LEFT);
        postLayout.setPadding(new Insets(20, 20, 30, 20));
        
        VBox mainLayout = new VBox(10);
        mainLayout.getChildren().addAll(logoView, postLayout, saveButton, cancelButton);
        mainLayout.setAlignment(Pos.CENTER);
        mainLayout.setPadding(new Insets(20, 20, 30, 20));
        
        stage.setScene(new Scene(mainLayout, 400, 500));
        stage.setTitle(existingPost == null ? "Add Post" : "Edit Post");
        stage.show();
    }

    private void handleSave(Post existingPost) {
        try {
        	// System.out.println("PostFormView - User's ID: " + user.getUserId());

            String content = contentField.getText();
            
            if (content == null || content.trim().isEmpty()) {
                viewFacade.showAlert(AlertType.ERROR, "Error", "Content cannot be empty!");
                return;
            }
            
            String author = user.getUsername(); // Set the current user as the author of the post
            
            int likes;
            int shares;

            try {
                likes = Integer.parseInt(likesField.getText());
                shares = Integer.parseInt(sharesField.getText());
            } catch (NumberFormatException nfe) {
            	viewFacade.showAlert(AlertType.ERROR, "Error", "Please enter valid numbers for Likes and Shares!");
                return;
            }
            
            LocalDate date = datePicker.getValue();
            if (date == null) {
            	viewFacade.showAlert(AlertType.ERROR, "Error", "Please select a valid date!");
                return;
            }
            
            LocalDateTime dateTime = date.atStartOfDay();  // Convert LocalDate to LocalDateTime (at midnight)
            
            // Convert LocalDate to the expected date-time string format
            String formattedDateTime = dateTime.format(formatter);

            if (existingPost == null) {
                // For new posts, we don't need to set the PostID as it will be auto-incremented
//                Post post = new Post(content, author, likes, shares, formattedDateTime, user.getUserId());
//                postController.addPost(post);
//                new DashboardView(stage, user, postController, userController); // Return to the Dashboard
                	
                viewFacade.addPost(content, author, likes, shares, formattedDateTime, user);
                viewFacade.showAlert(AlertType.INFORMATION, "Success", "Post created successfully!");
                viewFacade.navigateToDashboard(user); // Return to the Dashboard
                
            } else {
                // For existing posts, we keep the PostID and update other fields
                int postId = existingPost.getPostId();
//                Post post = new Post(postId, content, author, likes, shares, formattedDateTime, user.getUserId());
//                postController.updatePost(post);
//                new PostListView(stage, user, postController, userController);
                viewFacade.editPost(postId, content, author, likes, shares, formattedDateTime, user);
                viewFacade.showAlert(AlertType.INFORMATION, "Success", "Post updated successfully!");
                viewFacade.navigateToMyPosts(user);
            }
            
        } catch (Exception e) {
            //showError("Error saving the post. Please ensure all fields are correctly filled.");
            viewFacade.showAlert(AlertType.ERROR, "Error", "Error saving the post. Please ensure all fields are correctly filled.");
        }
    }


    private void handleCancel() {
        // new DashboardView(stage, user, postController, new UserController(new DBManager())); // Return to the Dashboard
    	viewFacade.navigateToDashboard(user); 	// Return to the Dashboard
    }
}

