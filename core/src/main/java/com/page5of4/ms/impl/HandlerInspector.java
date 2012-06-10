package com.page5of4.ms.impl;

public class HandlerInspector {
   public HandlerDescriptor discoverBindings(final Object object) {
      return new HandlerDescriptor(object.getClass(), new InstanceResolver() {
         @Override
         public Object resolve() {
            return object;
         }
      });
   }
}
