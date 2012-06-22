package com.page5of4.codon;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.page5of4.codon.impl.HandlerBinding;
import com.page5of4.codon.impl.HandlerRegistry;
import com.page5of4.codon.impl.TopologyConfiguration;
import com.page5of4.codon.impl.Transport;

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
      logger.info("Starting module...");

      handlerRegistry.initialize();

      for(HandlerBinding binding : handlerRegistry.getBindings()) {
         Class<?> messageType = binding.getMessageType();
         if(binding.shouldSubscribe()) {
            logger.info("Subscribing and listening for {}", messageType);
            bus.subscribe(binding.getMessageType());
         }
         transport.listen(topologyConfiguration.getLocalAddressOf(messageType));
      }

      logger.info("Module started");
   }
}
