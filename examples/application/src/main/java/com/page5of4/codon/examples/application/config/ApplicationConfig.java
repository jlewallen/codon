package com.page5of4.codon.examples.application.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

import com.page5of4.codon.Bus;
import com.page5of4.codon.examples.application.ProjectManagementService;
import com.page5of4.codon.examples.application.domain.ProjectEvents;
import com.page5of4.codon.examples.application.domain.repositories.DefaultProjectRepository;
import com.page5of4.codon.examples.application.domain.repositories.ProjectRepository;
import com.page5of4.codon.examples.application.impl.ProjectManagementServiceImpl;
import com.page5of4.codon.useful.DomainEventsConfig;
import com.page5of4.codon.useful.repositories.RepositoryFactory;

@Configuration
@ExcludeFromScan
@Import(value = { DomainEventsConfig.class })
public class ApplicationConfig {
   @Autowired
   private Bus bus;
   @Autowired
   private RepositoryFactory repositoryFactory;

   @Bean
   public ProjectRepository projectRepository() {
      return new DefaultProjectRepository(repositoryFactory);
   }

   @Bean
   public ProjectManagementService projectManagementService() {
      return new ProjectManagementServiceImpl(projectRepository(), bus);
   }

   @Bean
   public ProjectEvents projectEvents() {
      return new ProjectEvents(bus);
   }
}
