package com.page5of4.codon.examples.application.model;

import java.util.Date;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.common.eventbus.Subscribe;
import com.page5of4.codon.Bus;
import com.page5of4.codon.examples.messages.ProjectAbandonedMessage;
import com.page5of4.codon.examples.messages.ProjectCompletedMessage;
import com.page5of4.codon.examples.messages.ProjectDefinedMessage;
import com.page5of4.codon.examples.messages.ProjectStartedMessage;

@Service
public class ProjectEvents {
   private final Bus bus;

   @Autowired
   public ProjectEvents(Bus bus) {
      super();
      this.bus = bus;
   }

   @Subscribe
   public void defined(Defined event) {
      bus.publish(new ProjectDefinedMessage(event.getId(), event.getTitle(), event.getOwner(), event.getHoursRequired(), event.getDefinedAt()));
   }

   @Subscribe
   public void started(Started event) {
      bus.publish(new ProjectStartedMessage(event.getId(), event.getStartedAt()));
   }

   @Subscribe
   public void abandoned(Abandoned event) {
      bus.publish(new ProjectAbandonedMessage(event.getId(), event.getAbandonedAt(), event.getReason()));
   }

   @Subscribe
   public void completed(Completed event) {
      bus.publish(new ProjectCompletedMessage(event.getId(), event.getCompletedAt()));
   }

   public static class Defined {
      private final Project project;
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

      public Project getProject() {
         return project;
      }

      public Defined(Project project, UUID id, String title, String owner, Long hoursRequired, Date definedAt) {
         super();
         this.project = project;
         this.id = id;
         this.title = title;
         this.owner = owner;
         this.hoursRequired = hoursRequired;
         this.definedAt = definedAt;
      }
   }

   public static class Started {
      private final Project project;
      private final UUID id;
      private final Date startedAt;

      public Project getProject() {
         return project;
      }

      public UUID getId() {
         return id;
      }

      public Date getStartedAt() {
         return startedAt;
      }

      public Started(Project project, UUID id, Date startedAt) {
         super();
         this.project = project;
         this.id = id;
         this.startedAt = startedAt;
      }
   }

   public static class Abandoned {
      private final Project project;
      private final UUID id;
      private final Date abandonedAt;
      private final String reason;

      public Project getProject() {
         return project;
      }

      public UUID getId() {
         return id;
      }

      public Date getAbandonedAt() {
         return abandonedAt;
      }

      public String getReason() {
         return reason;
      }

      public Abandoned(Project project, UUID id, Date abandonedAt, String reason) {
         super();
         this.project = project;
         this.id = id;
         this.abandonedAt = abandonedAt;
         this.reason = reason;
      }
   }

   public static class Completed {
      private final Project project;
      private final UUID id;
      private final Date completedAt;

      public Project getProject() {
         return project;
      }

      public UUID getId() {
         return id;
      }

      public Date getCompletedAt() {
         return completedAt;
      }

      public Completed(Project project, UUID id, Date completedAt) {
         super();
         this.project = project;
         this.id = id;
         this.completedAt = completedAt;
      }
   }
}
