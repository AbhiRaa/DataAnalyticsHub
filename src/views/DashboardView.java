package views;

import controllers.PostController;
import controllers.UserController;
import exceptions.UserException;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.stage.Stage;
import models.User;
import views.facade.GUIViewFacade;
import views.facade.GUIViewFacadeInterface;
import views.interfaces.DashboardViewInterface;

/**
 * The DashboardView provides a graphical interface for the main dashboard where users can interact with various features.
 * It offers options to add posts, view posts, edit the profile, logout, and provides links to other premium features
 * for VIP users.
 */
public class DashboardView extends BaseView implements DashboardViewInterface {

    private Stage stage;
    private User user;
    private Label welcomeMessage, vipStatusLabel;
    private Button addPostButton, viewPostsButton, editProfileButton, logoutButton, dataVisualization, importPosts;
    private Hyperlink vipUpgradeLink;
    private Hyperlink cancelVIPSubscriptionLink = null;
    private BorderPane mainLayout;
    
    private GUIViewFacadeInterface viewFacade;
    
    /**
     * Constructs the DashboardView.
     * 
     * @param stage The primary stage for this view.
     * @param user The current logged-in user.
     * @param postController The controller for post-related operations.
     * @param userController The controller for user-related operations.
     */
    public DashboardView(Stage stage, User user, PostController postController, UserController userController) {
        this.user = user;
        this.stage = stage;
        
        this.viewFacade = new GUIViewFacade(stage, userController, postController);
        
        initializeComponents();
        show();
        System.out.println("DashboardView initialized for user: " + user.getUsername());
    }
    
    @Override
    protected void initializeComponents() {
    	
        welcomeMessage = new Label("Welcome, " + user.getFirstName() + " " + user.getLastName() + "!");
        welcomeMessage.setTextFill(Color.GREEN);
        welcomeMessage.setFont(Font.font("Arial", 20));
        
        addPostButton = new Button("Add Post");
        addPostButton.setOnAction(e -> handleAddPost());

        viewPostsButton = new Button("View My Posts");
        viewPostsButton.setOnAction(e -> handleViewPosts());

        editProfileButton = new Button("Edit Profile");
        editProfileButton.setOnAction(e -> handleEditProfile());

        logoutButton = new Button("Logout");
        logoutButton.setOnAction(e -> handleLogout());
        
        HBox userFeatures = new HBox(10, addPostButton, viewPostsButton, editProfileButton);
        HBox vipFeatures = new HBox(10);
        
        userFeatures.setAlignment(Pos.CENTER);
        vipFeatures.setAlignment(Pos.CENTER);

        if (!user.isVIP()) {
        	vipStatusLabel = new Label("Standard User");
            vipStatusLabel.setTextFill(Color.GRAY); // Setting color to GRAY for standard status
            
            vipUpgradeLink = new Hyperlink("Become a VIP user.");
            vipUpgradeLink.setOnAction(e -> handleUpgradeToVIP());
        } else {
        	vipStatusLabel = new Label("VIP User");
        	vipStatusLabel.setTextFill(Color.GOLD); // Setting color to GOLD for VIP status
        	
        	cancelVIPSubscriptionLink = new Hyperlink("Cancel VIP Subscription");
        	cancelVIPSubscriptionLink.setOnAction(e -> {
				try {
					handleDegrade();
				} catch (UserException e1) {
					e1.printStackTrace();
				}
			});
        	
        	dataVisualization = new Button("Posts Visualization");
        	dataVisualization.setOnAction(e -> handleVisualization());
        	
        	importPosts = new Button("Import Posts");
        	importPosts.setOnAction(e -> handleImports());
        	
        	vipFeatures.getChildren().addAll(dataVisualization, importPosts);
        }
        
        vipStatusLabel.setFont(Font.font("Arial", FontPosture.ITALIC, 14)); // Styling the label
        
        // Add this Label to the top right corner of your BorderPane (mainLayout)
        HBox topRightLayout = new HBox(vipStatusLabel);
        topRightLayout.setAlignment(Pos.TOP_RIGHT);
        topRightLayout.setPadding(new Insets(20, 20, 0, 0));  // Padding on top and right
        
        VBox centerLayout = new VBox(20);
        centerLayout.getChildren().addAll(userFeatures, vipFeatures);
        centerLayout.setAlignment(Pos.CENTER);
        centerLayout.setPadding(new Insets(20));  // Add padding to the main layout
        
        // Logout icon
        Image logoutImage = new Image(getClass().getResourceAsStream("/image/logout.png"));
        ImageView logoutImageView = new ImageView(logoutImage);
        logoutImageView.setFitHeight(30);
        logoutImageView.setFitWidth(30);
        logoutButton.setGraphic(logoutImageView);

        HBox bottomRightLayout = new HBox(10);
        if (vipUpgradeLink != null) {
            bottomRightLayout.getChildren().addAll(vipUpgradeLink, logoutButton);
        } else if (cancelVIPSubscriptionLink != null) {
            bottomRightLayout.getChildren().addAll(cancelVIPSubscriptionLink, logoutButton);
        } else {
            bottomRightLayout.getChildren().add(logoutButton);
        }
        bottomRightLayout.setAlignment(Pos.BOTTOM_RIGHT);
        bottomRightLayout.setPadding(new Insets(0, 20, 20, 0));  // Padding on right and bottom

        mainLayout = new BorderPane();
        mainLayout.setTop(welcomeMessage);
        mainLayout.setRight(topRightLayout);
        mainLayout.setCenter(centerLayout);
        mainLayout.setBottom(bottomRightLayout);
        BorderPane.setAlignment(welcomeMessage, Pos.TOP_CENTER);
        BorderPane.setMargin(welcomeMessage, new Insets(20, 0, 0, 0));
        System.out.println("DashboardView components initialized.");
    }
    
