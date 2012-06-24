package com.page5of4.codon.extender;

import org.junit.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import com.page5of4.codon.extender.ExtendedBundle.BundleConfig;

public class ContextFactorySpecs {
   @Test
   public void when_creating_context() {
      AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext();
      applicationContext.register(BundleConfig.class);
      applicationContext.refresh();
   }
}
