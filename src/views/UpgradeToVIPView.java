package views;

import controllers.PostController;
import controllers.UserController;
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
import views.interfaces.UpgradeToVIPViewInterface;

public class UpgradeToVIPView extends BaseView implements UpgradeToVIPViewInterface {

    private Stage stage;
    private User user;
    private Button upgradeButton, backButton;
    private VBox layout;
    
    private GUIViewFacade viewFacade;

    public UpgradeToVIPView(Stage stage, User user, UserController userController, PostController postController) {
        this.user = user;
        this.stage = stage;
        this.viewFacade = new GUIViewFacade(stage, userController, postController);
        
        initializeComponents();
        show();
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
        upgradeButton.setOnAction(e -> handleUpgrade());

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
    }
    
    @Override
    public void handleBack() {
    	viewFacade.navigateToDashboard(user);
	}
    
    @Override
	public void handleUpgrade() {
        if (viewFacade.upgradeToVIP(user)) {
        	viewFacade.showAlert(AlertType.INFORMATION, "Success", "Successfully upgraded to VIP. Please log out and log in again to access VIP functionalities.");
        	viewFacade.navigateToDashboard(user);
        } else {
        	viewFacade.showAlert(AlertType.ERROR, "Error", "An error occurred during the upgrade. Please try again.");
        }
    }
}