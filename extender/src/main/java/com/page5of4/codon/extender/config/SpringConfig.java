package com.page5of4.codon.extender.config;

import org.springframework.beans.factory.config.PropertyPlaceholderConfigurer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SpringConfig {
   @Bean
   public PropertyPlaceholderConfigurer propertyPlaceholderConfigurer() {
      PropertyPlaceholderConfigurer configurer = new PropertyPlaceholderConfigurer();
      configurer.setIgnoreUnresolvablePlaceholders(false);
      return configurer;
   }
}