package com.page5of4.codon.extender.config;

import java.util.HashMap;
import java.util.Map;

import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceRegistration;
import org.springframework.beans.factory.BeanClassLoaderAware;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.osgi.service.exporter.support.ExportContextClassLoader;
import org.springframework.osgi.service.exporter.support.OsgiServiceFactoryBean;
import org.springframework.osgi.service.importer.support.ImportContextClassLoader;
import org.springframework.osgi.service.importer.support.OsgiServiceProxyFactoryBean;

import com.page5of4.codon.Bus;
import com.page5of4.codon.BusBundle;
import com.page5of4.codon.HandlerRegistry;
import com.page5of4.codon.config.CoreConfig;

@Configuration
@Import(value = { CoreConfig.class })
public class ExtenderConfig implements BeanClassLoaderAware {
   @Autowired
   private BundleContext bundleContext;
   @Autowired
   private HandlerRegistry handlerRegistry;
   private ClassLoader classLoader;

   @Bean
   public Bus bus() {
      OsgiServiceProxyFactoryBean factory = new OsgiServiceProxyFactoryBean();
      factory.setBeanClassLoader(classLoader);
      factory.setBundleContext(bundleContext);
      factory.setInterfaces(new Class[] { Bus.class });
      factory.setContextClassLoader(ImportContextClassLoader.CLIENT);
      factory.afterPropertiesSet();
      return (Bus)factory.getObject();
   }

   @Bean
   public BusBundle busBundle() {
      return new BusBundle(handlerRegistry, bus());
   }

   @Bean
   public ServiceRegistration busBundleReference() throws Exception {
      Map<String, String> properties = new HashMap<String, String>();
      OsgiServiceFactoryBean bean = new OsgiServiceFactoryBean();
      bean.setBundleContext(bundleContext);
      bean.setTarget(busBundle());
      bean.setInterfaces(new Class[] { BusBundle.class });
      bean.setContextClassLoader(ExportContextClassLoader.UNMANAGED);
      bean.setServiceProperties(properties);
      bean.afterPropertiesSet();
      return (ServiceRegistration)bean.getObject();
   }

   @Override
   public void setBeanClassLoader(ClassLoader classLoader) {
      this.classLoader = classLoader;
   }
}