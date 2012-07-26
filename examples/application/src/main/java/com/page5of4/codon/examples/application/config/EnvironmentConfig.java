package com.page5of4.codon.examples.application.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

import com.page5of4.codon.BusConfiguration;
import com.page5of4.codon.PropertiesResourceConfiguration;
import com.page5of4.codon.useful.spring.config.OsgiRepositoryConfig;

@Configuration
@ExcludeFromScan
@Import(value = OsgiRepositoryConfig.class)
public class EnvironmentConfig {
   @Value("${application.name:application}")
   private String applicationName;

   @Bean
   public BusConfiguration busConfiguration() {
      return new PropertiesResourceConfiguration(applicationName, "local-server");
   }
}
