package services.factory;

import database.DBManager;
import services.PostService;
import services.PostServiceImpl;
import services.UserService;
import services.UserServiceImpl;

// A concrete implementation of the ServiceFactory interface that contains the logic to decide which service instance to create.
public class ServiceFactoryImpl implements ServiceFactory {
	
	 private final DBManager dbManager;

	    public ServiceFactoryImpl(DBManager dbManager) {
	        this.dbManager = dbManager;
	    }

	    @Override
	    public UserService createUserService() {
	        return new UserServiceImpl(dbManager);
	    }

	    @Override
	    public PostService createPostService() {
	        return new PostServiceImpl(dbManager);
	    }
}
