package com.page5of4.codon.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.page5of4.codon.BusConfiguration;
import com.page5of4.codon.subscriptions.SubscriptionStorage;
import com.page5of4.codon.subscriptions.impl.XmlSubscriptionStorage;

@Configuration
public class PublisherConfig {
   @Autowired
   private BusConfiguration busConfiguration;

   @Bean
   public SubscriptionStorage subscriptionStorage() {
      return new XmlSubscriptionStorage(busConfiguration);
   }
}
