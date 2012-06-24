package com.page5of4.codon.extender;

import org.osgi.framework.Bundle;
import org.osgi.framework.BundleEvent;
import org.springframework.core.ConstantException;
import org.springframework.core.Constants;

public abstract class OsgiStringUtils {
   public static final Constants BUNDLE_STATES = new Constants(Bundle.class);
   public static final Constants BUNDLE_EVENTS = new Constants(BundleEvent.class);
   private static final String UNKNOWN_EVENT_TYPE = "UNKNOWN EVENT TYPE";

   /**
    * Returns a String representation for the given bundle event.
    * 
    * @param eventType
    *           OSGi <code>BundleEvent</code> given as an int
    * @return String representation for the bundle event
    */
   public static String nullSafeBundleEventToString(int eventType) {
      try {
         return BUNDLE_EVENTS.toCode(new Integer(eventType), "");
      }
      catch(ConstantException cex) {
         return UNKNOWN_EVENT_TYPE;
      }

   }

   /**
    * Returns a String representation of the <code>Bundle</code> state.
    * 
    * @param bundle
    *           OSGi bundle (can be <code>null</code>)
    * @return bundle state as a string
    */
   public static String bundleStateAsString(Bundle bundle) {
      int state = bundle.getState();

      try {
         return BUNDLE_STATES.toCode(new Integer(state), "");
      }
      catch(ConstantException cex) {
         return "UNKNOWN STATE";
      }
   }
}
