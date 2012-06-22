package com.page5of4.codon.extender;

import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Activator implements BundleActivator {
   static final Logger logger = LoggerFactory.getLogger(Activator.class);
   private Tracker tracker;

   @Override
   public void start(BundleContext context) throws Exception {
      logger.info("Starting...");
      tracker = new Tracker(context);
      tracker.open();
   }

   @Override
   public void stop(BundleContext context) throws Exception {
      tracker.close();
      logger.info("Stopping...");
   }
}
