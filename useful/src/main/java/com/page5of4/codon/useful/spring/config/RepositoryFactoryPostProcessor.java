package com.page5of4.codon.useful.spring.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.BeanDefinitionRegistryPostProcessor;
import org.springframework.beans.factory.support.RootBeanDefinition;

import com.page5of4.codon.useful.repositories.DefaultRepositoryProvider;

public class RepositoryFactoryPostProcessor implements BeanDefinitionRegistryPostProcessor {
   private static final Logger logger = LoggerFactory.getLogger(RepositoryFactoryPostProcessor.class);
   private final DefaultRepositoryProvider repositoryProvider;

   public RepositoryFactoryPostProcessor(DefaultRepositoryProvider repositoryProvider) {
      super();
      this.repositoryProvider = repositoryProvider;
   }

   @Override
   public void postProcessBeanDefinitionRegistry(BeanDefinitionRegistry registry) throws BeansException {
      logger.info(String.format("%s", registry));
   }

   @Override
   public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) throws BeansException {
      BeanDefinitionRegistry registry = (BeanDefinitionRegistry)beanFactory;
      logger.info(String.format("%s", beanFactory));
      for(Class<?> klass : repositoryProvider.findRepositoryClasses()) {
         RootBeanDefinition bean = new RootBeanDefinition(RepositoryFactoryBean.class);
         bean.setAutowireMode(RootBeanDefinition.AUTOWIRE_CONSTRUCTOR);
         bean.getPropertyValues().addPropertyValue("repositoryClass", klass);
         registry.registerBeanDefinition(klass.getName() + "Impl", bean);
      }
   }
}
