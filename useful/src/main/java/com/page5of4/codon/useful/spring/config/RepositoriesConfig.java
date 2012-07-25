package com.page5of4.codon.useful.spring.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.page5of4.codon.useful.repositories.DefaultRepositoryProvider;
import com.page5of4.codon.useful.repositories.RepositoryFactory;

@Configuration
public class RepositoriesConfig {
   @Autowired
   private RepositoryFactory repositoryFactory;

   @Bean
   public RepositoryFactoryPostProcessor repositoryFactoryPostProcessor() {
      return new RepositoryFactoryPostProcessor(new DefaultRepositoryProvider());
   }
}
