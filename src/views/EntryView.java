package views;

import controllers.PostController;
import controllers.UserController;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.util.Duration;

public class EntryView {

	private Stage stage;
	private PostController postController;
	private UserController userController;

    public EntryView(Stage stage, UserController userController, PostController postController) {
        this.stage = stage;
        this.userController = userController;
        this.postController = postController;
        initializeComponents();
    }

    private void initializeComponents() {
        Label welcomeLabel = new Label("Welcome to Data Analytics Hub");
        welcomeLabel.setFont(new Font("Arial", 24));  // You can adjust the font as you like
        welcomeLabel.setStyle("-fx-text-fill: #2E8B57;");  // Color for the label

        ProgressBar loadingBar = new ProgressBar(0);
        loadingBar.setPrefWidth(300);
        loadingBar.setStyle("-fx-accent: #2E8B57;");  // Color for the loading bar

        VBox vbox = new VBox(10, welcomeLabel, loadingBar);
        vbox.setAlignment(Pos.CENTER);  // Center the VBox contents

        BorderPane root = new BorderPane();
        root.setCenter(vbox);  // Center the VBox in the BorderPane
        root.setStyle("-fx-background-color: #F5F5F5;");  // Background color for the view
        
        Scene scene = new Scene(root, 400, 300);
        stage.setScene(scene);
        stage.setTitle("Welcome");
        stage.show();

        // Animation for the loading bar
        Timeline timeline = new Timeline(
            new KeyFrame(Duration.seconds(2), new KeyValue(loadingBar.progressProperty(), 1))
        );

        // After animation completes, navigate to the login page
        timeline.setOnFinished(e -> {
            new LoginView(stage, userController, postController);
        });

        timeline.play();
    }
}
