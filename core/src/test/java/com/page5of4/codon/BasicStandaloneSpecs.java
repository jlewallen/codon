package com.page5of4.codon;

import org.junit.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.page5of4.codon.config.PublisherConfig;
import com.page5of4.codon.config.StandaloneConfig;

public class BasicStandaloneSpecs {
   @Test
   public void when_starting_things_start_listening_and_publishing() throws InterruptedException {
      AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext();
      applicationContext.register(SimpleBusConfigurationConfig.class);
      applicationContext.register(StandaloneConfig.class);
      applicationContext.register(PublisherConfig.class);
      applicationContext.refresh();
      Thread.sleep(1000);
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
