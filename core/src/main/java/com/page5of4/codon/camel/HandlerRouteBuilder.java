package com.page5of4.codon.camel;

import org.apache.camel.builder.RouteBuilder;
import org.springframework.stereotype.Service;

@Service
public class HandlerRouteBuilder extends RouteBuilder {
   private static final String ROUTE_ID_PREFIX = "listen:";
   private final InvokeHandlerProcessor handlerProcessor;
   private final String fromAddress;
   private final String poisonAddress;

   public HandlerRouteBuilder(InvokeHandlerProcessor handlerProcessor, String fromAddress) {
      this(handlerProcessor, fromAddress, fromAddress + ".poison");
   }

   public HandlerRouteBuilder(InvokeHandlerProcessor handlerProcessor, String fromAddress, String poisonAddress) {
      this.handlerProcessor = handlerProcessor;
      this.fromAddress = fromAddress;
      this.poisonAddress = poisonAddress;
   }

   @Override
   public void configure() throws Exception {
      PoisonProcessor poison = new PoisonProcessor();
      from(fromAddress).
            id(ROUTE_ID_PREFIX + fromAddress).
            transacted().
            choice().
            when(poison).
            to(poisonAddress).
            stop().
            end().
            doTry().
            process(handlerProcessor).
            doFinally().
            process(poison).
            end();
   }
}
