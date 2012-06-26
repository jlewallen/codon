package com.page5of4.codon.extender;

import org.junit.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.osgi.context.support.OsgiBundleXmlApplicationContext;
import org.springframework.osgi.mock.MockBundleContext;

import com.page5of4.codon.extender.OsgiBus.BundleConfig;
import com.page5of4.codon.extender.OsgiBus.OsgiConfig;

public class ContextFactorySpecs {
   @Test
   public void when_creating_context() {
      AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext();
      applicationContext.register(BundleConfig.class);
      applicationContext.refresh();
   }

   @Test
   public void when_creating_context_with_osgi_parent() {
      OsgiBundleXmlApplicationContext applicationContext = new OsgiBundleXmlApplicationContext();
      applicationContext.setBundleContext(new MockBundleContext());
      applicationContext.refresh();

      AnnotationConfigApplicationContext childContext = new AnnotationConfigApplicationContext();
      childContext.setClassLoader(applicationContext.getClassLoader());
      childContext.setParent(applicationContext);
      childContext.register(BundleConfig.class);
      childContext.register(OsgiConfig.class);
      childContext.refresh();
   }
}
