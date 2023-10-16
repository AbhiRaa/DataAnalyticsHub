
package models;

import java.time.LocalDateTime;

public class Auditable {

    public Auditable() {
        this.createdDate = LocalDateTime.now();
        this.updatedOn = this.createdDate;
    }

    public Auditable(LocalDateTime createdDate, LocalDateTime updatedOn) {
        this.createdDate = createdDate;
        this.updatedOn = updatedOn;
    }
   
    private LocalDateTime createdDate;

    private LocalDateTime updatedOn;


    public LocalDateTime getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(LocalDateTime createdDate) {
        this.createdDate = createdDate;
    }

    public LocalDateTime getUpdatedOn() {
        return updatedOn;
    }

    public void setUpdatedOn(LocalDateTime updatedOn) {
        this.updatedOn = updatedOn;
    }
}

