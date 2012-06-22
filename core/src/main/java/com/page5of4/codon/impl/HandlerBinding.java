package com.page5of4.codon.impl;

import java.lang.reflect.Method;

import com.page5of4.codon.AutomaticallySubscribe;

public class HandlerBinding {
   private final Class<?> handlerType;
   private final Class<?> messageType;
   private final Method method;
   private final AutomaticallySubscribe automaticallySubscribe;

   public boolean shouldSubscribe() {
      return automaticallySubscribe.shouldSubscribe();
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

   public HandlerBinding(Class<?> handlerType, Class<?> messageType, Method method, AutomaticallySubscribe automaticallySubscribe) {
      super();
      this.handlerType = handlerType;
      this.messageType = messageType;
      this.method = method;
      this.automaticallySubscribe = automaticallySubscribe;
   }
}
