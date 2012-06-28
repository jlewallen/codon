package com.page5of4.codon.tests.support;

import org.apache.camel.model.ModelCamelContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.page5of4.codon.camel.CamelTransport;

@Configuration
public class ExposeCamelContextConfig {
   @Autowired
   CamelTransport transport;

   @Bean
   public ModelCamelContext camelContext() {
      return transport.getCamelContext();
   }
}
