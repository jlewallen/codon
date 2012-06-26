package com.page5of4.codon;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;


public class BusModule {
   private static final Logger logger = LoggerFactory.getLogger(BusModule.class);
   private final HandlerRegistry handlerRegistry;
   private final Bus bus;

   public HandlerRegistry getHandlerRegistry() {
      return handlerRegistry;
   }

   @Autowired
   public BusModule(HandlerRegistry handlerRegistry, Bus bus) {
      super();
      this.handlerRegistry = handlerRegistry;
      this.bus = bus;
   }

   @PostConstruct
   public void initialize() {
      handlerRegistry.initialize();
      open();
   }

   public void open() {
      for(HandlerBinding binding : handlerRegistry.getBindings()) {
         logger.info("Preparing '{}'", binding.getMethod());
         Class<?> messageType = binding.getMessageType();
         if(binding.shouldSubscribe()) {
            bus.subscribe(binding.getMessageType());
         }
         bus.listen(messageType);
      }
   }

   @PreDestroy
   public void close() {
      for(HandlerBinding binding : handlerRegistry.getBindings()) {
         Class<?> messageType = binding.getMessageType();
         bus.unlisten(messageType);
      }
   }
}
