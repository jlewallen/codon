package com.page5of4.codon.extender.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

import com.page5of4.codon.BusConfiguration;
import com.page5of4.codon.PropertiesConfiguration;
import com.page5of4.codon.config.BusConfig;

@Configuration
@Import(value = { SpringConfig.class, BusConfig.class })
public class BundleConfig {
   @Value("${application.name:application}")
   private String applicationName;

   @Bean
   public BusConfiguration busConfiguration() {
      return new PropertiesConfiguration(applicationName, "local-server");
   }
}