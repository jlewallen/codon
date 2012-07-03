package com.page5of4.codon.camel;

import org.apache.camel.Processor;
import org.apache.camel.model.ProcessorDefinition;
import org.apache.camel.spi.RouteContext;
import org.apache.camel.spi.TransactedPolicy;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class EmptyTransactionPolicy implements TransactedPolicy {
   private static final Logger logger = LoggerFactory.getLogger(EmptyTransactionPolicy.class);

   @Override
   public void beforeWrap(RouteContext routeContext, ProcessorDefinition<?> definition) {

   }

   @Override
   public Processor wrap(RouteContext routeContext, Processor processor) {
      logger.info("Wrapping {} {}", routeContext.getRoute().getId(), processor);
      return processor;
   }
}
