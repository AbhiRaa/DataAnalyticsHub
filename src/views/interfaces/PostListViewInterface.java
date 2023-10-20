package views.interfaces;

import models.Post;

public interface PostListViewInterface {
	
	void handleBack();
    
    void handleRetrievePostById();

    void handleEditPost(Post selectedPost);

    void handleDeletePost(Post selectedPost);
    
    void handleTopPosts();

    void handleExportPost(Post post);
}
