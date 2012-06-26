package com.page5of4.codon.extender;

import org.osgi.framework.ServiceReference;
import org.osgi.util.tracker.ServiceTrackerCustomizer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.page5of4.codon.BusModule;

public class BusModuleTracker implements ServiceTrackerCustomizer {
   private static final Logger logger = LoggerFactory.getLogger(BusModuleTracker.class);

   @Override
   public Object addingService(ServiceReference reference) {
      logger.info("Adding {}", reference);
      BusModule module = (BusModule)reference.getBundle().getBundleContext().getService(reference);
      RegisteredClient registeredClient = new RegisteredClient(module);
      registeredClient.open();
      logger.info("Added {}", reference);
      return registeredClient;
   }

   @Override
   public void modifiedService(ServiceReference reference, Object service) {
      logger.info("Modified {}", reference);
   }

   @Override
   public void removedService(ServiceReference reference, Object service) {
      logger.info("Removing {}", reference);
      ((RegisteredClient)service).close();
      logger.info("Removed {}", reference);
   }

   public static class RegisteredClient {
      private final BusModule module;

      public RegisteredClient(BusModule module) {
         this.module = module;
      }

      public void open() {
         module.open();
      }

      public void close() {
         module.close();
      }
   }
}
