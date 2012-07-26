package com.page5of4.codon.useful.spring.config;

import org.osgi.framework.BundleContext;
import org.springframework.beans.factory.BeanClassLoaderAware;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.osgi.service.importer.support.ImportContextClassLoader;
import org.springframework.osgi.service.importer.support.OsgiServiceProxyFactoryBean;

import com.page5of4.codon.useful.repositories.RepositoryFactory;

@Configuration
public class OsgiRepositoryConfig implements BeanClassLoaderAware {
   private ClassLoader classLoader;
   @Autowired
   private BundleContext bundleContext;

   @Bean
   public RepositoryFactory repositoryFactory() {
      OsgiServiceProxyFactoryBean factory = new OsgiServiceProxyFactoryBean();
      factory.setBeanClassLoader(classLoader);
      factory.setBundleContext(bundleContext);
      factory.setInterfaces(new Class[] { RepositoryFactory.class });
      factory.setContextClassLoader(ImportContextClassLoader.SERVICE_PROVIDER);
      factory.afterPropertiesSet();
      return (RepositoryFactory)factory.getObject();
   }

   @Override
   public void setBeanClassLoader(ClassLoader classLoader) {
      this.classLoader = classLoader;
   }
}
