package com.page5of4.codon.examples.messages;

import java.io.Serializable;
import java.util.Date;
import java.util.UUID;

public class ProjectAbandonedMessage implements Serializable {
   private static final long serialVersionUID = 1L;

   private UUID id;
   private Date abandonedAt;

   public UUID getId() {
      return id;
   }

   public void setId(UUID id) {
      this.id = id;
   }

   public Date getAbandonedAt() {
      return abandonedAt;
   }

   public void setAbandonedAt(Date abandonedAt) {
      this.abandonedAt = abandonedAt;
   }

   protected ProjectAbandonedMessage() {
      super();
   }

   public ProjectAbandonedMessage(UUID id, Date abandonedAt) {
      super();
      this.id = id;
      this.abandonedAt = abandonedAt;
   }
}
