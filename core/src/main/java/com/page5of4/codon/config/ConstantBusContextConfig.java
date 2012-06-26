package com.page5of4.codon.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.page5of4.codon.BusConfiguration;
import com.page5of4.codon.impl.BusContext;
import com.page5of4.codon.impl.BusContextProvider;
import com.page5of4.codon.impl.ConstantBusContextProvider;
import com.page5of4.codon.impl.TopologyConfiguration;

@Configuration
public class ConstantBusContextConfig {
   @Autowired
   private BusConfiguration busConfiguration;

   @Bean
   public BusContextProvider busContextProvider() {
      return new ConstantBusContextProvider(busContext());
   }

   @Bean
   public TopologyConfiguration topologyConfiguration() {
      return new TopologyConfiguration(busConfiguration);
   }

   private BusContext busContext() {
      return new BusContext(topologyConfiguration());
   }
}
