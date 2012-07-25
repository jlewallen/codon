package com.page5of4.codon.extender.internal.config;

import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceRegistration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.osgi.service.exporter.support.AutoExport;
import org.springframework.osgi.service.exporter.support.ExportContextClassLoader;
import org.springframework.osgi.service.exporter.support.OsgiServiceFactoryBean;

import com.page5of4.codon.Bus;
import com.page5of4.codon.HandlerRegistry;
import com.page5of4.codon.config.BusConfig;
import com.page5of4.codon.config.ConstantBusContextConfig;
import com.page5of4.codon.config.JmsTransactionConventionConfig;
import com.page5of4.codon.config.XmlSubscriptionStorageConfig;
import com.page5of4.codon.extender.internal.OsgiHandlerRegistry;

@Configuration
@Import(value = { SpringConfig.class, BusConfig.class, XmlSubscriptionStorageConfig.class, JmsTransactionConventionConfig.class, ConstantBusContextConfig.class })
public class BusRegistrationConfig {
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

   @Bean
   public HandlerRegistry handlerRegistry() {
      return new OsgiHandlerRegistry(bundleContext);
   }
}
