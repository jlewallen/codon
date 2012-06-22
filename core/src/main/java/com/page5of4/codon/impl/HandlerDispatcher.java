package com.page5of4.codon.impl;

import java.lang.reflect.Method;

import com.page5of4.codon.BusException;

public class HandlerDispatcher {
   private final InstanceResolver resolver;
   private final HandlerBinding binding;

   public InstanceResolver getResolver() {
      return resolver;
   }

   public HandlerBinding getBinding() {
      return binding;
   }

   public HandlerDispatcher(InstanceResolver resolver, HandlerBinding binding) {
      super();
      this.resolver = resolver;
      this.binding = binding;
   }

   public void dispatch(Object message) {
      Method method = binding.getMethod();
      try {
         binding.getMethod().invoke(resolver.resolve(binding.getHandlerType()), message);
      }
      catch(Exception e) {
         throw new BusException(String.format("Error invoking '%s' with '%s'", method, message), e);
      }
   }
}
