package services;

import models.User;

public interface UserService {
	
	boolean registerUser(String username, String password, String firstName, String lastName);

    User loginUser(String username, String password);

    boolean updateUserProfile(User user);

    boolean updateUserPassword(User user, String newPassword);

    boolean usernameExists(String username);

    String getHashedPassword(int userId);

    boolean upgradeToVIP(User user);

    boolean degradeToStandard(User user);
}
