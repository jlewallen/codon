package com.page5of4.codon.examples.application.config;

import java.util.UUID;

import org.apache.camel.builder.RouteBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

import com.google.common.eventbus.EventBus;
import com.page5of4.codon.Bus;
import com.page5of4.codon.camel.OutgoingProcessor;
import com.page5of4.codon.camel.SendLocalProcessor;
import com.page5of4.codon.config.CoreConfig;
import com.page5of4.codon.examples.application.OurApplicationService;
import com.page5of4.codon.examples.application.ProjectManagementService;
import com.page5of4.codon.examples.application.impl.OurApplicationServiceImpl;
import com.page5of4.codon.examples.application.impl.ProjectManagementServiceImpl;
import com.page5of4.codon.examples.application.impl.Publisher;
import com.page5of4.codon.examples.application.model.DomainEvents;
import com.page5of4.codon.examples.application.model.ProjectEvents;
import com.page5of4.codon.examples.application.model.repositories.ProjectRepository;
import com.page5of4.codon.examples.messages.LaunchWorkMessage;

@Configuration
@Import(value = { EnvironmentConfig.class, CoreConfig.class })
public class ExampleConfig {
   @Autowired
   private Bus bus;

   @Bean
   public Publisher publisher() {
      return new Publisher(bus);
   }

   @Bean
   public OurApplicationService ourApplicationService() {
      return new OurApplicationServiceImpl(bus);
   }

   @Bean
   public ProjectManagementService projectManagementService() {
      return new ProjectManagementServiceImpl(projectRepository(), bus);
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
      ProjectEvents projectEvents = new ProjectEvents(bus);
      eventBus().register(projectEvents);
      return projectEvents;
   }

   @Bean
   public JobRouteBuilder jobRouteBuilder() {
      return new JobRouteBuilder(bus);
   }

   public static class JobRouteBuilder extends RouteBuilder {
      private final Bus bus;

      public JobRouteBuilder(Bus bus) {
         this.bus = bus;
      }

      @Override
      public void configure() throws Exception {
         from("timer://job?fixedRate=true&period=1000").
               process(new OutgoingProcessor(new LaunchWorkMessage(UUID.randomUUID(), 1L))).
               process(new SendLocalProcessor(bus));
      }
   }
}
