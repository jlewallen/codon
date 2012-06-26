package com.page5of4.codon.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

import com.page5of4.codon.Bus;
import com.page5of4.codon.BusModule;
import com.page5of4.codon.BusModule.ModuleMode;
import com.page5of4.codon.HandlerRegistry;

@Configuration
@Import(value = { CoreConfig.class, ClientConfig.class, BusConfig.class, ConstantBusContextConfig.class, PublisherConfig.class, SubscriptionStorageConfig.class })
public class StandaloneConfig {
   @Autowired
   private Bus bus;
   @Autowired
   private HandlerRegistry handlerRegistry;

   @Bean
   public BusModule busModule() {
      return new BusModule(handlerRegistry, bus, ModuleMode.STANDALONE);
   }
}
