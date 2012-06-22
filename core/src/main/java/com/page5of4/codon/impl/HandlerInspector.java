package com.page5of4.codon.impl;

public class HandlerInspector {
   public HandlerDescriptor discoverBindings(final Class<?> klass) {
      return new HandlerDescriptor(klass);
   }
}
