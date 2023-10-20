
package models;

import java.time.LocalDateTime;

/**
 * Auditable is a base class that provides fields and behaviors for 
 * auditing purposes. Entities that extend this class automatically 
 * have created and updated timestamps.
 */
public class Auditable {
	
	/**
     * Default constructor initializes the created and updated timestamps to the current time.
     */
    public Auditable() {
        this.createdDate = LocalDateTime.now();
        this.updatedOn = this.createdDate;
    }
    
    /**
     * Constructs an Auditable object with provided created and updated timestamps.
     */
    public Auditable(LocalDateTime createdDate, LocalDateTime updatedOn) {
        this.createdDate = createdDate;
        this.updatedOn = updatedOn;
    }
   
    private LocalDateTime createdDate;

    private LocalDateTime updatedOn;


    /**
     * Gets the timestamp when the entity was created.
     */
    public LocalDateTime getCreatedDate() {
        return createdDate;
    }

    /**
     * Sets the timestamp when the entity was created.
     */
    public void setCreatedDate(LocalDateTime createdDate) {
        this.createdDate = createdDate;
    }

    /**
     * Gets the timestamp when the entity was last updated.
     */
    public LocalDateTime getUpdatedOn() {
        return updatedOn;
    }

    /**
     * Sets the timestamp when the entity was last updated.
     */
    public void setUpdatedOn(LocalDateTime updatedOn) {
        this.updatedOn = updatedOn;
    }
}

