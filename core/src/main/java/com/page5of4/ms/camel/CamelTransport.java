package com.page5of4.ms.camel;

import org.apache.activemq.camel.component.ActiveMQComponent;
import org.apache.camel.CamelContext;
import org.apache.camel.ProducerTemplate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.page5of4.ms.BusException;
import com.page5of4.ms.EndpointAddress;
import com.page5of4.ms.impl.Transport;

public class CamelTransport implements Transport {
   private static final Logger logger = LoggerFactory.getLogger(Transport.class);
   private final CamelContext camelContext;
   private final ProducerTemplate producer;
   private final InvokeHandlerProcessor invokeHandlerProcessor;

   public static final String MESSAGE_TYPE_KEY = "messageType";

   @Autowired
   public CamelTransport(CamelContext camelContext, InvokeHandlerProcessor invokeHandlerProcessor) {
      this.camelContext = camelContext;
      this.invokeHandlerProcessor = invokeHandlerProcessor;
      this.producer = camelContext.createProducerTemplate();
   }

   public void send(EndpointAddress address, Object message) {
      autoCreateDestination(address);
      try {
         logger.debug("Sending {} -> {}", message, address);
         producer.send(toEndpointUri(address), new OutgoingProcessor(message));
      }
      catch(Exception e) {
         throw new BusException(String.format("Unable to send %s to %s", message, address), e);
      }
   }

   public void listen(EndpointAddress address) {
      autoCreateDestination(address);
      try {
         camelContext.addRoutes(new HandlerRouteBuilder(toEndpointUri(address), invokeHandlerProcessor));
      }
      catch(Exception e) {
         throw new BusException(String.format("Unable to listen on '%s'", address), e);
      }
   }

   private String toEndpointUri(EndpointAddress address) {
      return String.format("%s:%s", address.getHost(), address.getPath());
   }

   private void autoCreateDestination(EndpointAddress address) {
      try {
         if(camelContext.hasComponent(address.getHost()) != null) {
            return;
         }

         logger.info(String.format("Auto creating destination '%s'", address.getHost()));

         ActiveMQComponent component = new ActiveMQComponent(camelContext);
         component.setBrokerURL("tcp://127.0.0.1:61616");
         camelContext.addComponent(address.getHost(), component);
      }
      catch(Exception e) {
         throw new BusException(String.format("Unable to create Camel component for destination '%s'", address), e);
      }
   }
}
