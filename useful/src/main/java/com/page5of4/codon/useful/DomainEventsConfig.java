package com.page5of4.codon.useful;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.google.common.eventbus.EventBus;

@Configuration
public class DomainEventsConfig {
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
}
