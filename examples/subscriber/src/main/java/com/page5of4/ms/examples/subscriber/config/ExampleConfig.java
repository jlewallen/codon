package com.page5of4.ms.examples.subscriber.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

import com.page5of4.ms.Bus;
import com.page5of4.ms.config.CoreConfig;
import com.page5of4.ms.examples.subscriber.impl.UserRegisteredHandler;

@Configuration
@Import(value = { EnvironmentConfig.class, CoreConfig.class })
public class ExampleConfig {
   @Autowired
   private Bus bus;

   @Bean
   public UserRegisteredHandler userRegisteredHandler() {
      return new UserRegisteredHandler(bus);
   }
}
