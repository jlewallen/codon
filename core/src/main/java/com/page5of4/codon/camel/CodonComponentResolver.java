package com.page5of4.codon.camel;

import org.apache.camel.CamelContext;
import org.apache.camel.Component;
import org.apache.camel.component.jms.JmsComponent;
import org.apache.camel.impl.DefaultComponentResolver;
import org.apache.camel.spi.ComponentResolver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.page5of4.codon.impl.TransactionConvention;

public class CodonComponentResolver implements ComponentResolver {
   private static final Logger logger = LoggerFactory.getLogger(CodonComponentResolver.class);
   private static String TEMPLATE_COMPONENT_NAME = "activemq";
   private final DefaultComponentResolver resolver = new DefaultComponentResolver();
   private final TransactionConvention transactionConvention;

   public CodonComponentResolver(TransactionConvention transactionConvention) {
      this.transactionConvention = transactionConvention;
   }

   @Override
   public Component resolveComponent(String name, CamelContext camelContext) throws Exception {
      logger.info("Resolving '{}'", name);
      Component resolved = resolver.resolveComponent(TEMPLATE_COMPONENT_NAME, camelContext);
      if(resolved instanceof JmsComponent) {
         JmsComponent jmsComponent = (JmsComponent)resolved;
         jmsComponent.setTransactionManager(transactionConvention.locate(name, jmsComponent.getConfiguration().getConnectionFactory()));
         jmsComponent.setTransacted(true);
      }
      return resolved;
   }
}
