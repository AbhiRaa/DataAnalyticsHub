package views;

import controllers.PostController;
import controllers.UserController;
import exceptions.UserException;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import models.User;
import views.facade.GUIViewFacade;
import views.facade.GUIViewFacadeInterface;
import views.interfaces.UpgradeToVIPViewInterface;

/**
 * The UpgradeToVIPView class provides a graphical interface for users to upgrade their account status to VIP.
 * It displays a promotional label about the VIP subscription, an upgrade button to handle the upgrade, 
 * and a back button to navigate back to the dashboard.
 */
public class UpgradeToVIPView extends BaseView implements UpgradeToVIPViewInterface {

    private Stage stage;
    private User user;
    private Button upgradeButton, backButton;
    private VBox layout;
    
    private GUIViewFacadeInterface viewFacade;
    
    /**
     * Constructs the UpgradeToVIPView.
     * 
     * @param stage The primary stage for this view.
     * @param user The current logged in user.
     * @param userController The controller for user-related operations.
     * @param postController The controller for post-related operations.
     */
    public UpgradeToVIPView(Stage stage, User user, UserController userController, PostController postController) {
        this.user = user;
        this.stage = stage;
        this.viewFacade = new GUIViewFacade(stage, userController, postController);
        
        initializeComponents();
        show();
        System.out.println("UpgradeToVIPView initialized.");
    }
    
    @Override
    protected void initializeComponents() {
    	
    	ImageView logoView = new ImageView(new Image("/image/jewelry.png"));
        logoView.setFitWidth(100);
        logoView.setPreserveRatio(true);
        
        Label vipLabel = new Label("Would you like to subscribe to the application for a monthly fee of $0?");
        vipLabel.setTextFill(Color.GREEN);
        vipLabel.setFont(Font.font("Arial", 15));
        
        upgradeButton = new Button("Upgrade to VIP");
        upgradeButton.setOnAction(e -> {
			try {
				handleUpgrade();
			} catch (UserException e1) {
				e1.printStackTrace();
			}
		});

        backButton = new Button("Back");
        backButton.setOnAction(e -> handleBack());

        layout = new VBox(10);
        layout.getChildren().addAll(logoView, vipLabel, upgradeButton, backButton);
        layout.setAlignment(Pos.CENTER);
        layout.setPadding(new Insets(20, 20, 30, 20));
        
    }
    
    @Override
    protected void show() {
    	stage.setScene(new Scene(layout, 600, 300));
        stage.setTitle("Upgrade to VIP");
        stage.show();
        System.out.println("UpgradeToVIPView displayed.");
    }
    
    @Override
    public void handleBack() {
    	viewFacade.navigateToDashboard(user);
    	System.out.println("Navigating back to dashboard.");
	}
    
    @Override
	public void handleUpgrade() throws UserException {
    	System.out.println("Upgrade process started for user: " + user.getUsername());
        upgradeButton.setDisable(true); // Disable the button to prevent multiple clicks
        upgradeButton.setText("Upgrading...");
        
        if (viewFacade.upgradeToVIP(user)) {
        	System.out.println("Successfully upgraded user: " + user.getUsername());
        	viewFacade.showAlert(AlertType.INFORMATION, "Success", "Successfully upgraded to VIP. Please log out and log in again to access VIP functionalities.");
        	viewFacade.navigateToDashboard(user);
        } else {
        	System.err.println("Error during the upgrade for user: " + user.getUsername());
        	viewFacade.showAlert(AlertType.ERROR, "Error", "An error occurred during the upgrade. Please try again.");
        }
        
        // Resetting the button state
        upgradeButton.setDisable(false);
        upgradeButton.setText("Upgrade to VIP");
    }
}