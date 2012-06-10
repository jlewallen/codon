package com.page5of4.ms.camel;

import org.apache.camel.Exchange;
import org.apache.camel.Message;
import org.apache.camel.Processor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.page5of4.ms.impl.HandlerBinding;
import com.page5of4.ms.impl.HandlerRegistry;

public class InvokeHandlerProcessor implements Processor {
   private static final Logger logger = LoggerFactory.getLogger(InvokeHandlerProcessor.class);
   private final HandlerRegistry handlerRegistry;

   public InvokeHandlerProcessor(HandlerRegistry handlerRegistry) {
      super();
      this.handlerRegistry = handlerRegistry;
   }

   @Override
   public void process(Exchange exchange) throws Exception {
      Message message = exchange.getIn();
      Object body = message.getBody();

      logger.debug("Processing: {} {}", exchange, body);

      for(HandlerBinding binding : handlerRegistry.getBindingsFor(body.getClass())) {
         logger.debug("Invoking {}", binding.getMethod());
         binding.invoke(body);
      }
   }
}
