package views.interfaces;

import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

//Interface for SignupView
public interface SignupViewInterface {
	
	TextField createStyledTextField(String promptText);
	
	TextField createStyledTextField(String promptText, PasswordField passwordField);
	
	void handleSignup();
	
	void handleBack();
}
