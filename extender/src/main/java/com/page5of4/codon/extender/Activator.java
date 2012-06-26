package com.page5of4.codon.extender;

import java.util.Dictionary;
import java.util.concurrent.Callable;

import org.osgi.framework.Bundle;
import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.osgi.framework.BundleEvent;
import org.osgi.util.tracker.BundleTrackerCustomizer;
import org.osgi.util.tracker.ServiceTracker;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.page5of4.codon.Bus;
import com.page5of4.codon.BusModule;

public class Activator implements BundleTrackerCustomizer, BundleActivator {
   private static final Logger logger = LoggerFactory.getLogger(Activator.class);
   private static final String CODON_BUS = "Codon-Bus";
   private ServiceTracker busServiceTracker;
   private ServiceTracker busModuleTracker;
   private BundleContext bundleContext;
   private OsgiApplicationContextFactory osgi;

   @Override
   public void start(BundleContext context) throws Exception {
      logger.info("Starting...");

      bundleContext = context;

      busServiceTracker = new ServiceTracker(context, Bus.class.getName(), new BusServiceTracker());
      busModuleTracker = new ServiceTracker(context, BusModule.class.getName(), new BusModuleTracker());

      busServiceTracker.open();
      busModuleTracker.open();

      osgi = new OsgiApplicationContextFactory(bundleContext);
      osgi.open();

      logger.info("Started");
   }

   @Override
   public void stop(BundleContext context) throws Exception {
      logger.info("Stopping...");

      if(busServiceTracker != null) busServiceTracker.close();
      if(busModuleTracker != null) busModuleTracker.close();
      osgi.close();
      bundleContext = null;

      logger.info("Stopped");
   }

   @Override
   @SuppressWarnings("unchecked")
   public Object addingBundle(final Bundle bundle, BundleEvent event) {
      Dictionary<String, String> headers = bundle.getHeaders();
      if(headers.get(CODON_BUS) != null) {
         logger.info(String.format("Adding %s %s (CCL: %s)", bundle, event != null ? OsgiStringUtils.nullSafeBundleEventToString(event.getType()) : "", Thread.currentThread().getContextClassLoader()));
         synchronized(this) {
            return ContextClassLoaderUtils.doWithClassLoader(null,
                  new Callable<ExtendedBundle>() {
                     @Override
                     public ExtendedBundle call() throws Exception {
                        ExtendedBundle extended = new ExtendedBundle(bundleContext, bundle);
                        extended.open();
                        return extended;
                     }
                  });
         }
      }
      return null;
   }

   @Override
   public void modifiedBundle(Bundle bundle, BundleEvent event, Object object) {

   }

   @Override
   public void removedBundle(Bundle bundle, BundleEvent event, Object object) {
      ExtendedBundle extended = (ExtendedBundle)object;
      if(extended != null) {
         logger.info("Removed {} {}", bundle, OsgiStringUtils.nullSafeBundleEventToString(event.getType()));
         extended.close();
      }
   }
}
