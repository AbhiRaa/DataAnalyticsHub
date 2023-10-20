package services.factory;

import services.PostService;
import services.UserService;

/**
 * This interface defines methods for creating service instances.
 * The factory pattern abstracts the instantiation of service objects.
 */
public interface ServiceFactory {
    
    /**
     * Creates an instance of UserService.
     * 
     * @return UserService instance.
     */
    UserService createUserService();
    
    /**
     * Creates an instance of PostService.
     * 
     * @return PostService instance.
     */
    PostService createPostService();
}
