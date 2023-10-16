package models;


import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Represents a social media post.
 */
public class Post extends Auditable {
	
	// Unique identifier for the post
    private int postId;
    
    // Content/text of the post
    private String content;
    
    // Author or user who created the post
    private String author;
    
    // Count of likes received on the post
    private int likes;
    
    // Count of times the post was shared
    private int shares;
    
    // Date and time when the post was created or published
    private LocalDateTime dateTime;
    
    private int userId;

    // Static constant: DateTime formatter for converting the provided date-time string to a LocalDateTime object
    private static DateTimeFormatter formatter = DateTimeFormatter.ofPattern("d/MM/yyyy HH:mm");

    /**
     * Constructor for creating a new Post object.
     *
     * @param postId      Unique identifier for the post.
     * @param content     Text content of the post.
     * @param author      Author or user who created the post.
     * @param likes       Count of likes the post received.
     * @param shares      Count of times the post was shared.
     * @param dateTimeStr Date-time string representing when the post was created or published.
     */
    public Post(int postId, String content, String author, int likes, int shares, String dateTimeStr, int userId) {
        this.postId = postId;
        this.content = content;
        this.author = author;
        this.likes = likes;
        this.shares = shares;
        this.userId = userId;
        // Convert the provided date-time string to a LocalDateTime object using the formatter
      // this.dateTime = LocalDateTime.parse(dateTimeStr, formatter);
     // Check if the string is in ISO format
        if (dateTimeStr.contains("T")) {
            this.dateTime = LocalDateTime.parse(dateTimeStr, DateTimeFormatter.ISO_LOCAL_DATE_TIME);
        } else {
            this.dateTime = LocalDateTime.parse(dateTimeStr, formatter);
        }
    }
    
    // Constructor without PostID
    public Post(String content, String author, int likes, int shares, String dateTimeStr, int userId) {
        this.content = content;
        this.author = author;
        this.likes = likes;
        this.shares = shares;
        //this.dateTime = LocalDateTime.parse(dateTime, formatter);
        this.userId = userId;
     // Check if the string is in ISO format
        if (dateTimeStr.contains("T")) {
            this.dateTime = LocalDateTime.parse(dateTimeStr, DateTimeFormatter.ISO_LOCAL_DATE_TIME);
        } else {
            this.dateTime = LocalDateTime.parse(dateTimeStr, formatter);
        }
    }

    // Getter methods to access the private attributes of the Post class:
    public int getPostId() { return postId; }
    public String getContent() { return content; }
    public String getAuthor() { return author; }
    public int getLikes() { return likes; }
    public int getShares() { return shares; }
    public int getUserId() { return userId; }
    public LocalDateTime getDateTime() { return dateTime; }

    // Setter methods to modify the private attributes of the Post class:
    public void setPostId(int postId) { this.postId = postId; }
    public void setContent(String content) { this.content = content; }
    public void setAuthor(String author) { this.author = author; }
    public void setLikes(int likes) { this.likes = likes; }
    public void setShares(int shares) { this.shares = shares; }
    public void setUserId(int userId) { this.userId = userId; }
    public void setDateTime(String dateTimeStr) { this.dateTime = LocalDateTime.parse(dateTimeStr, formatter); }

    /**
     * Provides a custom string representation of the Post object.
     * Useful for printing post details in a readable format.
     *
     * @return A formatted string representing the post details.
     */
    @Override
    public String toString() {
        return String.format("%d | %s | %d | %d | %s", postId, content, likes, shares, dateTime.format(formatter));
    }
}

