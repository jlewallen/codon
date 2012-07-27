package com.page5of4.codon.tests.support;

import org.apache.activemq.broker.BrokerService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class EmbeddedActiveMqBrokerConfig {
   @Bean
   public BrokerService broker() throws Exception {
      BrokerService broker = new BrokerService();
      broker.addConnector("tcp://localhost:61616");
      broker.start();
      return broker;
   }
}