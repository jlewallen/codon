package com.page5of4.ms.config;

import org.apache.camel.CamelContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.page5of4.ms.Bus;
import com.page5of4.ms.BusModule;
import com.page5of4.ms.camel.InvokeHandlerProcessor;
import com.page5of4.ms.impl.AddressNamingConvention;
import com.page5of4.ms.impl.Bootstrap;
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
   private CamelContext camelContext;
   @Autowired
   private ApplicationContext applicationContext;

   @Bean
   public Bootstrap bootstrap() {
      return new Bootstrap();
   }

   @Bean
   public Bus bus() {
      return new DefaultBus(topologyConfiguration(), transport(), subscriptionStorage());
   }

   @Bean
   public TopologyConfiguration topologyConfiguration() {
      return new TopologyConfiguration(new AddressNamingConvention());
   }

   @Bean
   public SubscriptionStorage subscriptionStorage() {
      return new XmlSubscriptionStorage();
   }

   @Bean
   public Transport transport() {
      return new Transport(camelContext, invokeHandlerProcessor());
   }

   @Bean
   public InvokeHandlerProcessor invokeHandlerProcessor() {
      return new InvokeHandlerProcessor(handlerRegistry());
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
   public SubscribeHandler subscribeHandler() {
      return new SubscribeHandler(subscriptionStorage());
   }

   @Bean
   public UnsubscribeHandler unsubscribeHandler() {
      return new UnsubscribeHandler(subscriptionStorage());
   }
}
