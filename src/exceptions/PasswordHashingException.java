package exceptions;

/**
 * Custom exception to handle errors related to password hashing.
 */
public class PasswordHashingException extends RuntimeException {

	private static final long serialVersionUID = 1L;
	
	public PasswordHashingException(String message, Throwable cause) {
        super(message, cause);
    }
}
