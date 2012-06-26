package com.page5of4.codon.config;

import org.apache.camel.spi.TransactedPolicy;
import org.apache.camel.spring.SpringCamelContext;
import org.apache.camel.spring.spi.SpringTransactionPolicy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.transaction.PlatformTransactionManager;

import com.page5of4.codon.Bus;
import com.page5of4.codon.BusConfiguration;
import com.page5of4.codon.BusException;
import com.page5of4.codon.HandlerRegistry;
import com.page5of4.codon.Transport;
import com.page5of4.codon.camel.ActiveMQComponentResolver;
import com.page5of4.codon.camel.CamelTransport;
import com.page5of4.codon.camel.ComponentResolver;
import com.page5of4.codon.camel.EmptyTransactionPolicy;
import com.page5of4.codon.camel.InvokeHandlerProcessor;
import com.page5of4.codon.impl.ApplicationContextResolver;
import com.page5of4.codon.impl.DefaultBus;
import com.page5of4.codon.impl.TopologyConfiguration;
import com.page5of4.codon.subscriptions.SubscriptionStorage;
import com.page5of4.codon.subscriptions.impl.SubscribeHandler;
import com.page5of4.codon.subscriptions.impl.UnsubscribeHandler;
import com.page5of4.codon.subscriptions.impl.XmlSubscriptionStorage;

@Configuration
@Import(value = { CoreConfig.class })
public class BusConfig {
   @Autowired
   private ApplicationContext applicationContext;
   @Autowired
   private BusConfiguration busConfiguration;
   @Autowired
   private HandlerRegistry handlerRegistry;

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
      return new InvokeHandlerProcessor(handlerRegistry, new ApplicationContextResolver(applicationContext));
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
