package com.page5of4.codon.camel;

import org.apache.camel.Processor;
import org.apache.camel.model.ProcessorDefinition;
import org.apache.camel.spi.RouteContext;
import org.apache.camel.spi.TransactedPolicy;
import org.apache.camel.spring.spi.SpringTransactionPolicy;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.PlatformTransactionManager;

import com.page5of4.codon.impl.TransactionConvention;

public class AutomaticTransactionPolicy implements TransactedPolicy {
   private static final Logger logger = LoggerFactory.getLogger(AutomaticTransactionPolicy.class);
   private final TransactionConvention transactionConvention;

   @Autowired
   public AutomaticTransactionPolicy(TransactionConvention transactionConvention) {
      super();
      this.transactionConvention = transactionConvention;
   }

   @Override
   public void beforeWrap(RouteContext routeContext, ProcessorDefinition<?> definition) {

   }

   @Override
   public Processor wrap(RouteContext routeContext, Processor processor) {
      String key = routeContext.getEndpoint().getEndpointKey();
      String uri = routeContext.getEndpoint().getEndpointUri();
      logger.info("Attempting to wrap route '{}' '{}'", uri, key);
      PlatformTransactionManager manager = transactionConvention.locate(EndpointUri.toEndpointAddress(uri).getHost());
      SpringTransactionPolicy springPolicy = new SpringTransactionPolicy(manager);
      return springPolicy.wrap(routeContext, processor);
   }
}
