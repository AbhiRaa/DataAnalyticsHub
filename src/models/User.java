package models;

/**
 * User represents a user entity in the system. It captures details about the user such as
 * username, password (hashed), first name, last name, and VIP status.
 * The User class extends the Auditable class, inheriting its auditing fields.
 */
public class User extends Auditable {

	private int userId;
	private String username;
    private String hashedPassword;  // Store the hashed password, not the original.
    private String salt;  // Salt for the password hashing.
    private String firstName;
    private String lastName;
    private boolean isVIP;
    
    /**
     * Constructs a User object with all attributes.
     */
    public User(int userId, String username, String hashedPassword, String salt, String firstName, String lastName, boolean isVIP) {
        this.userId = userId;
    	this.username = username;
        this.salt = salt;
        this.hashedPassword = hashedPassword;
        this.firstName = firstName;
        this.lastName = lastName;
        this.isVIP = isVIP;
    }
    
    /**
     * Constructs a User object without an userId. Useful for creating new users where the ID is auto-generated.
     */
    public User(String username, String hashedPassword, String salt, String firstName, String lastName, boolean isVIP) {
    	this.username = username;
        this.salt = salt;
        this.hashedPassword = hashedPassword;
        this.firstName = firstName;
        this.lastName = lastName;
        this.isVIP = isVIP;
    }
    
    // Standard getters and setters
    /**
	 * @return the userId
	 */
	public int getUserId() {
		return userId;
	}
	
	/**
	 * @param userId the userId to set
	 */
	public void setUserId(int userId) {
		this.userId = userId;
	}
    
    /**
	 * @return the username
	 */
	public String getUsername() {
		return username;
	}

	/**
	 * @param username the username to set
	 */
	public void setUsername(String username) {
		this.username = username;
	}

	/**
	 * @return the hashedPassword
	 */
	public String getHashedPassword() {
		return hashedPassword;
	}

	/**
	 * @param hashedPassword the hashedPassword to set
	 */
	public void setHashedPassword(String hashedPassword) {
		this.hashedPassword = hashedPassword;
	}

	/**
	 * @return the salt
	 */
	public String getSalt() {
		return salt;
	}

	/**
	 * @param salt the salt to set
	 */
	public void setSalt(String salt) {
		this.salt = salt;
	}

	/**
	 * @return the firstName
	 */
	public String getFirstName() {
		return firstName;
	}

	/**
	 * @param firstName the firstName to set
	 */
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	/**
	 * @return the lastName
	 */
	public String getLastName() {
		return lastName;
	}

	/**
	 * @param lastName the lastName to set
	 */
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	/**
	 * @return the isVIP
	 */
	public boolean isVIP() {
		return isVIP;
	}

	/**
	 * @param isVIP the isVIP to set
	 */
	public void setVIP(boolean isVIP) {
		this.isVIP = isVIP;
	}
	
	@Override
	public String toString() {
		return "User [username=" + username + ", hashedPassword=" + hashedPassword + ", salt=" + salt + ", firstName="
				+ firstName + ", lastName=" + lastName + ", isVIP=" + isVIP + "]";
	}
}
