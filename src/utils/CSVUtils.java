package utils;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import exceptions.CsvLoadingException;
import models.Post;
import models.User;

public class CSVUtils {
	
	/**
	    * Reads posts from a given CSV file and returns a list of Post objects.
	    *
	    * @param csvFile The path to the CSV file containing posts.
	    * @return A list of Post objects extracted from the CSV file.
	    * @throws CsvLoadingException if there's an issue with the CSV file format or content.
	    */
	    public static List<Post> readPosts(String csvFile, User currentUser) throws CsvLoadingException {
	        List<Post> posts = new ArrayList<>();
	        
	        // The try-with-resources statement ensures that the reader is automatically closed after use
	        // Reference : https://stackoverflow.com/questions/65033185/how-to-capture-the-field-values-in-the-csv-file-using-bufferedreader
	        try (BufferedReader reader = new BufferedReader(new FileReader(csvFile))) {
	            String line;
	            
	            // Skip the header row of the CSV file
	            reader.readLine();
	            while ((line = reader.readLine()) != null) {
	                String[] data = line.split(",");
	                
	                // Verify if the CSV row has the expected number of columns
	                if (data.length < 6) {
	                    throw new CsvLoadingException("Missing data in CSV file" + line, null);
	                }
	                
	                // Extract and validate data from the CSV row
	                //int id = checkInt(data[0], "ID");
	                String content = checkString(data[1], "content");
	                String author = checkString(data[2], "author");
	                int likes = checkInt(data[3], "likes");
	                int shares = checkInt(data[4], "shares");
	                String dateTime = checkString(data[5], "date-time");
	                
	                // If the author matches the current user's username, then add the post
	                if (author.equals(currentUser.getUsername())) {
	                    posts.add(new Post(content, author, likes, shares, dateTime, currentUser.getUserId()));
	                }
	                
	            }
	        } catch (IOException e) {
	            System.out.println("Error reading CSV file: " + e.getMessage());
	            // Propagate the exception to notify caller about the CSV reading error
	            throw new CsvLoadingException("Error reading CSV file", e);
	        }
	        
	        return posts;
	    }
	    
	    /**
	     * Checks if a given string value represents a valid integer. If not, throws an exception.
	     *
	     * @param value The string to check.
	     * @param fieldName The name of the CSV column (used for error messages).
	     * @return The integer representation of the given string.
	     * @throws CsvLoadingException if the value is empty or cannot be parsed as an integer.
	     */
	    private static int checkInt(String value, String fieldName) throws CsvLoadingException {
	        if (value.trim().isEmpty()) {
	            throw new CsvLoadingException("Missing integer value for field: " + fieldName, null);
	        }
	        return Integer.parseInt(value);
	    }
	    
	    /**
	     * Checks if a given string value is valid and non-empty.
	     *
	     * @param value The string to check.
	     * @param fieldName The name of the CSV column (used for error messages).
	     * @return The validated string value.
	     * @throws CsvLoadingException if the value is empty.
	     */
	    private static String checkString(String value, String fieldName) throws CsvLoadingException {
	        if (value.trim().isEmpty()) {
	            throw new CsvLoadingException("Missing string value for field: " + fieldName, null);
	        }
	        return value;
	    }

    private static final String CSV_DELIMITER = ",";

    public static List<String[]> readFromCSV(String filePath) throws IOException {
        List<String[]> records = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = br.readLine()) != null) {
                records.add(line.split(CSV_DELIMITER));
            }
        }
        return records;
    }

    public static void writeToCSV(String filePath, List<String[]> data) throws IOException {
        try (FileWriter writer = new FileWriter(filePath)) {
            for (String[] record : data) {
                writer.append(String.join(CSV_DELIMITER, record));
                writer.append("\n");
            }
        }
    }
}
