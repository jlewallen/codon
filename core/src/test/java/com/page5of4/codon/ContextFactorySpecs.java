package com.page5of4.codon;

import org.junit.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.page5of4.codon.config.StandaloneConfig;

public class ContextFactorySpecs {
   @Test
   public void when_creating_context() {
      AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext();
      applicationContext.register(SimpleBusConfigurationConfig.class);
      applicationContext.register(StandaloneConfig.class);
      applicationContext.refresh();
   }

   @Configuration
   public static class SimpleBusConfigurationConfig {
      @Bean
      public BusConfiguration busConfiguration() {
         return new PropertiesConfiguration("test", "mock");
      }
   }
}