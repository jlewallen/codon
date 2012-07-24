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
import com.page5of4.codon.BusModule;
import com.page5of4.codon.BusModule.ModuleMode;
import com.page5of4.codon.HandlerRegistry;
import com.page5of4.codon.config.ClientConfig;
import com.page5of4.codon.config.CoreConfig;
import com.page5of4.codon.config.PublisherConfig;

@Configuration
@Import(value = { CoreConfig.class, PublisherConfig.class, ClientConfig.class })
public class OsgiBusConfig implements BeanClassLoaderAware {
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
   public BusModule busModule() {
      return new BusModule(handlerRegistry, bus(), ModuleMode.OSGI);
   }

   @Bean
   public ServiceRegistration busModuleReference() throws Exception {
      Map<String, String> properties = new HashMap<String, String>();
      OsgiServiceFactoryBean bean = new OsgiServiceFactoryBean();
      bean.setBundleContext(bundleContext);
      bean.setTarget(busModule());
      bean.setInterfaces(new Class[] { BusModule.class });
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
