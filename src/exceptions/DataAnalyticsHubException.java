package exceptions;

/**
 * The DataAnalyticsHubException class represents a general exception for the
 * Data Analytics Hub application. It serves as a base exception class that can 
 * be extended by other specific exception classes. This ensures a standardized 
 * way of handling and reporting errors throughout the application.
 */
public class DataAnalyticsHubException extends Exception {
	
	private static final long serialVersionUID = 1L;
	
	/**
     * Constructs a new exception with the specified detail message.
     * 
     * @param message The detailed message. The detail message is saved for 
     *                later retrieval by the {@link #getMessage()} method.
     */
	public DataAnalyticsHubException(String message) {
        super(message);
    }
	
	/**
     * Constructs a new exception with the specified detail message and cause.
     * 
     * @param message The detailed message. The detail message is saved for 
     *                later retrieval by the {@link #getMessage()} method.
     * @param cause The cause (which is saved for later retrieval by the
     *              {@link #getCause()} method). A null value is permitted,
     *              and indicates that the cause is nonexistent or unknown.
     */
    public DataAnalyticsHubException(String message, Throwable cause) {
        super(message, cause);
    }
}
