package com.page5of4.ms.impl;

import org.apache.camel.CamelContext;
import org.apache.camel.ProducerTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.page5of4.ms.EndpointAddress;

@Service
public class Transport {
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
         producer.sendBody(toCamelUrl(address), message);
      }
      catch(Exception e) {
         throw new RuntimeException(String.format("Unable to send %s to %s", message, address), e);
      }
   }

   private String toCamelUrl(EndpointAddress address) {
      return String.format("activemq:%s", address);
   }
}
