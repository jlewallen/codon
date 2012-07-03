package com.page5of4.codon.camel;

import javax.jms.ConnectionFactory;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.activemq.RedeliveryPolicy;
import org.apache.activemq.camel.component.ActiveMQComponent;
import org.apache.activemq.pool.PooledConnectionFactory;
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
      component.setTransacted(true);
      component.setTransactionManager(transactionConvention.locate(address, component.getConfiguration().getConnectionFactory()));

      {
         ActiveMQConnectionFactory connectionFactory = getConnectionFactory(component.getConfiguration().getConnectionFactory());
         RedeliveryPolicy redeliveryPolicy = connectionFactory.getRedeliveryPolicy();
         redeliveryPolicy.setMaximumRedeliveries(4);
      }

      return component;
   }

   private ActiveMQConnectionFactory getConnectionFactory(ConnectionFactory factory) {
      if(factory instanceof PooledConnectionFactory) {
         return getConnectionFactory(((PooledConnectionFactory)factory).getConnectionFactory());
      }
      return (ActiveMQConnectionFactory)factory;
   }
}
