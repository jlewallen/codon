package com.page5of4.ms.impl;

public class HandlerInspector {
   public HandlerDescriptor discoverBindings(final Class<?> klass) {
      return new HandlerDescriptor(klass);
   }
}
