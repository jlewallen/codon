package com.page5of4.codon.extender;

import org.osgi.framework.ServiceReference;
import org.osgi.util.tracker.ServiceTrackerCustomizer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class BusServiceTracker implements ServiceTrackerCustomizer {
   private static final Logger logger = LoggerFactory.getLogger(BusServiceTracker.class);

   @Override
   public Object addingService(ServiceReference reference) {
      logger.info("Added {}", reference);
      return new Object();
   }

   @Override
   public void modifiedService(ServiceReference reference, Object service) {
      logger.info("Modified {}", reference);
   }

   @Override
   public void removedService(ServiceReference reference, Object service) {
      logger.info("Removed {}", reference);
   }
}