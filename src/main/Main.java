package main;

import controllers.PostController;
import controllers.UserController;
import database.DBManager;
import javafx.application.Application;
import javafx.stage.Stage;
import views.EntryView;

/**
 * Main class responsible for initiating the application.
 * It sets up the database connection, initializes controllers,
 * and launches the main view for the user.
 */
public class Main extends Application {
	
	private DBManager dbManager;
	
	 /**
     * The start method initializes the application.
     * It sets up the database connection, creates instances of the controllers,
     * and then launches the main user view.
     * 
     * @param primaryStage the main stage/window of the application.
     */
    @Override
    public void start(Stage primaryStage) throws Exception {
    	try {
    		System.out.println("Starting application...");
        	
        	// Ensure only one DBManager instance throughout the app's lifetime.
        	dbManager = DBManager.getInstance();
        	UserController userController = new UserController(dbManager);
        	PostController postController = new PostController(dbManager);
             
        	// Launch the EntryView
            new EntryView(primaryStage, userController, postController);
            
            System.out.println("Application started successfully.");
    		
    	} catch (Exception e) {
            System.err.println("Error starting application: " + e.getMessage());
            e.printStackTrace();
        }
    	
    }
    
    /**
     * This method is executed when the application is exiting.
     * It ensures that resources, like the database connection, are properly closed.
     */
    @Override
    public void stop() {
    	System.out.println("Closing application...");
    	
        if (dbManager != null) {
            dbManager.close();
        }
        System.out.println("Application closed.");
    }
    
    /**
     * The main entry point of the application.
     * 
     * @param args command line arguments.
     */
    public static void main(String[] args) {
    	System.out.println("Launching application...");
        launch(args);
    }
}