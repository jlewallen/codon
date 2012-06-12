package com.page5of4.ms.config;

import org.apache.camel.spi.TransactedPolicy;
import org.apache.camel.spring.SpringCamelContext;
import org.apache.camel.spring.spi.SpringTransactionPolicy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.PlatformTransactionManager;

import com.page5of4.ms.Bus;
import com.page5of4.ms.BusConfiguration;
import com.page5of4.ms.BusException;
import com.page5of4.ms.BusModule;
import com.page5of4.ms.camel.ActiveMQComponentResolver;
import com.page5of4.ms.camel.CamelTransport;
import com.page5of4.ms.camel.ComponentResolver;
import com.page5of4.ms.camel.EmptyTransactionPolicy;
import com.page5of4.ms.camel.InvokeHandlerProcessor;
import com.page5of4.ms.impl.ApplicationContextResolver;
import com.page5of4.ms.impl.DefaultBus;
import com.page5of4.ms.impl.HandlerRegistry;
import com.page5of4.ms.impl.TopologyConfiguration;
import com.page5of4.ms.impl.Transport;
import com.page5of4.ms.subscriptions.SubscriptionStorage;
import com.page5of4.ms.subscriptions.impl.SubscribeHandler;
import com.page5of4.ms.subscriptions.impl.UnsubscribeHandler;
import com.page5of4.ms.subscriptions.impl.XmlSubscriptionStorage;

@Configuration
public class CoreConfig {
   @Autowired
   private ApplicationContext applicationContext;
   @Autowired
   private BusConfiguration busConfiguration;

   @Bean
   public Bus bus() {
      return new DefaultBus(topologyConfiguration(), transport(), subscriptionStorage());
   }

   @Bean
   public SubscriptionStorage subscriptionStorage() {
      return new XmlSubscriptionStorage(busConfiguration);
   }

   @Bean
   public Transport transport() {
      try {
         SpringCamelContext camelContext = new SpringCamelContext(applicationContext);
         camelContext.afterPropertiesSet();
         return new CamelTransport(camelContext, componentResolver(), invokeHandlerProcessor());
      }
      catch(Exception e) {
         throw new BusException(e);
      }
   }

   @Bean
   public ComponentResolver componentResolver() {
      return new ActiveMQComponentResolver();
   }

   @Bean
   public InvokeHandlerProcessor invokeHandlerProcessor() {
      return new InvokeHandlerProcessor(handlerRegistry(), new ApplicationContextResolver(applicationContext));
   }

   @Bean
   public HandlerRegistry handlerRegistry() {
      return new HandlerRegistry(applicationContext);
   }

   @Bean
   public BusModule busModule() {
      return new BusModule(handlerRegistry(), topologyConfiguration(), transport(), bus());
   }

   @Bean
   public TopologyConfiguration topologyConfiguration() {
      return new TopologyConfiguration(busConfiguration);
   }

   @Bean
   public SubscribeHandler subscribeHandler() {
      return new SubscribeHandler(subscriptionStorage());
   }

   @Bean
   public UnsubscribeHandler unsubscribeHandler() {
      return new UnsubscribeHandler(subscriptionStorage());
   }

   public static final String TRANSACTION_POLICY_NAME = "PROPAGATION_REQUIRED";

   @Autowired(required = false)
   private PlatformTransactionManager platformTransactionManager;

   @Bean(name = TRANSACTION_POLICY_NAME)
   public TransactedPolicy propagationRequired() {
      if(platformTransactionManager == null) return new EmptyTransactionPolicy();
      SpringTransactionPolicy policy = new SpringTransactionPolicy();
      policy.setTransactionManager(platformTransactionManager);
      policy.setPropagationBehaviorName(TRANSACTION_POLICY_NAME);
      return policy;
   }
}
