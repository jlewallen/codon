package com.page5of4.codon.persistence.voldemort.extender;

import java.util.Dictionary;
import java.util.Hashtable;
import java.util.List;

import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceRegistration;
import org.osgi.service.cm.ManagedServiceFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.collect.Lists;

public class Activator implements BundleActivator {
   private static final Logger logger = LoggerFactory.getLogger(Activator.class);
   private final List<ServiceRegistration> registrations = Lists.newArrayList();

   @Override
   public void start(BundleContext bundleContext) throws Exception {
      StoreClientFactory storeClientFactory = new StoreClientFactory(bundleContext);
      VoldemortServerFactory serverFactory = new VoldemortServerFactory(storeClientFactory);
      {
         Dictionary<String, String> properties = new Hashtable<String, String>();
         properties.put("service.pid", storeClientFactory.getName());
         registrations.add(bundleContext.registerService(ManagedServiceFactory.class.getName(), storeClientFactory, properties));
      }

      {
         Dictionary<String, String> properties = new Hashtable<String, String>();
         properties.put("service.pid", serverFactory.getName());
         registrations.add(bundleContext.registerService(ManagedServiceFactory.class.getName(), serverFactory, properties));
      }

      for(ServiceRegistration registration : registrations) {
         logger.info("Registered '{}'", registration.getReference());
      }
   }

   @Override
   public void stop(BundleContext bundleContext) throws Exception {
      for(ServiceRegistration registration : registrations) {
         registration.unregister();
         logger.info("Unregistered '{}'", registration);
      }
   }
}
