package com.page5of4.codon.extender;

import org.junit.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.osgi.mock.MockBundleContext;

import com.page5of4.codon.extender.config.BundleConfig;

public class ContextFactorySpecs {
   @Test
   public void when_creating_context() {
      AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext();
      applicationContext.register(BundleConfig.class);
      applicationContext.refresh();
   }

   @Test
   public void when_creating_context_with_osgi_parent() {
      AbstractApplicationContext applicationContext = OsgiApplicationContextFactory.createApplicationContext(new MockBundleContext());
      applicationContext.refresh();
      applicationContext.destroy();
   }
}
