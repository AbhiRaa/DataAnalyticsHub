package controllers;

import database.DBManager;
import models.User;
import services.UserService;
import services.factory.ServiceFactory;
import services.factory.ServiceFactoryImpl;

public class UserController {
	
	// Dependency Injection
	private final UserService userService;

    public UserController(DBManager dbManager) {
    	ServiceFactory serviceFactory = new ServiceFactoryImpl(dbManager);
        this.userService = serviceFactory.createUserService();
    }

    // Register a new user
    public boolean registerUser(String username, String password, String firstName, String lastName) {
        return userService.registerUser(username, password, firstName, lastName);
    }

    // Authenticate user login
    public User loginUser(String username, String password) {
    	return userService.loginUser(username, password);
    }

    // Update user profile
    public boolean updateUserProfile(User user) {
    	return userService.updateUserProfile(user);
    }

    public boolean updateUserPassword(User user, String newPassword) {
    	return userService.updateUserPassword(user, newPassword);
    }
    
    public boolean usernameExists(String username) {
    	return userService.usernameExists(username);
    }
    
    public String getHashedPassword(int userId) {
    	return userService.getHashedPassword(userId);
    }
    
    // Upgrade to VIP status
    public boolean upgradeToVIP(User user) {
    	return userService.upgradeToVIP(user);
    }
    
    // Degrade VIP status
    public boolean degradeToStandard(User user) {
    	return userService.degradeToStandard(user);
    }
}