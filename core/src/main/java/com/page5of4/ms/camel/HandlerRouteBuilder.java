package com.page5of4.ms.camel;

import org.apache.camel.builder.RouteBuilder;
import org.springframework.stereotype.Service;

@Service
public class HandlerRouteBuilder extends RouteBuilder {
   private final String fromAddress;
   private final InvokeHandlerProcessor invokeHandlerProcessor;

   public HandlerRouteBuilder(String fromAddress, InvokeHandlerProcessor invokeHandlerProcessor) {
      this.fromAddress = fromAddress;
      this.invokeHandlerProcessor = invokeHandlerProcessor;
   }

   @Override
   public void configure() throws Exception {
      from(fromAddress).transacted().process(invokeHandlerProcessor);
   }
}
