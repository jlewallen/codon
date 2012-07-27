package com.page5of4.codon.tests.support;

import java.io.File;

import org.apache.activemq.broker.BrokerService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class EmbeddedActiveMqBrokerConfig {
   private static final Logger logger = LoggerFactory.getLogger(EmbeddedActiveMqBrokerConfig.class);

   @Bean
   public BrokerService broker() throws Exception {
      File file = new File("activemq-data");
      if(file.isDirectory()) {
         logger.info("Deleting directory");
         file.delete();
      }
      else {
         logger.info("No such directory!");
      }
      BrokerService broker = new BrokerService();
      broker.addConnector("tcp://localhost:61616");
      broker.start();
      return broker;
   }
}
