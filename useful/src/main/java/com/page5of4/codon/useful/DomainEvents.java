package com.page5of4.codon.useful;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.eventbus.EventBus;

public class DomainEvents {
   private static final Logger logger = LoggerFactory.getLogger(DomainEvents.class);
   private static EventBus bus;

   public static void post(Object event) {
      if(bus == null) {
         throw new RuntimeException("DomainEvents are unavailable");
      }
      bus.post(event);
   }

   public static void register(Object handler) {
      if(bus == null) {
         throw new RuntimeException("DomainEvents are unavailable");
      }
      bus.register(handler);
   }

   public DomainEvents(EventBus eventBus) {
      if(bus != null) {
         logger.warn("Overwriting existing DomainEvents EventBus.");
      }
      bus = eventBus;
   }
}
