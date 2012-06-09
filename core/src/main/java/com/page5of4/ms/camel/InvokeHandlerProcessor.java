package com.page5of4.ms.camel;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class InvokeHandlerProcessor implements Processor {
   private static final Logger logger = LoggerFactory.getLogger(InvokeHandlerProcessor.class);

   public InvokeHandlerProcessor() {
      super();
   }

   @Override
   public void process(Exchange exchange) throws Exception {
      logger.debug("Processing: {}", exchange);
   }
}
