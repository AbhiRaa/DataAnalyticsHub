package views;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

import controllers.PostController;
import controllers.UserController;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import models.Post;
import models.User;

public class PostListView {

    private Stage stage;
    private PostController postController;
    private UserController userController;
    private User user;
    private TableView<Post> postsTable;
    private ObservableList<Post> postsData;
    private Button backButton, editButton, deleteButton, exportButton, retrieveButton, clearTableButton, resetButton, topPostsButton;
    private TextField topNInput, postIdInput;
    private ComboBox<String> sortByDropdown, filterByDropdown;

    public PostListView(Stage stage, User user, PostController postController, UserController userController) {
        this.postController = postController;
        this.userController = userController;
        this.user = user;
        this.stage = stage;
        initializeComponents();
    }

    private void initializeComponents() {
    	
		 postsData = FXCollections.observableArrayList(postController.getPostsByUser(user));
		 
		 postsTable = new TableView<>();
		 postsTable.setItems(postsData);
		
		 // Define columns
		 TableColumn<Post, Integer> idCol = new TableColumn<>("ID");
		 idCol.setCellValueFactory(new PropertyValueFactory<>("postId"));
		 
		 TableColumn<Post, String> contentCol = new TableColumn<>("Content");
		 contentCol.setCellValueFactory(new PropertyValueFactory<>("content"));
		 
		 TableColumn<Post, String> authorCol = new TableColumn<>("Author");
		 authorCol.setCellValueFactory(new PropertyValueFactory<>("author"));
		
		 TableColumn<Post, Integer> likesCol = new TableColumn<>("Likes");
		 likesCol.setCellValueFactory(new PropertyValueFactory<>("likes"));
		
		 TableColumn<Post, Integer> sharesCol = new TableColumn<>("Shares");
		 sharesCol.setCellValueFactory(new PropertyValueFactory<>("shares"));
		
		 TableColumn<Post, String> dateCol = new TableColumn<>("Date");
		 dateCol.setCellValueFactory(new PropertyValueFactory<>("dateTime"));
		 
//		 TableColumn<Post, LocalDateTime> createdDateCol = new TableColumn<>("Created Date");
//		 createdDateCol.setCellValueFactory(new PropertyValueFactory<>("createdDate"));
//		 
//		 TableColumn<Post, LocalDateTime> UpdatedOnCol = new TableColumn<>("Updated On");
//		 UpdatedOnCol.setCellValueFactory(new PropertyValueFactory<>("updatedOn"));
		
		 postsTable.getColumns().addAll(idCol, contentCol, authorCol, likesCol, sharesCol, dateCol);

		backButton = new Button("Back");
		backButton.setOnAction(e -> handleBack());
		
		editButton = new Button("Edit Selected Post");
		editButton.setOnAction(e -> {
		    Post selectedPost = postsTable.getSelectionModel().getSelectedItem();
		    if (selectedPost != null) {
		        handleEditPost(selectedPost);
		    }
		});
		
		deleteButton = new Button("Delete Selected Post");
		deleteButton.setOnAction(e -> {
		    Post selectedPost = postsTable.getSelectionModel().getSelectedItem();
		    if (selectedPost != null) {
		        handleDeletePost(selectedPost);
		    }
		});
		
		topNInput = new TextField();
		topNInput.setPromptText("Enter N");
		
		// For post retrieval by ID:
		postIdInput = new TextField();
		postIdInput.setPromptText("Enter Post ID");
		
		retrieveButton = new Button("Retrieve Post by ID");
		retrieveButton.setOnAction(e -> handleRetrievePostById());
		
		clearTableButton = new Button("Clear List");
		clearTableButton.setOnAction(e -> postsTable.getItems().clear());
		
		resetButton = new Button("Reload my posts");
		resetButton.setOnAction(e -> new PostListView(stage, user, postController, userController));
		
		sortByDropdown = new ComboBox<>();
        sortByDropdown.getItems().addAll("By Likes", "By Shares");
        sortByDropdown.setValue("By Likes"); // Default value

        filterByDropdown = new ComboBox<>();
        filterByDropdown.getItems().addAll("My Posts", "All Posts");
        filterByDropdown.setValue("My Posts"); // Default value

        topPostsButton = new Button("Top Posts");
        topPostsButton.setOnAction(e -> handleTopPosts());
		
		exportButton = new Button("Export Selected Post");
		exportButton.setOnAction(e -> {
		    Post selectedPost = postsTable.getSelectionModel().getSelectedItem();
		    if (selectedPost != null) {
		        handleExportPost(selectedPost);
		    }
		});
		
		 // Define HBoxes for grouping related controls
	    HBox postIDControls = new HBox(10, postIdInput, retrieveButton, clearTableButton);
	    HBox topNControls = new HBox(10, topNInput, sortByDropdown, filterByDropdown, topPostsButton);
	    HBox postActions = new HBox(10, editButton, deleteButton, exportButton);
	    HBox navigationActions = new HBox(10, backButton, resetButton);

	    // Setting padding for the HBoxes
	    postIDControls.setPadding(new Insets(10, 0, 10, 0));
	    topNControls.setPadding(new Insets(10, 0, 10, 0));
	    postActions.setPadding(new Insets(10, 0, 10, 0));
	    navigationActions.setPadding(new Insets(10, 0, 10, 0));

	    // Create the main layout and add components
	    VBox layout = new VBox(10, postsTable, postIDControls, topNControls, postActions, navigationActions);
	    layout.setPadding(new Insets(20));  // Add padding to the main layout
		
		stage.setScene(new Scene(layout, 800, 600));
		stage.setTitle("My Posts");
		stage.show();
    }

