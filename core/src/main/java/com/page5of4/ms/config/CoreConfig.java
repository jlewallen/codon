package com.page5of4.ms.config;

import org.apache.camel.CamelContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.page5of4.ms.Bus;
import com.page5of4.ms.impl.AddressNamingConvention;
import com.page5of4.ms.impl.Bootstrap;
import com.page5of4.ms.impl.DefaultBus;
import com.page5of4.ms.impl.TopologyConfiguration;
import com.page5of4.ms.impl.Transport;
import com.page5of4.ms.subscriptions.SubscriptionStorage;
import com.page5of4.ms.subscriptions.impl.XmlSubscriptionStorage;

@Configuration
public class CoreConfig {
   @Autowired
   private CamelContext camelContext;

   @Bean
   public Bootstrap bootstrap() {
      return new Bootstrap(camelContext);
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
      return new Transport(camelContext);
   }
}
