package com.page5of4.codon.examples.application.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

import com.page5of4.codon.Bus;
import com.page5of4.codon.examples.application.OurApplicationService;
import com.page5of4.codon.examples.application.ProjectManagementService;
import com.page5of4.codon.examples.application.impl.OurApplicationServiceImpl;
import com.page5of4.codon.examples.application.impl.ProjectManagementServiceImpl;
import com.page5of4.codon.examples.application.model.ProjectEvents;
import com.page5of4.codon.examples.application.model.repositories.ProjectRepository;
import com.page5of4.codon.useful.DomainEventsConfig;

@Configuration
@Import(value = { DomainEventsConfig.class })
public class ApplicationConfig {
   @Autowired
   private Bus bus;

   @Bean
   public OurApplicationService ourApplicationService() {
      return new OurApplicationServiceImpl(bus);
   }

   @Bean
   public ProjectManagementService projectManagementService() {
      return new ProjectManagementServiceImpl(projectRepository(), bus);
   }

   @Bean
   public ProjectRepository projectRepository() {
      return new ProjectRepository();
   }

   @Bean
   public ProjectEvents projectEvents() {
      return new ProjectEvents(bus);
   }
}
