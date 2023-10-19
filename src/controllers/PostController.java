package controllers;

import java.util.List;

import database.DBManager;
import models.Post;
import models.User;
import services.PostService;
import services.factory.ServiceFactory;
import services.factory.ServiceFactoryImpl;

public class PostController {
	
	// Dependency Injection
	private final PostService postService;

    public PostController(DBManager dbManager) {
		 ServiceFactory serviceFactory = new ServiceFactoryImpl(dbManager);
		 this.postService = serviceFactory.createPostService();
    }

    // Add a new post
    public boolean addPost(Post post) {
    	return postService.addPost(post);
    }

    // Delete a post by ID
    public boolean deletePost(Post post) {
    	return postService.deletePost(post);
    }

    // Retrieve a post by its ID
    public Post getPostByID(int postID) {
    	return postService.getPostByID(postID);
    }
    
    // Retrieve the top N posts by shares
    public List<Post> getTopNPostsByShares(int n, User user) {
    	return postService.getTopNPostsByShares(n, user);
    }

    // Retrieve the top N posts by likes
    public List<Post> getTopNPostsByLikes(int n, User user) {
    	return postService.getTopNPostsByLikes(n, user);
    }
    
    public List<Post> getPostsByUser(User user) {
    	return postService.getPostsByUser(user);
    }

    // Update an existing post
    public boolean updatePost(Post post) {
    	return postService.updatePost(post);
    }
    
    public List<Post> getAllPosts() {
    	return postService.getAllPosts();
    }
    
    public boolean addBulkPosts(List<Post> posts) {
    	return postService.addBulkPosts(posts);
    }
}