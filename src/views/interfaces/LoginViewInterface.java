package views.interfaces;

import exceptions.UserException;

//Interface for LoginView
public interface LoginViewInterface {
	
	void handleExit();

	void handleLogin() throws UserException;

	void handleSignup();
	   
	void handleTogglePasswordVisibility();
}
