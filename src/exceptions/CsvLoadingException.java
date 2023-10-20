package exceptions;

/**
 * The CsvLoadingException class represents a specific type of exception that
 * arises when there is an issue loading data from a CSV file.
 * <p>
 * This exception provides more context and specificity compared to the general
 * {@link DataAnalyticsHubException}, allowing for better error handling and
 * logging when dealing with CSV-related operations in the application.
 * </p>
 */
public class CsvLoadingException extends DataAnalyticsHubException {
    
	private static final long serialVersionUID = 1L;
	
	/**
     * Constructs a new CSV loading exception with the specified detail message
     * and cause.
     * 
     * @param message The detailed message explaining the nature of the CSV loading error.
     *                The detail message is saved for later retrieval by the 
     *                {@link #getMessage()} method.
     * @param cause   The cause (which is saved for later retrieval by the
     *                {@link #getCause()} method). A null value is permitted,
     *                and indicates that the cause is nonexistent or unknown.
     */
	public CsvLoadingException(String message, Throwable cause) {
        super(message, cause);
    }
}
