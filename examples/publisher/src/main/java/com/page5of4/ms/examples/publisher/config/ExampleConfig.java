package com.page5of4.ms.examples.publisher.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

import com.page5of4.ms.Bus;
import com.page5of4.ms.config.CoreConfig;
import com.page5of4.ms.examples.publisher.impl.Publisher;

@Configuration
@Import(value = { EnvironmentConfig.class, CoreConfig.class })
public class ExampleConfig {
   @Autowired
   private Bus bus;

   @Bean
   public Publisher publisher() {
      return new Publisher(bus);
   }

}
