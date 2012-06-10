package com.page5of4.ms.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;

import com.page5of4.ms.BusException;
import com.page5of4.ms.MessageHandler;

public class HandlerRegistry {
   private static final Logger logger = LoggerFactory.getLogger(HandlerRegistry.class);
   private final Map<Object, HandlerDescriptor> descriptors = new HashMap<Object, HandlerDescriptor>();
   private final ApplicationContext applicationContext;

   public HandlerRegistry(ApplicationContext applicationContext) {
      super();
      this.applicationContext = applicationContext;
   }

   public void initialize() {
      HandlerInspector inspector = new HandlerInspector();
      List<String> problems = new ArrayList<String>();
      Map<String, Object> handlers = applicationContext.getBeansWithAnnotation(MessageHandler.class);
      for(Map.Entry<String, Object> entry : handlers.entrySet()) {
         logger.info("Inspecting {}", entry.getValue());
         HandlerDescriptor descriptor = inspector.discoverBindings(entry.getValue().getClass(), new ApplicationContextResolver(applicationContext));
         descriptors.put(entry.getValue(), descriptor);
         problems.addAll(descriptor.getProblems());
      }
      if(!problems.isEmpty()) {
         StringBuilder sb = new StringBuilder();
         for(String s : problems) {
            sb.append("\n").append(s);
         }
         throw new BusException(String.format("Error starting Bus:%s", sb));
      }
   }

   public List<HandlerBinding> getBindings() {
      List<HandlerBinding> bindings = new ArrayList<HandlerBinding>();
      for(HandlerDescriptor descriptor : descriptors.values()) {
         for(HandlerBinding binding : descriptor.getBindings()) {
            bindings.add(binding);
         }
      }
      return bindings;
   }

   public List<HandlerBinding> getBindingsFor(Class<? extends Object> messageType) {
      List<HandlerBinding> bindings = new ArrayList<HandlerBinding>();
      for(HandlerDescriptor descriptor : descriptors.values()) {
         for(HandlerBinding binding : descriptor.getBindings()) {
            if(binding.getMessageType().isAssignableFrom(messageType)) {
               bindings.add(binding);
            }
         }
      }
      return bindings;
   }
}
