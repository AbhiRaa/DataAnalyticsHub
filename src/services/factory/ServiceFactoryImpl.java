package services.factory;

import database.DBManager;
import services.PostService;
import services.PostServiceImpl;
import services.UserService;
import services.UserServiceImpl;

/**
 * A concrete implementation of the ServiceFactory interface.
 * This class contains the logic to instantiate specific service objects.
 */
public class ServiceFactoryImpl implements ServiceFactory {
    
    private final DBManager dbManager;

    /**
     * Constructor for ServiceFactoryImpl.
     *
     * @param dbManager The database manager to be passed to services.
     */
    public ServiceFactoryImpl(DBManager dbManager) {
        this.dbManager = dbManager;
    }

    /**
     * Creates an instance of UserServiceImpl.
     * 
     * @return UserService instance.
     */
    @Override
    public UserService createUserService() {
        System.out.println("Creating UserService instance.");
        return new UserServiceImpl(dbManager);
    }

    /**
     * Creates an instance of PostServiceImpl.
     * 
     * @return PostService instance.
     */
    @Override
    public PostService createPostService() {
        System.out.println("Creating PostService instance.");
        return new PostServiceImpl(dbManager);
    }
}
