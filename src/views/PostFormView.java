package views;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import controllers.PostController;
import controllers.UserController;
import database.DBManager;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import models.Post;
import models.User;

public class PostFormView {

    private Stage stage;
    private PostController postController;
    private User user;
    private TextField idField, contentField, authorField, likesField, sharesField;
    private DatePicker datePicker;
    private Button saveButton, cancelButton;

    public PostFormView(PostController postController, User user, Post existingPost) {
        System.out.println("Constructing with postController: " + postController);

    	this.postController = postController;
        this.user = user;
        this.stage = new Stage();
        initializeComponents(existingPost);
    }
    
    public PostFormView(PostController postController, User user) {
        this(postController, user, null);
    }
    
    private static DateTimeFormatter formatter = DateTimeFormatter.ofPattern("d/MM/yyyy HH:mm");

    private void initializeComponents(Post existingPost) {
//        idField = new TextField();
//        idField.setPromptText("Post ID");
//        if (existingPost != null) idField.setText(String.valueOf(existingPost.getPostId()));

        contentField = new TextField();
        contentField.setPromptText("Content");
        if (existingPost != null) contentField.setText(existingPost.getContent());

//        authorField = new TextField();
//        authorField.setPromptText("Author");
//        if (existingPost != null) authorField.setText(existingPost.getAuthor());

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

        VBox layout = new VBox(10);
        layout.getChildren().addAll(contentField, likesField, sharesField, datePicker, saveButton, cancelButton);

        stage.setScene(new Scene(layout, 300, 350));
        stage.setTitle(existingPost == null ? "Add Post" : "Edit Post");
        stage.show();
    }

    private void handleSave(Post existingPost) {
        try {
        	System.out.println("PostFormView - User's ID: " + user.getUserId());

            //int postId = existingPost == null ? postController.getNextPostId() : Integer.parseInt(idField.getText());
            String content = contentField.getText();
            String author = user.getUsername(); // Set the current user as the author
            int likes = Integer.parseInt(likesField.getText());
            int shares = Integer.parseInt(sharesField.getText()); 
            LocalDate date = datePicker.getValue();
            LocalDateTime dateTime = date.atStartOfDay();  // Convert LocalDate to LocalDateTime (at midnight)
            
            // Convert LocalDate to the expected date-time string format
            String formattedDateTime = dateTime.format(formatter);

            if (existingPost == null) {
                // For new posts, we don't need to set the PostID as it will be auto-incremented
                Post post = new Post(content, author, likes, shares, formattedDateTime, user.getUserId());
                postController.addPost(post);
            } else {
                // For existing posts, we keep the PostID and update other fields
                int postId = Integer.parseInt(idField.getText());
                Post post = new Post(postId, content, author, likes, shares, formattedDateTime, user.getUserId());
                postController.updatePost(post);
            }

//            if (existingPost == null) {
//                postController.addPost(post);
//            } else {
//                postController.updatePost(post);
//            }

            new DashboardView(user, postController, new UserController(new DBManager())); // Return to the Dashboard
            stage.close();
        } catch (Exception e) {
            showError("Error saving the post. Please ensure all fields are correctly filled.");
            e.printStackTrace(); 
        }
    }



    private void handleCancel() {
        new DashboardView(user, postController, new UserController(new DBManager())); // Return to the Dashboard
        stage.close();
    }

    private void showError(String message) {
        // Display error message to user using a JavaFX Alert or similar
    }
}

