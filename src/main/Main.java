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
    	
    	/*
    	 * The whole point of the Singleton pattern is to ensure that there's only one instance of the class throughout the application's lifetime
    	 * and access this instance using the getInstance() method.
    	 */
    	dbManager = DBManager.getInstance();
    	UserController userController = new UserController(dbManager);
    	PostController postController = new PostController(dbManager);
         
        // Launch the EntryView Animation
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