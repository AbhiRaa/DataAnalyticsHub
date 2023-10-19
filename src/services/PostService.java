package services;

import java.util.List;

import models.Post;
import models.User;

public interface PostService {
	
	boolean addPost(Post post);
	
	boolean deletePost(Post post);
	
	Post getPostByID(int postID);
	
	List<Post> getTopNPostsByShares(int n, User user);
	
	List<Post> getTopNPostsByLikes(int n, User user);
	
	List<Post> getPostsByUser(User user);
	
	boolean updatePost(Post post);
	
	List<Post> getAllPosts();
	
	boolean addBulkPosts(List<Post> posts);
}
