package exceptions;

/**
 * The PostException class represents a specific type of exception that 
 * arises when there is an issue related to post operations in the application.
 * <p>
 * This exception wraps generic exceptions into a custom exception class to provide
 * better context and logging capabilities for post-related operations. By using this
 * custom exception, the application can provide specific error messages and handle
 * post-related errors in a more structured manner.
 * </p>
 */
public class PostException extends DataAnalyticsHubException  {
	
	private static final long serialVersionUID = 1L;
	
	/**
     * Constructs a new PostException with the specified detail message and
     * the root exception.
     * 
     * @param message The detailed message explaining the nature of the post error.
     *                The detail message is saved for later retrieval by the 
     *                {@link #getMessage()} method.
     * @param e       The root Exception that caused this custom exception. 
     *                The cause is saved for later retrieval by the
     *                {@link #getCause()} method.
     */
	public PostException(String message, Exception e) {
        super(message);
    }
}