    @Override
    protected void show() {
        stage.setScene(new Scene(mainLayout, 600, 400));
        stage.setTitle("Dashboard");
        stage.show();
    }
    
    @Override
	public void handleAddPost() {
        // Switch to the PostFormView to allow the user to add a new post
        viewFacade.navigateToAddPost(user);
        System.out.println("Navigating to Add Post view.");
    }
    
    @Override
    public void handleViewPosts() {
        // Display a list of the user's posts, with options to view, edit, or delete each post
        viewFacade.navigateToMyPosts(user);
        System.out.println("Navigating to View Posts view.");
    }
    
    @Override
    public void handleEditProfile() {
        // Switch to ProfileView to allow the user to edit their profile details
        viewFacade.navigateToEditUserProfile(user);
        System.out.println("Navigating to Edit Profile view.");
    }
    
    @Override
    public void handleLogout() {
        // Logout the user and return to the LoginView
    	viewFacade.showAlert(AlertType.INFORMATION, "Logout", user.getUsername() + " logged-out successfully!");
        viewFacade.navigateToLogin();
        System.out.println("User logged out: " + user.getUsername());
    }
    
    @Override
    public void handleUpgradeToVIP() {
    	// Offer the user the option to upgrade to VIP status            
        viewFacade.navigateToUpgradeToVIP(user);
        System.out.println("Navigating to Upgrade to VIP view.");
           
    }
    
    @Override
    public void handleDegrade() throws UserException {
		try {
			if (viewFacade.degradeToStandard(user)) {
				viewFacade.showAlert(AlertType.INFORMATION, "Message", "Successfully cancelled VIP subscription.");
				user.setVIP(false);
				viewFacade.navigateToDashboard(user);
			} else {
				viewFacade.showAlert(AlertType.ERROR, "Error", "An error occurred during subscription cancel. Please try again.");
			}
            System.out.println("User cancelled VIP subscription: " + user.getUsername());
		} catch (Exception e) {
            System.err.println("Error while handling degrade operation: " + e.getMessage());
            viewFacade.showAlert(AlertType.ERROR, "Error", "An unexpected error occurred while downgrading: " + e.getMessage());
        }
	}
    
    @Override
    public void handleVisualization() {
    	viewFacade.navigateToVisualization(user);
        System.out.println("Navigating to Posts Visualization view.");
	}
    
    @Override
    public void handleImports() {
    	viewFacade.navigateToBulkImports(user);
        System.out.println("Navigating to Bulk Imports view.");
    }
}