package models;

public class User {

	private int userId;
	private String username;
    private String hashedPassword;  // We store the hashed password, not the original.
    private String salt;  // Salt for the password hashing.
    private String firstName;
    private String lastName;
    private boolean isVIP;

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
	 * @return the userId
	 */
	public int getUserId() {
		return userId;
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
