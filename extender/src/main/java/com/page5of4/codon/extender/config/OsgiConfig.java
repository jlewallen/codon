package com.page5of4.codon.extender.config;

import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceRegistration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.osgi.service.exporter.support.AutoExport;
import org.springframework.osgi.service.exporter.support.ExportContextClassLoader;
import org.springframework.osgi.service.exporter.support.OsgiServiceFactoryBean;

import com.page5of4.codon.Bus;

@Configuration
public class OsgiConfig {
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