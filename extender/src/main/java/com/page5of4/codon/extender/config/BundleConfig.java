package com.page5of4.codon.extender.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

import com.page5of4.codon.BusConfiguration;
import com.page5of4.codon.PropertiesConfiguration;
import com.page5of4.codon.config.BusConfig;
import com.page5of4.codon.config.ConstantBusContextConfig;

@Configuration
@Import(value = { SpringConfig.class, BusConfig.class, ConstantBusContextConfig.class })
public class BundleConfig {
   @Value("${application.name:application}")
   private String applicationName;

   @Bean
   public BusConfiguration busConfiguration() {
      PropertiesConfiguration configuration = new PropertiesConfiguration(applicationName, "local-server");
      configuration.put("bus.owner.com.page5of4", "application:application.{messageType}");
      return configuration;
   }
}
