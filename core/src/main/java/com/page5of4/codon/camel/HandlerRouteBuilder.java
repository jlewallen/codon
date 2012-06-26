package com.page5of4.codon.camel;

import org.apache.camel.builder.RouteBuilder;
import org.springframework.stereotype.Service;

import com.page5of4.codon.config.BusConfig;

@Service
public class HandlerRouteBuilder extends RouteBuilder {
   private final InvokeHandlerProcessor invokeHandlerProcessor;
   private final String fromAddress;

   public HandlerRouteBuilder(InvokeHandlerProcessor invokeHandlerProcessor, String fromAddress) {
      this.invokeHandlerProcessor = invokeHandlerProcessor;
      this.fromAddress = fromAddress;
   }

   @Override
   public void configure() throws Exception {
      from(fromAddress).transacted(BusConfig.TRANSACTION_POLICY_NAME).process(invokeHandlerProcessor);
   }
}
