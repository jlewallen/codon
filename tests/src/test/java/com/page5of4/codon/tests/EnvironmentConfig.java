package com.page5of4.codon.tests;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.page5of4.codon.BusConfiguration;
import com.page5of4.codon.PropertiesConfiguration;

@Configuration
public class EnvironmentConfig {
   @Value("${application.name:application}")
   private String applicationName;

   @Bean
   public BusConfiguration busConfiguration() {
      return new PropertiesConfiguration(applicationName, "local-server");
   }
}
