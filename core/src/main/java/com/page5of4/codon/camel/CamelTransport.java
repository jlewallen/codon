package com.page5of4.codon.camel;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

import org.apache.camel.ProducerTemplate;
import org.apache.camel.model.ModelCamelContext;
import org.apache.camel.model.RoutesDefinition;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.page5of4.codon.BusException;
import com.page5of4.codon.EndpointAddress;
import com.page5of4.codon.Transport;

public class CamelTransport implements Transport {
   private static final Logger logger = LoggerFactory.getLogger(CamelTransport.class);
   private final Map<EndpointAddress, ListeningOn> listenerMap = new ConcurrentHashMap<EndpointAddress, ListeningOn>();
   private final ComponentResolver componentTemplate;
   private final ModelCamelContext camelContext;
   private final ProducerTemplate producer;
   private final InvokeHandlerProcessor invokeHandlerProcessor;

   public static final String MESSAGE_TYPE_KEY = "messageType";
   public static final String REPLY_TO_ADDRESS_KEY = "replyTo";

   public ModelCamelContext getCamelContext() {
      return camelContext;
   }

   public static class ListeningOn {
      private final RoutesDefinition routes;
      private final AtomicInteger numberOfListeners = new AtomicInteger();

      public ListeningOn(RoutesDefinition routes) {
         super();
         this.routes = routes;
      }

      public RoutesDefinition getRoutes() {
         return routes;
      }

      public int increase() {
         return numberOfListeners.incrementAndGet();
      }

      public int getNumberOfListeners() {
         return numberOfListeners.get();
      }

      public int decrease() {
         return numberOfListeners.decrementAndGet();
      }
   }

   @Autowired
   public CamelTransport(ModelCamelContext camelContext, ComponentResolver componentTemplate, InvokeHandlerProcessor invokeHandlerProcessor) {
      this.camelContext = camelContext;
      this.componentTemplate = componentTemplate;
      this.invokeHandlerProcessor = invokeHandlerProcessor;
      this.producer = camelContext.createProducerTemplate();
   }

   @PostConstruct
   public void start() {
      try {
         camelContext.start();
      }
      catch(Exception e) {
         throw new BusException(e);
      }
   }

   @PreDestroy
   public void stop() {
      try {
         camelContext.stop();
      }
      catch(Exception e) {
         throw new BusException(e);
      }
   }

   @Override
   public void send(EndpointAddress address, Object message) {
      try {
         logger.debug("Sending {} -> {}", message, address);
         autoCreateDestination(address);
         producer.send(EndpointUri.fromEndpointAddress(address), new OutgoingProcessor(message));
      }
      catch(Exception e) {
         throw new BusException(String.format("Unable to send '%s' to '%s'", message, address), e);
      }
   }

   @Override
   public void listen(EndpointAddress address) {
      try {
         synchronized(listenerMap) {
            if(!listenerMap.containsKey(address)) {
               autoCreateDestination(address);
               HandlerRouteBuilder builder = new HandlerRouteBuilder(invokeHandlerProcessor, EndpointUri.fromEndpointAddress(address));
               ListeningOn listening = new ListeningOn(builder.getRouteCollection());
               listenerMap.put(address, listening);
               camelContext.addRoutes(builder);
            }
            ListeningOn listening = listenerMap.get(address);
            logger.info("{} listeners on {}", listening.increase(), address);
         }
      }
      catch(Exception e) {
         throw new BusException(String.format("Unable to listen on '%s'", address), e);
      }
   }

   @Override
   public void unlisten(EndpointAddress address) {
      try {
         synchronized(listenerMap) {
            if(!listenerMap.containsKey(address)) {
               logger.warn("Not listening to {}", address);
               return;
            }
            ListeningOn listening = listenerMap.get(address);
            int remaining = listening.decrease();
            logger.info("{} listeners on {}", remaining, address);
            if(remaining == 0) {
               camelContext.removeRouteDefinitions(listening.getRoutes().getRoutes());
               listenerMap.remove(address);
            }
         }
      }
      catch(Exception e) {
         throw new BusException(String.format("Unable to listen on '%s'", address), e);
      }
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
