package com.page5of4.codon.impl;

import org.springframework.context.ApplicationContext;

public class ApplicationContextResolver implements InstanceResolver {
   private final ApplicationContext applicationContext;

   public ApplicationContextResolver(ApplicationContext applicationContext) {
      super();
      this.applicationContext = applicationContext;
   }

   @Override
   public Object resolve(Class<?> type) {
      return applicationContext.getBean(type);
   }
}
