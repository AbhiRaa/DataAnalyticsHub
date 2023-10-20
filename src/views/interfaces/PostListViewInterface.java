package views.interfaces;

import exceptions.PostException;
import models.Post;

//Interface for PostListView
public interface PostListViewInterface {
	
	void handleBack();
    
    void handleRetrievePostById() throws PostException;

    void handleEditPost(Post selectedPost);

    void handleDeletePost(Post selectedPost) throws PostException;
    
    void handleTopPosts() throws PostException;

    void handleExportPost(Post post);
}
