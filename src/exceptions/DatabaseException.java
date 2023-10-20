package exceptions;

import java.sql.SQLException;

/**
 * The DatabaseException class represents a specific type of exception that 
 * arises when there is an issue interacting with the database.
 * <p>
 * This exception wraps SQL exceptions into a custom exception class to provide
 * better context and logging capabilities for database-related operations in
 * the application. By using this custom exception, the application can decouple
 * itself from SQL-specific error handling, making the codebase cleaner and more
 * maintainable.
 * </p>
 */
public class DatabaseException extends DataAnalyticsHubException {
	
	private static final long serialVersionUID = 1L;
	
	/**
     * Constructs a new DatabaseException with the specified detail message and
     * the root SQL exception.
     * 
     * @param message The detailed message explaining the nature of the database error.
     *                The detail message is saved for later retrieval by the 
     *                {@link #getMessage()} method.
     * @param e       The root SQLException that caused this custom exception. 
     *                The cause is saved for later retrieval by the
     *                {@link #getCause()} method.
     */
	public DatabaseException(String message, SQLException e) {
        super(message);
    }
}
