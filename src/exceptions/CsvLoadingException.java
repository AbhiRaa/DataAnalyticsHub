package exceptions;

public class CsvLoadingException extends DataAnalyticsHubException {
    
	private static final long serialVersionUID = 1L;

	public CsvLoadingException(String message, Throwable cause) {
        super(message, cause);
    }
}
