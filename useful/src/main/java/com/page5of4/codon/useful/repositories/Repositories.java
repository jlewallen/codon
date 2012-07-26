package com.page5of4.codon.useful.repositories;

import org.springframework.aop.framework.ProxyFactory;

public class Repositories {
   public static final String PERSISTENCE_PROVIDER_PROPERTY = "codon.persistence.provider";

   public static <T extends Repository<?, ?>> T create(Class<T> repositoryClass, Class<? extends RepositoryFactory> factoryClass) {
      try {
         return create(repositoryClass, factoryClass.newInstance());
      }
      catch(Exception e) {
         throw new RuntimeException(e);
      }
   }

   public static <T extends Repository<?, ?>> T create(Class<T> repositoryClass, RepositoryFactory factory) {
      try {
         ProxyFactory proxyFactory = new ProxyFactory(factory.createRepository(repositoryClass));
         proxyFactory.addInterface(repositoryClass);
         proxyFactory.setProxyTargetClass(true);
         Object proxy = proxyFactory.getProxy(Thread.currentThread().getContextClassLoader());
         T repository = repositoryClass.cast(proxy);
         return repository;
      }
      catch(Exception e) {
         throw new RuntimeException(e);
      }
   }
}
