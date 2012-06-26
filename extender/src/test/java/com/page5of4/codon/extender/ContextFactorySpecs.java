package com.page5of4.codon.extender;

import org.junit.Test;
import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.osgi.mock.MockBundleContext;

public class ContextFactorySpecs {
   @Test
   public void when_creating_context() {
      AbstractApplicationContext applicationContext = OsgiApplicationContextFactory.createApplicationContext(new MockBundleContext());
      applicationContext.refresh();
      applicationContext.destroy();
   }
}
