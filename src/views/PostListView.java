package views;

import java.io.File;
import java.util.List;

import controllers.PostController;
import controllers.UserController;
import enums.FilterBy;
import enums.SortBy;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import models.Post;
import models.User;
import views.facade.GUIViewFacade;

public class PostListView {

    private Stage stage;
    private User user;
    private TableView<Post> postsTable;
    private ObservableList<Post> postsData;
    private Button backButton, editButton, deleteButton, exportButton, retrieveButton, clearTableButton, resetButton, topPostsButton;
    private TextField topNInput, postIdInput;
    private ComboBox<String> sortByDropdown, filterByDropdown;
    private GUIViewFacade viewFacade;

    public PostListView(Stage stage, User user, PostController postController, UserController userController) {
        this.user = user;
        this.stage = stage;
        this.viewFacade = new GUIViewFacade(stage, userController, postController);
        initializeComponents();
    }

    @SuppressWarnings("unchecked")
	private void initializeComponents() {
    	postsData = FXCollections.observableArrayList(viewFacade.getPostsByUser(user));
		 
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

		postsTable.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
		    if (newSelection != null) {
		        if (newSelection.getAuthor().equals(user.getUsername())) {
		            // Enable buttons if the post belongs to the current user
		            editButton.setDisable(false);
		            deleteButton.setDisable(false);
		            exportButton.setDisable(false);
		        } else {
		            // Disable buttons if the post doesn't belong to the current user
		            editButton.setDisable(true);
		            deleteButton.setDisable(true);
		            exportButton.setDisable(true);
		        }
		    }
		});
		
		backButton = new Button("Back");
		backButton.setOnAction(e -> handleBack());
		
		editButton = new Button("Edit Selected Post");
		editButton.setDisable(true);
		editButton.setOnAction(e -> {
		    Post selectedPost = postsTable.getSelectionModel().getSelectedItem();
		    if (selectedPost != null) {
		        handleEditPost(selectedPost);
		    }
		});
		
		deleteButton = new Button("Delete Selected Post");
		deleteButton.setDisable(true);
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
		resetButton.setOnAction(e -> viewFacade.navigateToMyPosts(user));
		
		sortByDropdown = new ComboBox<>();
        sortByDropdown.getItems().addAll("By Likes", "By Shares");
        sortByDropdown.setValue("By Likes"); // Default value

        filterByDropdown = new ComboBox<>();
        filterByDropdown.getItems().addAll("My Posts", "All Posts");
        filterByDropdown.setValue("My Posts"); // Default value

        topPostsButton = new Button("Top Posts");
        topPostsButton.setOnAction(e -> handleTopPosts());
		
		exportButton = new Button("Export Selected Post");
		exportButton.setDisable(true);
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
        viewFacade.navigateToDashboard(user);
    }
    
    private void handleRetrievePostById() {
    	try {
            Post post = viewFacade.getPostByID(Integer.parseInt(postIdInput.getText()));
            if (post != null) {
                postsData.clear();
                postsData.add(post);
            } else {
                // Show an error message to the user indicating the post was not found or not authored by the user.
            	viewFacade.showAlert(AlertType.ERROR, "Error", "Post not found!");
            }
        } catch (NumberFormatException e) {
        	viewFacade.showAlert(AlertType.ERROR, "Error", "Invalid Post ID format!");
        }
    }

    private void handleEditPost(Post selectedPost) {
        viewFacade.navigateToEditPost(user, selectedPost);
    }

    private void handleDeletePost(Post selectedPost) {
    	viewFacade.deletePost(selectedPost);
        postsData.remove(selectedPost);  // Refresh the table

    }
    
    private void handleTopPosts() {
        int n;
        String sortBy = sortByDropdown.getValue();
        String filterBy = filterByDropdown.getValue();

        try {
            if (topNInput.getText().isEmpty()) {
            	viewFacade.showAlert(AlertType.ERROR, "Error", "Please enter a number in the 'Enter N' field!");
                return;
            }

            n = Integer.parseInt(topNInput.getText());
            List<Post> topPosts;

            if (SortBy.By_Likes.getSortBy().equals(sortBy)) {
                if (FilterBy.My_posts.getFilterBy().equals(filterBy)) {
                    topPosts = viewFacade.getTopNPostsByLikes(n, user);
                } else {
                    topPosts = viewFacade.getTopNPostsByLikes(n, null);
                }
            } else {
                if (FilterBy.My_posts.getFilterBy().equals(filterBy)) {
                    topPosts = viewFacade.getTopNPostsByShares(n, user);
                } else {
                    topPosts = viewFacade.getTopNPostsByShares(n, null);
                }
            }

            postsTable.getItems().clear();
            if (topPosts != null) {
                postsData.addAll(topPosts);
            }
        } catch (NumberFormatException e) {
        	viewFacade.showAlert(AlertType.ERROR, "Error", "Invalid number format in the 'Enter N' field!");
        }
    }

    private void handleExportPost(Post post) {
    	
        // Show save file dialog
        File file = viewFacade.handleExportPost();

        if (file != null) {
            boolean isSaved = viewFacade.savePostToFile(post, file);
            if (isSaved) 
            	viewFacade.showAlert(AlertType.INFORMATION, "Success", "Post exported successfully!");
            else
            	viewFacade.showAlert(AlertType.ERROR, "Error", "Error while exporting. Please try again!");

        } else { 
        	viewFacade.showAlert(AlertType.ERROR, "Error", "Error occured!");
        }
    }
}