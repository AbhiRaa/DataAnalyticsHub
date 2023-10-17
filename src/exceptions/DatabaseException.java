package exceptions;

public class DatabaseException extends DataAnalyticsHubException {
	
	private static final long serialVersionUID = 1L;

	public DatabaseException(String message) {
        super(message);
    }
}
