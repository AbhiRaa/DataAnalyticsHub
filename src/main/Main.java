package main;

import controllers.PostController;
import controllers.UserController;
import database.DBManager;
import javafx.application.Application;
import javafx.stage.Stage;
import views.EntryView;

public class Main extends Application {
	
	private DBManager dbManager;

    @Override
    public void start(Stage primaryStage) throws Exception {

    	dbManager = new DBManager(); // instantiate it once
    	UserController userController = new UserController(dbManager);
    	PostController postController = new PostController(dbManager);
         
        // Launch the LoginView
        new EntryView(primaryStage, userController, postController);
    }
    
    @Override
    public void stop() {
        // This method is called when the application is exiting.
        if (dbManager != null) {
            dbManager.close();
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}