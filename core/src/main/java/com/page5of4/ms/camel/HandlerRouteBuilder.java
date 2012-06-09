package com.page5of4.ms.camel;

import org.apache.camel.builder.RouteBuilder;
import org.springframework.stereotype.Service;

@Service
public class HandlerRouteBuilder extends RouteBuilder {
   private final String fromAddress;

   public HandlerRouteBuilder(String fromAddress) {
      this.fromAddress = fromAddress;
   }

   @Override
   public void configure() throws Exception {
      from(fromAddress).process(new InvokeHandlerProcessor());
   }
}
