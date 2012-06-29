package com.page5of4.codon.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.page5of4.codon.subscriptions.SubscriptionStorage;
import com.page5of4.codon.subscriptions.impl.InMemorySubscriptionStorage;

@Configuration
public class InMemorySubscriptionStorageConfig {
   @Bean
   public SubscriptionStorage subscriptionStorage() {
      return new InMemorySubscriptionStorage();
   }
}
