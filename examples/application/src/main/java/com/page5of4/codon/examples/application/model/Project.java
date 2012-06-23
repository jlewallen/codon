package com.page5of4.codon.examples.application.model;

import java.util.Date;
import java.util.UUID;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import com.sun.istack.NotNull;

@Entity
public class Project {
   @Id
   @GeneratedValue(strategy = GenerationType.AUTO)
   private Long id;
   @NotNull
   private ProjectState state;
   @NotNull
   private final String title;
   @NotNull
   private final String owner;
   @NotNull
   private final Long hoursRequired;
   @NotNull
   private final Date definedAt;

   public Project(UUID id, String title, String owner, Long hoursRequired, Date definedAt) {
      super();
      // this.id = id;
      this.title = title;
      this.owner = owner;
      this.hoursRequired = hoursRequired;
      this.definedAt = definedAt;
   }

   public enum ProjectState {
      DEFINED,
      STARTED,
      ABANDONED,
      COMPLETED
   }

   public void start() {
      requireState(ProjectState.DEFINED);
      state = ProjectState.STARTED;
   }

   public void abandon() {
      requireState(ProjectState.DEFINED, ProjectState.STARTED);
      state = ProjectState.ABANDONED;
   }

   public void complete() {
      requireState(ProjectState.STARTED);
      state = ProjectState.COMPLETED;
   }

   private void requireState(ProjectState... required) {
      for(ProjectState r : required) {
         if(state == r) return;
      }
      throw new RuntimeException("Project should be on of: " + required);
   }
}
