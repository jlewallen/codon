package com.page5of4.codon.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.page5of4.codon.HandlerRegistry;
import com.page5of4.codon.impl.SpringHandlerRegistry;

@Configuration
public class CoreConfig {
   @Autowired
   private ApplicationContext applicationContext;

   @Bean
   public HandlerRegistry handlerRegistry() {
      return new SpringHandlerRegistry(applicationContext);
   }
}
