package com.page5of4.codon.persistence.memory.extender;

import java.util.Dictionary;
import java.util.Hashtable;

import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceRegistration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.page5of4.codon.persistence.memory.MemoryRepositoryFactory;
import com.page5of4.codon.useful.repositories.Repositories;
import com.page5of4.codon.useful.repositories.RepositoryFactory;

public class Activator implements BundleActivator {
   private static final Logger logger = LoggerFactory.getLogger(Activator.class);
   private ServiceRegistration registration;

   @Override
   public void start(BundleContext bundleContext) throws Exception {
      MemoryRepositoryFactory factory = new MemoryRepositoryFactory();
      Dictionary<String, String> properties = new Hashtable<String, String>();
      properties.put(Repositories.PERSISTENCE_PROVIDER_PROPERTY, factory.getClass().getName());
      registration = bundleContext.registerService(RepositoryFactory.class.getName(), factory, properties);
      logger.info("Registered '{}'", factory);
   }

   @Override
   public void stop(BundleContext bundleContext) throws Exception {
      registration.unregister();
      logger.info("Unregistered '{}'", registration);
   }
}
