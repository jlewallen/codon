package com.page5of4.codon.examples.application.model.repositories;

import java.util.UUID;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import com.page5of4.codon.examples.application.model.Project;

public class JpaProjectRepository implements ProjectRepository {
   @PersistenceContext
   private EntityManager entityManager;

   @Override
   public Project get(UUID key) {
      return entityManager.find(Project.class, key);
   }

   @Override
   public void delete(UUID key) {
      entityManager.remove(entityManager.find(Project.class, key));
   }

   @Override
   public void add(UUID key, Project project) {
      entityManager.persist(project);
   }
}
