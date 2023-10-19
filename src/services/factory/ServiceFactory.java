package services.factory;

import services.PostService;
import services.UserService;

// An interface that outlines the methods for creating service instances.
public interface ServiceFactory {
	
	UserService createUserService();
	
    PostService createPostService();
}
