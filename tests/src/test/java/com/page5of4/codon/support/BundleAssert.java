package com.page5of4.codon.support;

import static org.fest.assertions.Formatting.format;

import org.fest.assertions.GenericAssert;
import org.osgi.framework.Bundle;

public class BundleAssert extends GenericAssert<BundleAssert, Bundle> {
   public BundleAssert(Bundle bundle) {
      super(BundleAssert.class, bundle);
   }

   public static BundleAssert assertThat(Bundle actual) {
      return new BundleAssert(actual);
   }

   public BundleAssert isActive() {
      isNotNull();
      if(actual.getState() == Bundle.ACTIVE) return this;
      throw failure(format("bundle<%s> is not active", actual));
   }
}
