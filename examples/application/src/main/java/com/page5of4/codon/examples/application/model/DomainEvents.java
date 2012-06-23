package com.page5of4.codon.examples.application.model;

import com.google.common.eventbus.EventBus;

public class DomainEvents {
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
      bus = eventBus;
   }
}
