package com.page5of4.codon.tests.integration;

import org.junit.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.page5of4.codon.BusConfiguration;
import com.page5of4.codon.PropertiesConfiguration;
import com.page5of4.codon.config.InMemorySubscriptionStorageConfig;
import com.page5of4.codon.config.JmsOnlyTransactionConventionConfig;
import com.page5of4.codon.config.StandaloneConfig;

public class ContextFactorySpecs {
   @Test
   public void when_creating_context() {
      AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext();
      applicationContext.register(SimpleBusConfigurationConfig.class);
      applicationContext.register(StandaloneConfig.class);
      applicationContext.register(InMemorySubscriptionStorageConfig.class);
      applicationContext.register(JmsOnlyTransactionConventionConfig.class);
      applicationContext.refresh();
      applicationContext.destroy();
   }

   @Configuration
   public static class SimpleBusConfigurationConfig {
      @Bean
      public BusConfiguration busConfiguration() {
         return new PropertiesConfiguration("test", "mock");
      }
   }
}
