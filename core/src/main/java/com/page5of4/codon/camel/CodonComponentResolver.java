package com.page5of4.codon.camel;

import org.apache.activemq.camel.component.ActiveMQComponent;
import org.apache.camel.CamelContext;
import org.apache.camel.Component;
import org.apache.camel.impl.DefaultComponentResolver;
import org.apache.camel.spi.ComponentResolver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.page5of4.codon.impl.TransactionConvention;

public class CodonComponentResolver implements ComponentResolver {
   private static final Logger logger = LoggerFactory.getLogger(CodonComponentResolver.class);
   private final DefaultComponentResolver resolver = new DefaultComponentResolver();
   private final TransactionConvention transactionConvention;

   public CodonComponentResolver(TransactionConvention transactionConvention) {
      this.transactionConvention = transactionConvention;
   }

   @Override
   public Component resolveComponent(String name, CamelContext camelContext) throws Exception {
      logger.info("Preparing for communication with {}", name);

      ActiveMQComponent component = new ActiveMQComponent();
      component.setCamelContext(camelContext);
      component.setBrokerURL("tcp://127.0.0.1:61616");
      component.setTransactionManager(transactionConvention.locate(name, component.getConfiguration().getConnectionFactory()));
      component.setTransacted(true);
      return component;
   }

}
