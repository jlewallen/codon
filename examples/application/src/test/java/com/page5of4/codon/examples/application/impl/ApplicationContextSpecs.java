package com.page5of4.codon.examples.application.impl;

import org.junit.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import com.page5of4.codon.examples.application.config.ApplicationConfig;

public class ApplicationContextSpecs {
   @Test
   public void when_configuring() {
      AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext();
      applicationContext.register(TestingConfig.class);
      applicationContext.register(ApplicationConfig.class);
      applicationContext.refresh();
      applicationContext.destroy();
   }
}
