package com.page5of4.codon.camel;

import org.apache.activemq.camel.component.ActiveMQComponent;
import org.apache.camel.CamelContext;
import org.apache.camel.Component;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.PlatformTransactionManager;

import com.page5of4.codon.EndpointAddress;

public class ActiveMQComponentResolver implements ComponentResolver {
   private static final Logger logger = LoggerFactory.getLogger(ActiveMQComponentResolver.class);

   @Autowired(required = false)
   private PlatformTransactionManager platformTransactionManager;

   @Override
   public Component createComponent(EndpointAddress address, CamelContext camelContext) {
      logger.info("Preparing for communication to {}", address);
      ActiveMQComponent component = new ActiveMQComponent(camelContext);
      component.setTransactionManager(platformTransactionManager);
      component.setBrokerURL("tcp://127.0.0.1:61616");
      return component;
   }
}
