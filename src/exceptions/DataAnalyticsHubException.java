package exceptions;

//Main exception class for our application
public class DataAnalyticsHubException extends Exception {
	
	private static final long serialVersionUID = 1L;

	public DataAnalyticsHubException(String message) {
        super(message);
    }

    public DataAnalyticsHubException(String message, Throwable cause) {
        super(message, cause);
    }
}
