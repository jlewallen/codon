package com.page5of4.codon.camel;

import javax.jms.ConnectionFactory;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.activemq.RedeliveryPolicy;
import org.apache.activemq.camel.component.ActiveMQComponent;
import org.apache.activemq.camel.component.ActiveMQConfiguration;
import org.apache.activemq.pool.PooledConnectionFactory;
import org.apache.camel.CamelContext;
import org.apache.camel.Component;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.connection.JmsTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;

import com.page5of4.codon.EndpointAddress;

public class ActiveMQComponentResolver implements ComponentResolver {
   private static final Logger logger = LoggerFactory.getLogger(ActiveMQComponentResolver.class);

   @Autowired(required = false)
   private PlatformTransactionManager platformTransactionManager;

   @Override
   public Component createComponent(EndpointAddress address, CamelContext camelContext) {
      logger.info("Preparing for communication to {}", address);
      ActiveMQConfiguration configuration = new ActiveMQConfiguration();
      ActiveMQComponent component = new ActiveMQComponent(configuration);
      component.setCamelContext(camelContext);
      component.setTransactionManager(platformTransactionManager);
      component.setBrokerURL("tcp://127.0.0.1:61616");

      component.setTransacted(true);

      ActiveMQConnectionFactory connectionFactory = getConnectionFactory(component.getConfiguration().getConnectionFactory());

      if(platformTransactionManager == null) {
         JmsTransactionManager manager = new JmsTransactionManager(connectionFactory);
         component.setTransactionManager(manager);
      }

      RedeliveryPolicy redeliveryPolicy = connectionFactory.getRedeliveryPolicy();
      redeliveryPolicy.setMaximumRedeliveries(4);

      return component;
   }

   private ActiveMQConnectionFactory getConnectionFactory(ConnectionFactory factory) {
      if(factory instanceof PooledConnectionFactory) {
         return getConnectionFactory(((PooledConnectionFactory)factory).getConnectionFactory());
      }
      return (ActiveMQConnectionFactory)factory;
   }
}
