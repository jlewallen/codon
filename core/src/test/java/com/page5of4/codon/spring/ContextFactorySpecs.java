package com.page5of4.codon.spring;

import org.junit.Test;
import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.context.support.GenericXmlApplicationContext;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;

public class ContextFactorySpecs {
   @Test
   public void when_creating_context() {
      Resource resource = new ClassPathResource("/com/page5of4/codon/spring/test-context.xml");
      AbstractApplicationContext applicationContext = new GenericXmlApplicationContext(resource);
      applicationContext.destroy();
   }
}
