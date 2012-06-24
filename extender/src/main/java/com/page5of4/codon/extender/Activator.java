package com.page5of4.codon.extender;

import java.util.Dictionary;
import java.util.HashMap;
import java.util.Map;
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
import org.springframework.context.ApplicationContext;

import com.page5of4.codon.Bus;

public class Activator implements BundleTrackerCustomizer, BundleActivator, ServiceTrackerCustomizer {
   private static final String CODON_BUS = "Codon-Bus";
   private static final Logger logger = LoggerFactory.getLogger(Activator.class);
   private final Map<String, ExtendedBundle> map = new HashMap<String, ExtendedBundle>();
   private BundleTracker tracker;
   private ServiceTracker busServiceTracker;
   private ServiceTracker applicationContextTracker;
   private BundleContext bundleContext;

   @Override
   public void start(BundleContext context) throws Exception {
      logger.info("Starting...");

      bundleContext = context;

      busServiceTracker = new ServiceTracker(context, Bus.class.getName(), this);
      applicationContextTracker = new ServiceTracker(context, ApplicationContext.class.getName(), this);
      tracker = new BundleTracker(context, Bundle.INSTALLED | Bundle.RESOLVED | Bundle.STARTING | Bundle.ACTIVE | Bundle.STOPPING, this);

      busServiceTracker.open();
      applicationContextTracker.open();
      tracker.open();

      logger.info("Started");
   }

   @Override
   public void stop(BundleContext context) throws Exception {
      logger.info("Stopping...");

      if(busServiceTracker != null) busServiceTracker.close();
      if(applicationContextTracker != null) applicationContextTracker.close();
      if(tracker != null) tracker.close();
      bundleContext = null;

      logger.info("Stopped");
   }

   @Override
   @SuppressWarnings("unchecked")
   public Object addingBundle(final Bundle bundle, BundleEvent event) {
      Dictionary<String, String> headers = bundle.getHeaders();
      if(headers.get(CODON_BUS) != null) {
         logger.info(String.format("Adding %s %s (CCL: %s)", bundle, event != null ? OsgiStringUtils.nullSafeBundleEventToString(event.getType()) : "", Thread.currentThread().getContextClassLoader()));
         synchronized(map) {
            if(!map.containsKey(bundle.getSymbolicName())) {
               ContextClassLoaderUtils.doWithClassLoader(null, new Callable<Object>() {
                  @Override
                  public Object call() throws Exception {
                     ExtendedBundle extended = new ExtendedBundle(bundleContext, bundle);
                     map.put(bundle.getSymbolicName(), extended);
                     map.get(bundle.getSymbolicName()).open();
                     return extended;
                  }
               });
            }
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
      Dictionary<String, String> headers = bundle.getHeaders();
      if(headers.get(CODON_BUS) != null) {
         logger.info("Removed {} {}", bundle, OsgiStringUtils.nullSafeBundleEventToString(event.getType()));
         synchronized(map) {
            if(map.containsKey(bundle.getSymbolicName())) {
               map.get(bundle.getSymbolicName()).close();
               map.remove(bundle.getSymbolicName());
            }
         }
      }
   }

   @Override
   public Object addingService(ServiceReference reference) {
      logger.info("Added {}", reference);
      return null;
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
