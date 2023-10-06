package main;

import controllers.UserController;
import database.DBManager;
import javafx.application.Application;
import javafx.stage.Stage;
import views.LoginView;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {

    	DBManager manager = new DBManager();
    	UserController userController = new UserController(manager);
         
        // Launch the LoginView
        new LoginView(primaryStage, userController);
    }

    public static void main(String[] args) {
        launch(args);
    }
}