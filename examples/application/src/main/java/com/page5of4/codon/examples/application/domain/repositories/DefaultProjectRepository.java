package com.page5of4.codon.examples.application.domain.repositories;

import java.util.UUID;

import com.page5of4.codon.examples.application.domain.Project;
import com.page5of4.codon.useful.repositories.GenericRepositoryProxy;
import com.page5of4.codon.useful.repositories.RepositoryFactory;

public class DefaultProjectRepository extends GenericRepositoryProxy<UUID, Project> implements ProjectRepository {
   public DefaultProjectRepository(RepositoryFactory repositoryFactory) {
      super(repositoryFactory, ProjectRepository.class);
   }
}
