package com.page5of4.ms;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;

import com.page5of4.ms.impl.HandlerInspector;
import com.page5of4.ms.impl.HandlerInspector.HandlerBinding;
import com.page5of4.ms.impl.HandlerInspector.HandlerDescriptor;

public class BusModule {
   private static final Logger logger = LoggerFactory.getLogger(BusModule.class);
   private final ApplicationContext applicationContext;
   private final Bus bus;

   public BusModule(ApplicationContext applicationContext, Bus bus) {
      this.applicationContext = applicationContext;
      this.bus = bus;
   }

   @PostConstruct
   public void startup() {
      logger.info("Starting Module...");

      HandlerInspector inspector = new HandlerInspector();
      List<String> problems = new ArrayList<String>();
      Map<Object, HandlerDescriptor> descriptors = new HashMap<Object, HandlerDescriptor>();
      Map<String, Object> handlers = applicationContext.getBeansWithAnnotation(MessageHandler.class);
      for(Map.Entry<String, Object> entry : handlers.entrySet()) {
         logger.info("{} {}", entry.getKey(), entry.getValue());
         HandlerDescriptor descriptor = inspector.discoverBindings(entry.getValue());
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
      for(HandlerDescriptor descriptor : descriptors.values()) {
         for(HandlerBinding binding : descriptor.getBindings()) {
            bus.subscribe(binding.getMessageType());
         }
      }

      logger.info("Started");
   }

   @PreDestroy
   public void shutdown() {
      logger.info("Shutdown");
   }
}
