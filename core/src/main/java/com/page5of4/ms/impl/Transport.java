package com.page5of4.ms.impl;

import org.apache.camel.CamelContext;
import org.apache.camel.ProducerTemplate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.page5of4.ms.EndpointAddress;

@Service
public class Transport {
   private static final Logger logger = LoggerFactory.getLogger(Transport.class);
   private final CamelContext camelContext;
   private ProducerTemplate producer;

   @Autowired
   public Transport(CamelContext camelContext) {
      super();
      this.camelContext = camelContext;
      this.producer = camelContext.createProducerTemplate();
   }

   public void send(EndpointAddress address, Object message) {
      try {
         logger.debug("Sending {} -> {}", message, address);
         producer.sendBody(toCamelUrl(address), message);
      }
      catch(Exception e) {
         throw new RuntimeException(String.format("Unable to send %s to %s", message, address), e);
      }
   }

   public void listen(EndpointAddress address) {

   }

   private String toCamelUrl(EndpointAddress address) {
      return String.format("activemq:%s", address);
   }
}
