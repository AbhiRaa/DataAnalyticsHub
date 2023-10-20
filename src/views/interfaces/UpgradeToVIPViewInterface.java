package views.interfaces;

import exceptions.UserException;

//Interface for UpgradeToVIPView
public interface UpgradeToVIPViewInterface {
	
	void handleBack();
	
	void handleUpgrade() throws UserException;
}
