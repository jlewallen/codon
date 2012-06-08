package com.page5of4.ms.config;

import org.apache.camel.CamelContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.page5of4.ms.Bus;
import com.page5of4.ms.impl.Bootstrap;
import com.page5of4.ms.impl.DefaultBus;

@Configuration
public class CoreConfig {
   @Autowired
   private CamelContext camelContext;

   @Bean
   public Bootstrap bootstrap() {
      return new Bootstrap(camelContext);
   }

   @Bean
   public Bus bus() {
      return new DefaultBus();
   }
}
