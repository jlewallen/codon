package com.page5of4.codon.extender;

import org.osgi.framework.Bundle;
import org.osgi.framework.BundleContext;
import org.osgi.framework.BundleEvent;
import org.osgi.util.tracker.BundleTracker;

public class Tracker extends BundleTracker {
   public Tracker(BundleContext context) {
      super(context, Bundle.ACTIVE | Bundle.STOPPING, null);
   }

   @Override
   public void modifiedBundle(Bundle bundle, BundleEvent event, Object object) {
      Activator.logger.info("Modified: {} {}", bundle, event);
   }

   @Override
   public Object getObject(Bundle bundle) {
      return super.getObject(bundle);
   }
}