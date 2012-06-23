package com.page5of4.codon.examples.application;

import java.util.Date;
import java.util.UUID;

public class ProjectDto {
   private final UUID id;
   private final String title;
   private final String owner;
   private final Long hoursRequired;
   private final Date definedAt;

   public UUID getId() {
      return id;
   }

   public String getTitle() {
      return title;
   }

   public String getOwner() {
      return owner;
   }

   public Long getHoursRequired() {
      return hoursRequired;
   }

   public Date getDefinedAt() {
      return definedAt;
   }

   public ProjectDto(UUID id, String title, String owner, Long hoursRequired, Date definedAt) {
      super();
      this.id = id;
      this.title = title;
      this.owner = owner;
      this.hoursRequired = hoursRequired;
      this.definedAt = definedAt;
   }
}
