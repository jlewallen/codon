package com.page5of4.codon.examples.messages;

import java.io.Serializable;
import java.util.Date;
import java.util.UUID;

public class ProjectDefinedMessage implements Serializable {
   private static final long serialVersionUID = 1L;

   private UUID id;
   private String title;
   private String owner;
   private Long hoursRequired;
   private Date definedAt;

   public UUID getId() {
      return id;
   }

   public void setId(UUID id) {
      this.id = id;
   }

   public String getTitle() {
      return title;
   }

   public void setTitle(String title) {
      this.title = title;
   }

   public String getOwner() {
      return owner;
   }

   public void setOwner(String owner) {
      this.owner = owner;
   }

   public Long getHoursRequired() {
      return hoursRequired;
   }

   public void setHoursRequired(Long hoursRequired) {
      this.hoursRequired = hoursRequired;
   }

   public Date getDefinedAt() {
      return definedAt;
   }

   public void setDefinedAt(Date definedAt) {
      this.definedAt = definedAt;
   }

   protected ProjectDefinedMessage() {
      super();
   }

   public ProjectDefinedMessage(UUID id, String title, String owner, Long hoursRequired, Date definedAt) {
      super();
      this.id = id;
      this.title = title;
      this.owner = owner;
      this.hoursRequired = hoursRequired;
      this.definedAt = definedAt;
   }
}
