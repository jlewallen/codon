package com.page5of4.codon.examples.application.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.google.common.eventbus.EventBus;
import com.page5of4.codon.Bus;
import com.page5of4.codon.examples.application.OurApplicationService;
import com.page5of4.codon.examples.application.ProjectManagementService;
import com.page5of4.codon.examples.application.impl.OurApplicationServiceImpl;
import com.page5of4.codon.examples.application.impl.ProjectManagementServiceImpl;
import com.page5of4.codon.examples.application.model.DomainEvents;
import com.page5of4.codon.examples.application.model.ProjectEvents;
import com.page5of4.codon.examples.application.model.repositories.ProjectRepository;

@Configuration
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
   public EventBusBeanPostProcessor eventBusBeanPostProcessor() {
      return new EventBusBeanPostProcessor(eventBus());
   }

   @Bean
   public DomainEvents domainEvents() {
      return new DomainEvents(eventBus());
   }

   @Bean
   public EventBus eventBus() {
      return new EventBus();
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
