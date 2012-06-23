package com.page5of4.codon.examples.application.config;

import java.lang.reflect.Method;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.util.ReflectionUtils;

import com.google.common.eventbus.EventBus;
import com.google.common.eventbus.Subscribe;

public class EventBusBeanPostProcessor implements BeanPostProcessor {
   private static final Logger logger = LoggerFactory.getLogger(EventBusBeanPostProcessor.class);
   private final EventBus eventBus;

   public EventBusBeanPostProcessor(EventBus eventBus) {
      super();
      this.eventBus = eventBus;
   }

   @Override
   public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
      if(shouldRegister(bean.getClass())) {
         logger.info("Registering {} with {}", bean, eventBus);
         eventBus.register(bean);
      }
      return bean;
   }

   @Override
   public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
      return bean;
   }

   protected boolean shouldRegister(Class<?> klass) {
      Method[] methods = ReflectionUtils.getAllDeclaredMethods(klass);
      for(Method method : methods) {
         if(method.getAnnotation(Subscribe.class) != null) {
            return true;
         }
      }
      return false;
   }
}
