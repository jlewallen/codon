package com.page5of4.codon.examples.application.model.repositories;

import java.util.UUID;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Service;

import com.page5of4.codon.examples.application.model.Project;

@Service
public class ProjectRepository {
   @PersistenceContext
   private EntityManager entityManager;

   public Project findById(UUID id) {
      return null;
   }

   public void add(Project project) {

   }
}
