package exceptions;

public class UserException  extends DataAnalyticsHubException  {
	
	private static final long serialVersionUID = 1L;

	public UserException(String message) {
        super(message);
    }
}
