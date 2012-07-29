package com.page5of4.codon.tests.support;

import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceReference;
import org.osgi.service.cm.ConfigurationAdmin;
import org.osgi.util.tracker.ServiceTracker;

import com.page5of4.codon.Bus;

public class OsgiHelper {
   private final BundleContext bundleContext;

   public OsgiHelper(BundleContext bundleContext) {
      super();
      this.bundleContext = bundleContext;
   }

   public Bus getBus() {
      return getService(Bus.class);
   }

   public ConfigurationAdmin getConfigurationAdmin() {
      ServiceReference configurationAdminReference = bundleContext.getServiceReference(ConfigurationAdmin.class.getName());
      return (ConfigurationAdmin)bundleContext.getService(configurationAdminReference);
   }

   public <T> T getService(Class<T> klass) {
      try {
         ServiceTracker tracker = new ServiceTracker(bundleContext, klass.getName(), null);
         tracker.open();
         T service = klass.cast(tracker.waitForService(10000));
         if(service == null) {
            throw new RuntimeException("Missing " + klass);
         }
         tracker.close();
         return service;
      }
      catch(Exception e) {
         throw new RuntimeException(e);
      }
   }
}