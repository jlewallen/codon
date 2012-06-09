package com.page5of4.ms.camel;

import java.util.List;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.page5of4.ms.impl.HandlerInspector.HandlerBinding;

public class InvokeHandlerProcessor implements Processor {
   private static final Logger logger = LoggerFactory.getLogger(InvokeHandlerProcessor.class);
   private final List<HandlerBinding> bindings;

   public InvokeHandlerProcessor(List<HandlerBinding> bindings) {
      super();
      this.bindings = bindings;
   }

   @Override
   public void process(Exchange exchange) throws Exception {
      logger.debug("Processing: {}", exchange);
      for(HandlerBinding binding : bindings) {

      }
   }
}
