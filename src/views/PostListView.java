package views;

import java.io.File;
import java.util.List;

import controllers.PostController;
import controllers.UserController;
import enums.FilterBy;
import enums.SortBy;
import exceptions.PostException;
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
import views.facade.GUIViewFacadeInterface;
import views.interfaces.PostListViewInterface;

/**
 * The PostListView class provides a graphical interface for users to manage and interact
 * with their posts. It allows for viewing, editing, deleting, retrieving, and exporting posts.
 */
public class PostListView extends BaseView implements PostListViewInterface {

    private Stage stage;
    private User user;
    private TableView<Post> postsTable;
    private ObservableList<Post> postsData;
    private Button backButton, editButton, deleteButton, exportButton, retrieveButton, clearTableButton, resetButton, topPostsButton;
    private TextField topNInput, postIdInput;
    private ComboBox<String> sortByDropdown, filterByDropdown;
    private VBox layout;
    
    private GUIViewFacadeInterface viewFacade;
    
    /**
     * Constructs the PostListView.
     * 
     * @param stage The primary stage for this view.
     * @param user The logged-in user.
     * @param postController The controller for post-related operations.
     * @param userController The controller for user-related operations.
     */
    public PostListView(Stage stage, User user, PostController postController, UserController userController) {
        this.user = user;
        this.stage = stage;
        this.viewFacade = new GUIViewFacade(stage, userController, postController);
        
        initializeComponents();
        show();
        System.out.println("PostListView initialized.");
    }

    @SuppressWarnings("unchecked")
    @Override
	protected void initializeComponents() {
    	try {
			postsData = FXCollections.observableArrayList(viewFacade.getPostsByUser(user));
		} catch (PostException e) {
			e.printStackTrace();
		}
		 
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
		 
		postsTable.getColumns().addAll(idCol, contentCol, authorCol, likesCol, sharesCol, dateCol);

		postsTable.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
		    if (newSelection != null) {
		    	// Comparison on user ID because username is changeable
		    	if (newSelection.getUserId() == user.getUserId()) {			
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
		        try {
					handleDeletePost(selectedPost);
				} catch (PostException e1) {
					e1.printStackTrace();
				}
		    }
		});
		
		topNInput = new TextField();
		topNInput.setPromptText("Enter N");
		
		// For post retrieval by ID:
		postIdInput = new TextField();
		postIdInput.setPromptText("Enter Post ID");
		
		retrieveButton = new Button("Retrieve Post by ID");
		retrieveButton.setOnAction(e -> {
			try {
				handleRetrievePostById();
			} catch (PostException e1) {
				e1.printStackTrace();
			}
		});
		
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
        topPostsButton.setOnAction(e -> {
			try {
				handleTopPosts();
			} catch (PostException e1) {
				e1.printStackTrace();
			}
		});
		
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
	    layout = new VBox(10, postsTable, postIDControls, topNControls, postActions, navigationActions);
	    layout.setPadding(new Insets(20));  // Add padding to the main layout
		
    }
    
    @Override
    protected void show() { 
    	stage.setScene(new Scene(layout, 800, 600));
		stage.setTitle("My Posts");
		stage.show();
        System.out.println("PostListView initialized.");
    }
    
    @Override
    public void handleBack() {
        viewFacade.navigateToDashboard(user);
        System.out.println("Navigating back to dashboard.");
    }
    
    @Override
    public void handleRetrievePostById() throws PostException {
    	try {
            Post post = viewFacade.getPostByID(Integer.parseInt(postIdInput.getText()));
            if (post != null) {
                postsData.clear();
                postsData.add(post);
            } else {
                // Show an error message to the user indicating the post was not found or not authored by the user.
            	viewFacade.showAlert(AlertType.ERROR, "Error", "Post not found!");
            }
            System.out.println("Retrieved post by ID.");

        } catch (NumberFormatException e) {
        	viewFacade.showAlert(AlertType.ERROR, "Error", "Invalid Post ID format!");
        }
    }
    
    @Override
    public void handleEditPost(Post selectedPost) {
        viewFacade.navigateToEditPost(user, selectedPost);
        System.out.println("Editing selected post.");
    }
    
    @Override
    public void handleDeletePost(Post selectedPost) throws PostException {
    	try {
    		viewFacade.deletePost(selectedPost);
            postsData.remove(selectedPost);  // Refresh the table
            System.out.println("Deleted selected post.");
        } catch (Exception e) {
            System.err.println("Error deleting post: " + e.getMessage());
            viewFacade.showAlert(AlertType.ERROR, "Error", "An unexpected error occurred while deleting post: " + e.getMessage());
        }
    }
    
    @Override
    public void handleTopPosts() throws PostException {
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
    
    @Override
    public void handleExportPost(Post post) {
    	try {
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
            System.out.println("Exported selected post.");
        } catch (Exception e) {
            System.err.println("Error exporting post: " + e.getMessage());
            viewFacade.showAlert(AlertType.ERROR, "Error", "An unexpected error occurred while exporting post: " + e.getMessage());
        }
    }
}