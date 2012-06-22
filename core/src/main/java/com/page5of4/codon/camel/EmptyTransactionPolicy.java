package com.page5of4.codon.camel;

import org.apache.camel.Processor;
import org.apache.camel.model.ProcessorDefinition;
import org.apache.camel.spi.RouteContext;
import org.apache.camel.spi.TransactedPolicy;

public class EmptyTransactionPolicy implements TransactedPolicy {
   @Override
   public void beforeWrap(RouteContext routeContext, ProcessorDefinition<?> definition) {

   }

   @Override
   public Processor wrap(RouteContext routeContext, Processor processor) {
      return processor;
   }
}