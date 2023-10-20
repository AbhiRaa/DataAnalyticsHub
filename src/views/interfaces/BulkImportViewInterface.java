package views.interfaces;

import java.io.File;

import exceptions.PostException;
import models.Post;

// Interface for BulkImportView
public interface BulkImportViewInterface {
	
	void previewImportedPosts(File selectedFile);
    
    String formatPostForDisplay(Post post);

    void handleSave() throws PostException;
}
