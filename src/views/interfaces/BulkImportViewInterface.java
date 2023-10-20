package views.interfaces;

import java.io.File;

import models.Post;

public interface BulkImportViewInterface {
	
	void previewImportedPosts(File selectedFile);
    
    String formatPostForDisplay(Post post);

    void handleSave();
}
