package com.page5of4.codon.extender;

import org.osgi.framework.ServiceReference;
import org.osgi.util.tracker.ServiceTrackerCustomizer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.page5of4.codon.BusBundle;

public class ClientBundleTracker implements ServiceTrackerCustomizer {
   private static final Logger logger = LoggerFactory.getLogger(ClientBundleTracker.class);

   @Override
   public Object addingService(ServiceReference reference) {
      logger.info("Adding {}", reference);
      BusBundle bundle = (BusBundle)reference.getBundle().getBundleContext().getService(reference);
      RegisteredClient registeredClient = new RegisteredClient(bundle);
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
      private final BusBundle bundle;

      public RegisteredClient(BusBundle bundle) {
         this.bundle = bundle;
      }

      public void open() {
         bundle.open();
      }

      public void close() {
         bundle.close();
      }
   }
}
