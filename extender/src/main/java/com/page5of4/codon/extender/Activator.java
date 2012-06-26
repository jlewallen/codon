package com.page5of4.codon.extender;

import java.util.Dictionary;
import java.util.concurrent.Callable;

import org.osgi.framework.Bundle;
import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.osgi.framework.BundleEvent;
import org.osgi.framework.ServiceReference;
import org.osgi.util.tracker.BundleTracker;
import org.osgi.util.tracker.BundleTrackerCustomizer;
import org.osgi.util.tracker.ServiceTracker;
import org.osgi.util.tracker.ServiceTrackerCustomizer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.page5of4.codon.Bus;
import com.page5of4.codon.BusBundle;

public class Activator implements BundleTrackerCustomizer, BundleActivator {
   private static final Logger logger = LoggerFactory.getLogger(Activator.class);
   private static final String CODON_BUS = "Codon-Bus";
   private BundleTracker tracker;
   private ServiceTracker busServiceTracker;
   private ServiceTracker bundleTracker;
   private BundleContext bundleContext;
   private OsgiBus osgi;

   @Override
   public void start(BundleContext context) throws Exception {
      logger.info("Starting...");

      bundleContext = context;

      osgi = new OsgiBus(bundleContext);
      osgi.open();

      busServiceTracker = new ServiceTracker(context, Bus.class.getName(), new BusServiceTracker());
      bundleTracker = new ServiceTracker(context, BusBundle.class.getName(), new ClientBundleTracker());
      // tracker = new BundleTracker(context, Bundle.INSTALLED | Bundle.RESOLVED | Bundle.STARTING | Bundle.ACTIVE |
      // Bundle.STOPPING, this);

      busServiceTracker.open();
      bundleTracker.open();
      // tracker.open();

      logger.info("Started");
   }

   @Override
   public void stop(BundleContext context) throws Exception {
      logger.info("Stopping...");

      if(busServiceTracker != null) busServiceTracker.close();
      if(bundleTracker != null) bundleTracker.close();
      // if(tracker != null) tracker.close();
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
   @SuppressWarnings("unchecked")
   public void removedBundle(Bundle bundle, BundleEvent event, Object object) {
      ExtendedBundle extended = (ExtendedBundle)object;
      if(extended != null) {
         logger.info("Removed {} {}", bundle, OsgiStringUtils.nullSafeBundleEventToString(event.getType()));
         extended.close();
      }
   }

   public static class ClientBundleTracker implements ServiceTrackerCustomizer {
      private static final Logger logger = LoggerFactory.getLogger(ClientBundleTracker.class);

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

   public static class BusServiceTracker implements ServiceTrackerCustomizer {
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
}
