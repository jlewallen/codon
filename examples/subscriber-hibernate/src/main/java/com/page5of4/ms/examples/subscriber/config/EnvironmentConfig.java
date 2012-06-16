package com.page5of4.ms.examples.subscriber.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.page5of4.ms.BusConfiguration;

@Configuration
public class EnvironmentConfig {
   @Value("${application.name:subscriber-riak}")
   private String applicationName;

   @Bean
   public BusConfiguration busConfiguration() {
      return new BusConfiguration(applicationName, "local-server");
   }
}
