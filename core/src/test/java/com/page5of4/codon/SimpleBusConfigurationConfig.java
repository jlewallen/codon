package com.page5of4.codon;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.page5of4.codon.BasicStandaloneSpecs.MessageAHandler;

@Configuration
public class SimpleBusConfigurationConfig {
   @Bean
   public BusConfiguration busConfiguration() {
      PropertiesConfiguration configuration = new PropertiesConfiguration("test", "activemq");
      configuration.put("bus.owner.com.page5of4.codon", "remote:remote.{messageType}");
      return configuration;
   }

   @Bean
   public MessageAHandler messageAHandler() {
      return new MessageAHandler();
   }
}
