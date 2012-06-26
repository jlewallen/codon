package com.page5of4.codon.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.page5of4.codon.BusConfiguration;
import com.page5of4.codon.subscriptions.SubscriptionStorage;
import com.page5of4.codon.subscriptions.impl.SubscribeHandler;
import com.page5of4.codon.subscriptions.impl.UnsubscribeHandler;
import com.page5of4.codon.subscriptions.impl.XmlSubscriptionStorage;

@Configuration
public class PublisherConfig {
   @Bean
   public SubscribeHandler subscribeHandler() {
      return new SubscribeHandler(subscriptionStorage());
   }

   @Bean
   public UnsubscribeHandler unsubscribeHandler() {
      return new UnsubscribeHandler(subscriptionStorage());
   }

   @Autowired
   private BusConfiguration busConfiguration;

   @Bean
   public SubscriptionStorage subscriptionStorage() {
      return new XmlSubscriptionStorage(busConfiguration);
   }
}