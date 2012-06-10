package com.page5of4.ms.impl;

public class HandlerInspector {
   public HandlerDescriptor discoverBindings(final Class<?> klass, InstanceResolver resolver) {
      return new HandlerDescriptor(klass, resolver);
   }
}