    private void handleBack() {
        new DashboardView(stage, user, postController, userController);
        //stage.close();
    }
    
    private void handleRetrievePostById() {
    	try {
            int postId = Integer.parseInt(postIdInput.getText());
            Post post = postController.getPostByID(postId);
            if (post != null && post.getAuthor().equals(user.getUsername())) {
                postsData.clear();
                postsData.add(post);
            } else {
                // Show an error message to the user indicating the post was not found or not authored by the user.
            	showAlert(AlertType.ERROR, "Error", "Post not found or not authored by you!");
            }
        } catch (NumberFormatException e) {
        	showAlert(AlertType.ERROR, "Error", "Invalid Post ID format!");
        }
    }

    private void handleEditPost(Post selectedPost) {
        new PostFormView(stage, user, postController, userController, selectedPost);
        //stage.close();
    }

    private void handleDeletePost(Post selectedPost) {
        postController.deletePost(selectedPost);
        postsData.remove(selectedPost);  // Refresh the table

       // postsTable.getItems().remove(selectedPost);  // Refresh the list
    }
    
    private void handleTopPosts() {
        int n;
        String sortBy = sortByDropdown.getValue();
        String filterBy = filterByDropdown.getValue();

        try {
            if (topNInput.getText().isEmpty()) {
                showAlert(AlertType.ERROR, "Error", "Please enter a number in the 'Enter N' field!");
                return;
            }

            n = Integer.parseInt(topNInput.getText());
            List<Post> topPosts;

            if ("By Likes".equals(sortBy)) {
                if ("My Posts".equals(filterBy)) {
                    topPosts = postController.getTopNPostsByLikes(n, user);
                } else {
                    topPosts = postController.getTopNPostsByLikes(n, null);
                }
            } else {
                if ("My Posts".equals(filterBy)) {
                    topPosts = postController.getTopNPostsByShares(n, user);
                } else {
                    topPosts = postController.getTopNPostsByShares(n, null);
                }
            }

            postsTable.getItems().clear();
            if (topPosts != null) {
                postsData.addAll(topPosts);
            }
        } catch (NumberFormatException e) {
            showAlert(AlertType.ERROR, "Error", "Invalid number format in the 'Enter N' field!");
        }
    }

    private void handleExportPost(Post post) {
        // This method requires further implementation to export the selected post to a CSV file.
    	FileChooser fileChooser = new FileChooser();

        // Set extension filter
        FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("CSV files (*.csv)", "*.csv");
        fileChooser.getExtensionFilters().add(extFilter);

        // Show save file dialog
        File file = fileChooser.showSaveDialog(stage);

        if (file != null) {
            savePostToFile(post, file);
            showAlert(AlertType.INFORMATION, "Success", "Posts imported successfully!");
        }
    }
        
    private void savePostToFile(Post post, File file) {
        try {
            FileWriter fileWriter = new FileWriter(file);

            fileWriter.append("ID,Content,Author,Likes,Shares,DateTime\n");
            fileWriter.append(String.valueOf(post.getPostId()));
            fileWriter.append(",");
            fileWriter.append(post.getContent());
            fileWriter.append(",");
            fileWriter.append(post.getAuthor());
            fileWriter.append(",");
            fileWriter.append(String.valueOf(post.getLikes()));
            fileWriter.append(",");
            fileWriter.append(String.valueOf(post.getShares()));
            fileWriter.append(",");
            fileWriter.append(post.getDateTime().toString());
            fileWriter.append("\n");

            fileWriter.flush();
            fileWriter.close();
        } catch (IOException e) {
            System.out.println("Error writing to file: " + e.getMessage());
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