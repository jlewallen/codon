package com.page5of4.ms.impl;

import java.lang.reflect.Method;

import com.page5of4.ms.AutomaticallySubscribe;
import com.page5of4.ms.BusException;

public class HandlerBinding {
   private final Class<?> handlerType;
   private final Class<?> messageType;
   private final Method method;
   private final InstanceResolver resolver;
   private final AutomaticallySubscribe automaticallySubscribe;

   public boolean shouldSubscribe() {
      return getAutomaticallySubscribe().shouldSubscribe();
   }

   public Class<?> getHandlerType() {
      return handlerType;
   }

   public Class<?> getMessageType() {
      return messageType;
   }

   public Method getMethod() {
      return method;
   }

   public AutomaticallySubscribe getAutomaticallySubscribe() {
      return automaticallySubscribe;
   }

   public HandlerBinding(Class<?> handlerType, Class<?> messageType, Method method, InstanceResolver resolver, AutomaticallySubscribe automaticallySubscribe) {
      super();
      this.handlerType = handlerType;
      this.messageType = messageType;
      this.method = method;
      this.resolver = resolver;
      this.automaticallySubscribe = automaticallySubscribe;
   }

   public void invoke(Object message) {
      try {
         method.invoke(resolver.resolve(), message);
      }
      catch(Exception e) {
         throw new BusException(String.format("Error invoking '%s' with '%s'", method, message), e);
      }
   }
}
