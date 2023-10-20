package utils;

import java.io.File;

import javafx.stage.FileChooser;
import javafx.stage.Stage;

/**
 * Utility class for handling file operations in the JavaFX application.
 * <p>
 * This class provides static methods to show save and open dialogs specifically for CSV files.
 * </p>
 */
public class FileChooserUtils {
	
	/**
     * Shows a "Save File" dialog for the user to choose where to save a CSV file.
     *
     * @param stage The parent stage for the dialog.
     * @return The selected file or null if the user cancels the operation.
     */
	public static File showSaveCSVFileDialog(Stage stage) {
		System.out.println("Opening 'Save CSV File' dialog...");
        FileChooser fileChooser = new FileChooser();
        
        // Set extension filter
        FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("CSV files (*.csv)", "*.csv");
        fileChooser.getExtensionFilters().add(extFilter);
        
        File selectedFile = fileChooser.showSaveDialog(stage);
        
        if (selectedFile != null) {
            System.out.println("User chose to save to file: " + selectedFile.getAbsolutePath());
        } else {
            System.out.println("Save file operation was cancelled by the user.");
        }
        
        return selectedFile;
    }
	
	/**
     * Shows an "Open File" dialog for the user to choose a CSV file to open.
     *
     * @param stage The parent stage for the dialog.
     * @return The selected file or null if the user cancels the operation.
     */
	public static File showOpenCSVFileDialog(Stage stage) {
		System.out.println("Opening 'Open CSV File' dialog...");
        FileChooser fileChooser = new FileChooser();
        
        // Set extension filter
        FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("CSV files (*.csv)", "*.csv");
        fileChooser.getExtensionFilters().add(extFilter);
        
        File selectedFile = fileChooser.showOpenDialog(stage);
        
        if (selectedFile != null) {
            System.out.println("User chose to open file: " + selectedFile.getAbsolutePath());
        } else {
            System.out.println("Open file operation was cancelled by the user.");
        }
        
        return selectedFile;
    }
}
