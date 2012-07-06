package com.page5of4.codon.examples.subscriber.impl;

import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Activator implements BundleActivator {
   private static final Logger logger = LoggerFactory.getLogger(Activator.class);

   @Override
   public void start(BundleContext bundleContext) throws Exception {
      logger.info("Registered...");
      ApplicationServiceImpl service = new ApplicationServiceImpl(bundleContext);
      service.started();
      logger.info("Ready...");
   }

   @Override
   public void stop(BundleContext bundleContext) throws Exception {

   }
}
