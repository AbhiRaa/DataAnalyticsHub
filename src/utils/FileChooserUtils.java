package utils;

import java.io.File;

import javafx.stage.FileChooser;
import javafx.stage.Stage;

public class FileChooserUtils {
	
	public static File showSaveCSVFileDialog(Stage stage) {
		
        FileChooser fileChooser = new FileChooser();
        
        // Set extension filter
        FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("CSV files (*.csv)", "*.csv");
        fileChooser.getExtensionFilters().add(extFilter);
        
        return fileChooser.showSaveDialog(stage);
    }
	
	public static File showOpenCSVFileDialog(Stage stage) {
		
        FileChooser fileChooser = new FileChooser();
        
        // Set extension filter
        FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("CSV files (*.csv)", "*.csv");
        fileChooser.getExtensionFilters().add(extFilter);
        
        return fileChooser.showOpenDialog(stage);
    }
}
