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
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import models.Post;
import models.User;
import views.facade.GUIViewFacade;
import views.facade.GUIViewFacadeInterface;
import views.interfaces.PostFormViewInterface;

/**
 * The PostFormView class provides a graphical interface for creating a new post or editing an existing one.
 * It captures details like the content, likes, shares, and date of the post.
 */
public class PostFormView extends BaseView implements PostFormViewInterface {

    private Stage stage;
    private User user;
    private Post existingPost;
    private TextField contentField, likesField, sharesField;
    private DatePicker datePicker;
    private Button saveButton, cancelButton;
    private VBox mainLayout;
    
    private GUIViewFacadeInterface viewFacade;
    
    private static DateTimeFormatter formatter = DateTimeFormatter.ofPattern("d/MM/yyyy HH:mm");
    
    /**
     * Constructor for editing an existing post.
     * 
     * @param stage The primary stage for this view.
     * @param user The current logged in user.
     * @param postController The controller for post-related operations.
     * @param userController The controller for user-related operations.
     * @param existingPost The post to be edited.
     */
    public PostFormView(Stage stage, User user, PostController postController, UserController userController, Post existingPost) {
        this.user = user;
        this.stage = stage;
        this.existingPost = existingPost;
        viewFacade = new GUIViewFacade(stage, userController, postController);
        
        initializeComponents();
        show();
        System.out.println("PostFormView initialized for editing post with ID: " + (existingPost != null ? existingPost.getPostId() : "New post"));
    }
    
    public PostFormView(Stage stage, User user, PostController postController, UserController userController) {
        this(stage, user, postController, userController, null);
    }
    	
    @Override
    protected void initializeComponents() {
    	
    	ImageView logoView = new ImageView(new Image("/image/post.png"));
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
        cancelButton.setOnAction(e -> {
        	if (existingPost == null) 
        		viewFacade.navigateToDashboard(user); 
        	else
        		viewFacade.navigateToMyPosts(user);
        });

        VBox postLayout = new VBox(10);
        postLayout.getChildren().addAll(new Label("Content"), contentField, 
        		new Label("Likes"), likesField, 
        		new Label("Shares"), sharesField, 
        		new Label("Date Time"), datePicker, 
        		saveButton, 
        		cancelButton);
        postLayout.setAlignment(Pos.CENTER_LEFT);
        postLayout.setPadding(new Insets(20, 20, 30, 20));
        
        mainLayout = new VBox(10);
        mainLayout.getChildren().addAll(logoView, postLayout, saveButton, cancelButton);
        mainLayout.setAlignment(Pos.CENTER);
        mainLayout.setPadding(new Insets(20, 20, 30, 20));
        
    }
    
    @Override
    protected void show() {
    	stage.setScene(new Scene(mainLayout, 400, 550));
        stage.setTitle(existingPost == null ? "Add Post" : "Edit Post");
        stage.show();
        System.out.println("PostFormView displayed.");
    }
    
    @Override
    public void handleSave(Post existingPost) {
        try {
            System.out.println("Save process started for post with ID: " + (existingPost != null ? existingPost.getPostId() : "New post"));
            
            saveButton.setDisable(true); // Disable the button to prevent multiple clicks
            saveButton.setText("Saving...");
            
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
                
                // Check for negative values
                if (likes < 0 || shares < 0) {
                    viewFacade.showAlert(AlertType.ERROR, "Error", "Likes and Shares cannot have negative values!");
                    return;
                }
                
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
                viewFacade.addPost(content, author, likes, shares, formattedDateTime, user);
                viewFacade.showAlert(AlertType.INFORMATION, "Success", "Post created successfully!");
                viewFacade.navigateToDashboard(user); // Return to the Dashboard
                
            } else {
                // For existing posts, we keep the PostID and update other fields
                int postId = existingPost.getPostId();
                viewFacade.editPost(postId, content, author, likes, shares, formattedDateTime, user);
                viewFacade.showAlert(AlertType.INFORMATION, "Success", "Post updated successfully!");
                viewFacade.navigateToMyPosts(user);
            }
            
        } catch (Exception e) {
            viewFacade.showAlert(AlertType.ERROR, "Error", "Error saving the post. Please ensure all fields are correctly filled.");
        } finally {
            // Resetting the button state after save
            saveButton.setDisable(false);
            saveButton.setText("Save");
        }
    }
}