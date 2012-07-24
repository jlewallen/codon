package com.page5of4.codon.extender.internal.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.page5of4.codon.BusConfiguration;
import com.page5of4.codon.PropertiesConfiguration;

@Configuration
public class DefaultBusConfigurationConfig {
   @Value("${application.name:application}")
   private String applicationName;

   @Bean
   public BusConfiguration busConfiguration() {
      PropertiesConfiguration configuration = new PropertiesConfiguration(applicationName, "local-server");
      configuration.put("bus.owner.com.page5of4", "application:application.{messageType}");
      return configuration;
   }
}
