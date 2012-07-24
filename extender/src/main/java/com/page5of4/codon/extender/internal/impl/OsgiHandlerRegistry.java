package com.page5of4.codon.extender.internal.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

import org.osgi.framework.BundleContext;
import org.osgi.util.tracker.ServiceTracker;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.page5of4.codon.BusException;
import com.page5of4.codon.BusModule;
import com.page5of4.codon.HandlerBinding;
import com.page5of4.codon.HandlerRegistry;

public class OsgiHandlerRegistry implements HandlerRegistry {
   private static final Logger logger = LoggerFactory.getLogger(OsgiHandlerRegistry.class);
   private final BundleContext bundleContext;
   private final ServiceTracker tracker;

   public OsgiHandlerRegistry(BundleContext bundleContext) {
      super();
      this.bundleContext = bundleContext;
      this.tracker = new ServiceTracker(bundleContext, BusModule.class.getName(), null);
   }

   @Override
   @PostConstruct
   public void initialize() {
      logger.info("Opening");
      this.tracker.open();
   }

   @PreDestroy
   public void stop() {
      this.tracker.close();
      logger.info("Stopped");
   }

   @Override
   public void addAll(List<Class<?>> classes) {
      throw new BusException("Adding directly to OsgiHandlerRegistry is disallowed");
   }

   @Override
   public List<HandlerBinding> getBindings() {
      List<HandlerBinding> bindings = new ArrayList<HandlerBinding>();
      Object[] services = tracker.getServices();
      if(services != null) {
         for(BusModule module : Arrays.copyOf(services, services.length, BusModule[].class)) {
            logger.debug("Found {}", module);
            bindings.addAll(module.getHandlerRegistry().getBindings());
         }
      }
      else {
         logger.info("No BusModules found");
      }
      return bindings;
   }

   @Override
   public List<HandlerBinding> getBindingsFor(Class<? extends Object> messageType) {
      List<HandlerBinding> bindings = new ArrayList<HandlerBinding>();
      Object[] services = tracker.getServices();
      if(services != null) {
         for(BusModule module : Arrays.copyOf(services, services.length, BusModule[].class)) {
            logger.debug("Found {}", module);
            bindings.addAll(module.getHandlerRegistry().getBindingsFor(messageType));
         }
      }
      else {
         logger.info("No BusModules found");
      }
      return bindings;
   }
}
