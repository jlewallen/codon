package com.page5of4.codon.extender;

import java.util.concurrent.Callable;

import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceRegistration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.factory.config.PropertyPlaceholderConfigurer;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.osgi.context.support.OsgiBundleXmlApplicationContext;
import org.springframework.osgi.service.exporter.support.AutoExport;
import org.springframework.osgi.service.exporter.support.ExportContextClassLoader;
import org.springframework.osgi.service.exporter.support.OsgiServiceFactoryBean;

import com.page5of4.codon.Bus;
import com.page5of4.codon.BusConfiguration;
import com.page5of4.codon.PropertiesConfiguration;
import com.page5of4.codon.config.StandaloneConfig;

public class OsgiBus {
   private OsgiBundleXmlApplicationContext applicationContext;
   private final BundleContext extenderContext;

   public OsgiBus(BundleContext extenderContext) {
      super();
      this.extenderContext = extenderContext;
   }

   public void open() {
      ContextClassLoaderUtils.doWithClassLoader(null, new Callable<Object>() {
         @Override
         public Object call() throws Exception {
            applicationContext = new OsgiBundleXmlApplicationContext();
            applicationContext.setBundleContext(extenderContext);
            applicationContext.refresh();

            AnnotationConfigApplicationContext childContext = new AnnotationConfigApplicationContext();
            childContext.setClassLoader(applicationContext.getClassLoader());
            childContext.setParent(applicationContext);
            childContext.register(BundleConfig.class);
            childContext.register(OsgiConfig.class);
            childContext.refresh();

            return applicationContext;
         }
      });
   }

   public void close() {
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
   @Import(value = { SpringConfig.class, StandaloneConfig.class })
   public static class BundleConfig {
      @Value("${application.name:application}")
      private String applicationName;

      @Bean
      public BusConfiguration busConfiguration() {
         return new PropertiesConfiguration(applicationName, "local-server");
      }
   }

   @Configuration
   public static class OsgiConfig {
      @Autowired
      private BundleContext bundleContext;
      @Autowired
      private Bus bus;

      @Bean
      public ServiceRegistration osgiBus() throws Exception {
         OsgiServiceFactoryBean bean = new OsgiServiceFactoryBean();
         bean.setBundleContext(bundleContext);
         bean.setTarget(bus);
         bean.setAutoExport(AutoExport.INTERFACES);
         bean.setContextClassLoader(ExportContextClassLoader.UNMANAGED);
         bean.afterPropertiesSet();
         return (ServiceRegistration)bean.getObject();
      }
   }
}
