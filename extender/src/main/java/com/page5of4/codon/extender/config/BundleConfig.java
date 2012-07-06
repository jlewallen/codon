package com.page5of4.codon.extender.config;

import org.osgi.framework.BundleContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.jms.core.JmsOperations;

import com.page5of4.codon.BusConfiguration;
import com.page5of4.codon.HandlerRegistry;
import com.page5of4.codon.PropertiesConfiguration;
import com.page5of4.codon.config.BusConfig;
import com.page5of4.codon.config.ConstantBusContextConfig;
import com.page5of4.codon.config.JmsTransactionConventionConfig;
import com.page5of4.codon.config.XmlSubscriptionStorageConfig;
import com.page5of4.codon.extender.impl.OsgiHandlerRegistry;

@Configuration
@Import(value = { SpringConfig.class, BusConfig.class, XmlSubscriptionStorageConfig.class, JmsTransactionConventionConfig.class, ConstantBusContextConfig.class })
public class BundleConfig {
   @Value("${application.name:application}")
   private String applicationName;
   @Autowired
   private BundleContext bundleContext;

   @Bean
   public BusConfiguration busConfiguration() {
      PropertiesConfiguration configuration = new PropertiesConfiguration(applicationName, "local-server");
      configuration.put("bus.owner.com.page5of4", "application:application.{messageType}");
      return configuration;
   }

   @Bean
   public HandlerRegistry handlerRegistry() {
      return new OsgiHandlerRegistry(bundleContext);
   }
}
