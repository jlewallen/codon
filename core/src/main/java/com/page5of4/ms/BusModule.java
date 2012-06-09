package com.page5of4.ms;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.page5of4.ms.impl.HandlerRegistry;
import com.page5of4.ms.impl.TopologyConfiguration;
import com.page5of4.ms.impl.Transport;

public class BusModule {
   private static final Logger logger = LoggerFactory.getLogger(BusModule.class);
   private final HandlerRegistry handlerRegistry;
   private final TopologyConfiguration topologyConfiguration;
   private final Transport transport;
   private final Bus bus;

   public BusModule(HandlerRegistry handlerRegistry, TopologyConfiguration topologyConfiguration, Transport transport, Bus bus) {
      this.handlerRegistry = handlerRegistry;
      this.topologyConfiguration = topologyConfiguration;
      this.transport = transport;
      this.bus = bus;
   }

   @PostConstruct
   public void initialize() {
      logger.info("Starting Module...");

      handlerRegistry.initialize();

      for(Class<?> messageType : handlerRegistry.getMessageTypesThereAreHandlersFor()) {
         bus.subscribe(messageType);
         transport.listen(topologyConfiguration.getLocalAddressOf(messageType));
         transport.listen(topologyConfiguration.getSubscriptionAddressOf(messageType));
      }

      logger.info("Started");
   }
}
