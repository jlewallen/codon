package com.page5of4.codon.examples.messages;

import java.io.Serializable;
import java.util.Date;
import java.util.UUID;

public class ProjectCompletedMessage implements Serializable {
   private static final long serialVersionUID = 1L;

   private UUID id;
   private Date completedAt;

   public UUID getId() {
      return id;
   }

   public void setId(UUID id) {
      this.id = id;
   }

   public Date getCompletedAt() {
      return completedAt;
   }

   public void setCompletedAt(Date completedAt) {
      this.completedAt = completedAt;
   }

   protected ProjectCompletedMessage() {
      super();
   }

   public ProjectCompletedMessage(UUID id, Date completedAt) {
      super();
      this.id = id;
      this.completedAt = completedAt;
   }
}
