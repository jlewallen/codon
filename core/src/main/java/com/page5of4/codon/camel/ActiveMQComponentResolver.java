package com.page5of4.codon.camel;

import org.apache.activemq.camel.component.ActiveMQComponent;
import org.apache.camel.CamelContext;
import org.apache.camel.Component;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.page5of4.codon.EndpointAddress;
import com.page5of4.codon.impl.TransactionConvention;

public class ActiveMQComponentResolver implements ComponentResolver {
   private static final Logger logger = LoggerFactory.getLogger(ActiveMQComponentResolver.class);

   private final TransactionConvention transactionConvention;

   @Autowired
   public ActiveMQComponentResolver(TransactionConvention transactionConvention) {
      super();
      this.transactionConvention = transactionConvention;
   }

   @Override
   public Component createComponent(EndpointAddress address, CamelContext camelContext) {
      logger.info("Preparing for communication with {}", address);

      ActiveMQComponent component = new ActiveMQComponent();
      component.setCamelContext(camelContext);
      component.setBrokerURL("tcp://127.0.0.1:61616");
      component.setTransactionManager(transactionConvention.locate(address, component.getConfiguration().getConnectionFactory()));
      component.setTransacted(true);
      return component;
   }
}
