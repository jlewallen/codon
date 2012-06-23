package com.page5of4.codon.examples.messages;

import java.io.Serializable;
import java.util.Date;
import java.util.UUID;

public class ProjectStartedMessage implements Serializable {
   private static final long serialVersionUID = 1L;

   private UUID id;
   private Date startedAt;

   public UUID getId() {
      return id;
   }

   public void setId(UUID id) {
      this.id = id;
   }

   public Date getStartedAt() {
      return startedAt;
   }

   public void setStartedAt(Date startedAt) {
      this.startedAt = startedAt;
   }

   protected ProjectStartedMessage() {
      super();
   }

   public ProjectStartedMessage(UUID id, Date startedAt) {
      super();
      this.id = id;
      this.startedAt = startedAt;
   }
}
