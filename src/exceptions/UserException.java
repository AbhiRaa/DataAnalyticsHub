package exceptions;

import java.sql.SQLException;

/**
 * The UserException class represents a specific type of exception that 
 * arises when there is an issue related to user operations in the application.
 * <p>
 * This exception wraps generic exceptions into a custom exception class to provide
 * better context and logging capabilities for user-related operations. By using this
 * custom exception, the application can provide specific error messages and handle
 * user-related errors in a more structured manner.
 * </p>
 */
public class UserException  extends DataAnalyticsHubException  {
	
	private static final long serialVersionUID = 1L;
	
	/**
     * Constructs a new UserException with the specified detail message and
     * the root exception.
     * 
     * @param message The detailed message explaining the nature of the user error.
     *                The detail message is saved for later retrieval by the 
     *                {@link #getMessage()} method.
     * @param e       The root Exception that caused this custom exception. 
     *                The cause is saved for later retrieval by the
     *                {@link #getCause()} method.
     */
	public UserException(String message, SQLException e) {
        super(message);
    }
}
