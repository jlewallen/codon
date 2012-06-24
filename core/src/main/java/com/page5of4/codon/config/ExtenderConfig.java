package com.page5of4.codon.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.page5of4.codon.Bus;

@Configuration
public class ExtenderConfig {
   @Bean
   public Bus bus() {
      return null;
   }
}
