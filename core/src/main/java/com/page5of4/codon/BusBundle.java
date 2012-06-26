package com.page5of4.codon;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.page5of4.codon.impl.HandlerBinding;

public class BusBundle {
   private static final Logger logger = LoggerFactory.getLogger(BusBundle.class);
   private final HandlerRegistry handlerRegistry;
   private final Bus bus;

   @Autowired
   public BusBundle(HandlerRegistry handlerRegistry, Bus bus) {
      super();
      this.handlerRegistry = handlerRegistry;
      this.bus = bus;
   }

   @PostConstruct
   public void initialize() {
      handlerRegistry.initialize();
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

   public void close() {
      for(HandlerBinding binding : handlerRegistry.getBindings()) {
         Class<?> messageType = binding.getMessageType();
         bus.unlisten(messageType);
      }
   }
}
