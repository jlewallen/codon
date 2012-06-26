package com.page5of4.codon;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.page5of4.codon.impl.HandlerBinding;

public class BusModule {
   private static final Logger logger = LoggerFactory.getLogger(BusModule.class);
   private final HandlerRegistry handlerRegistry;
   private final Bus bus;

   public BusModule(HandlerRegistry handlerRegistry, Bus bus) {
      this.handlerRegistry = handlerRegistry;
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
         bus.listen(messageType);
      }

      logger.info("Module started");
   }
}
