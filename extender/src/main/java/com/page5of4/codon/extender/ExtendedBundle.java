package com.page5of4.codon.extender;

import org.osgi.framework.Bundle;
import org.osgi.framework.BundleContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.factory.config.PropertyPlaceholderConfigurer;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.osgi.util.BundleDelegatingClassLoader;

import com.page5of4.codon.BusConfiguration;
import com.page5of4.codon.PropertiesConfiguration;
import com.page5of4.codon.config.CoreConfig;

public class ExtendedBundle {
   private static final Logger logger = LoggerFactory.getLogger(ExtendedBundle.class);
   private final AnnotationConfigApplicationContext applicationContext;
   private final BundleContext extenderContext;
   private final Bundle bundle;

   public ExtendedBundle(BundleContext extenderContext, Bundle bundle) {
      super();
      this.extenderContext = extenderContext;
      this.bundle = bundle;
      applicationContext = new AnnotationConfigApplicationContext();
      applicationContext.setClassLoader(BundleDelegatingClassLoader.createBundleClassLoaderFor(extenderContext.getBundle()));
      applicationContext.register(BundleConfig.class);
      applicationContext.register(CoreConfig.class);
   }

   public void open() {
      logger.info("Opening");
      applicationContext.refresh();
   }

   public void close() {
      logger.info("Closing");
      applicationContext.destroy();
   }

   @Configuration
   public static class SpringConfig {
      @Bean
      public PropertyPlaceholderConfigurer propertyPlaceholderConfigurer() {
         PropertyPlaceholderConfigurer configurer = new PropertyPlaceholderConfigurer();
         configurer.setIgnoreUnresolvablePlaceholders(false);
         return configurer;
      }
   }

   @Configuration
   @Import(value = { SpringConfig.class })
   public static class BundleConfig {
      @Value("${application.name:application}")
      private String applicationName;

      @Bean
      public BusConfiguration busConfiguration() {
         return new PropertiesConfiguration(applicationName, "local-server");
      }
   }
}
