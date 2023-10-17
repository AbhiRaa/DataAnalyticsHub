package exceptions;

public class PostException extends DataAnalyticsHubException  {
	
	private static final long serialVersionUID = 1L;

	public PostException(String message) {
        super(message);
    }
}
