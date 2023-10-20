package views.interfaces;

import exceptions.UserException;

//Interface for DashboardView
public interface DashboardViewInterface {
	
	void handleAddPost();

    void handleViewPosts();

    void handleEditProfile();

    void handleLogout();

    void handleUpgradeToVIP();
    
    void handleDegrade() throws UserException;
    
    void handleVisualization();
    
    void handleImports();

}
