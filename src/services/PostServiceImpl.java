package services;

import java.sql.SQLException;
import java.util.List;

import database.DBManager;
import database.PostDAO;
import models.Post;
import models.User;

public class PostServiceImpl implements PostService {
	
	private PostDAO postDAO;

    public PostServiceImpl(DBManager dbManager) {
        this.postDAO = new PostDAO(dbManager);
    }

    @Override
    public boolean addPost(Post post) {
        try {
            postDAO.addPost(post);
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean deletePost(Post post) {
        try {
            postDAO.deletePost(post.getPostId());
            return true;
        } catch (SQLException e) {
            // Handle exception
    }
    return false;
}

	@Override
	public Post getPostByID(int postID) {
	    try {
	        return postDAO.getPostByID(postID);
	    } catch (SQLException e) {
	        // Handle exception
	    }
	    return null;
	}
	
	@Override
	public List<Post> getTopNPostsByShares(int n, User user) {
	    try {
	        if (user != null)
	        	return postDAO.getTopNPostsByShares(n, user);
	        else
	        	return postDAO.getTopNPostsByShares(n);
	    } catch (SQLException e) {
	        // Handle exception
	    }
	    return null;
	}
	
	@Override
	public List<Post> getTopNPostsByLikes(int n, User user) {
	    try {
	    	if (user != null)
	    		return postDAO.getTopNPostsByLikes(n, user);
	    	else 
	    		return postDAO.getTopNPostsByLikes(n);
	    } catch (SQLException e) {
	        // Handle exception
	    }
	    return null;
	}
	
	@Override
	public List<Post> getPostsByUser(User user) {
		try {
			return postDAO.getPostsByUser(user);
	    } catch (SQLException e) {
	        // Handle exception
	    }
	    return null;
	}
	
	@Override
	public boolean updatePost(Post post) {
	    try {
	        postDAO.updatePost(post);
	        return true;
	    } catch (SQLException e) {
	        e.printStackTrace();
	    }
	    return false;
	}
	
	@Override
	public List<Post> getAllPosts() {
		try {
			return postDAO.getAllPosts();
	    } catch (SQLException e) {
	        // Handle exception
	    }
	    return null;
	}
	
	@Override
	public boolean addBulkPosts(List<Post> posts) {
	    try {
	        for (Post post : posts) {
	            postDAO.addPost(post);
	        }
	        return true;
	    } catch (Exception e) {
	        System.err.println("Error adding bulk posts: " + e.getMessage());
	        return false;
	    }
	}
}
