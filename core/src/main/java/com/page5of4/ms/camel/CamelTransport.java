package com.page5of4.ms.camel;

import org.apache.camel.CamelContext;
import org.apache.camel.ProducerTemplate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.page5of4.ms.BusException;
import com.page5of4.ms.EndpointAddress;
import com.page5of4.ms.impl.Transport;

public class CamelTransport implements Transport {
   private static final Logger logger = LoggerFactory.getLogger(CamelTransport.class);
   private final ComponentResolver componentTemplate;
   private final CamelContext camelContext;
   private final ProducerTemplate producer;
   private final InvokeHandlerProcessor invokeHandlerProcessor;

   public static final String MESSAGE_TYPE_KEY = "messageType";
   public static final String REPLY_TO_ADDRESS_KEY = "replyTo";

   @Autowired
   public CamelTransport(CamelContext camelContext, ComponentResolver componentTemplate, InvokeHandlerProcessor invokeHandlerProcessor) {
      this.camelContext = camelContext;
      this.componentTemplate = componentTemplate;
      this.invokeHandlerProcessor = invokeHandlerProcessor;
      this.producer = camelContext.createProducerTemplate();
   }

   public void send(EndpointAddress address, Object message) {
      try {
         logger.debug("Sending {} -> {}", message, address);
         autoCreateDestination(address);
         producer.send(toEndpointUri(address), new OutgoingProcessor(message));
      }
      catch(Exception e) {
         throw new BusException(String.format("Unable to send '%s' to '%s'", message, address), e);
      }
   }

   public void listen(EndpointAddress address) {
      try {
         autoCreateDestination(address);
         camelContext.addRoutes(new HandlerRouteBuilder(invokeHandlerProcessor, toEndpointUri(address)));
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
         String name = address.getHost();
         if(camelContext.hasComponent(name) != null) {
            return;
         }
         camelContext.addComponent(name, componentTemplate.createComponent(address, camelContext));
      }
      catch(Exception e) {
         throw new BusException(String.format("Unable to create Camel component for destination '%s'", address), e);
      }
   }
}
