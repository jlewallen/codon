package com.page5of4.codon;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

public class BusBundle {
   private static final Logger logger = LoggerFactory.getLogger(BusBundle.class);
   private final HandlerRegistry handlerRegistry;

   @Autowired
   public BusBundle(HandlerRegistry handlerRegistry) {
      super();
      this.handlerRegistry = handlerRegistry;
   }

   @PostConstruct
   public void open() {
      logger.info("Opening");
      handlerRegistry.initialize();
   }

   @PreDestroy
   public void close() {
      logger.info("Closing");
   }
}
