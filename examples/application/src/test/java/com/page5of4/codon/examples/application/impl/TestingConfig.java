package com.page5of4.codon.examples.application.impl;

import static org.mockito.Mockito.mock;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.page5of4.codon.Bus;
import com.page5of4.codon.persistence.memory.MemoryRepositoryFactory;
import com.page5of4.codon.useful.repositories.RepositoryFactory;

@Configuration
public class TestingConfig {
   @Bean
   public Bus bus() {
      return mock(Bus.class);
   }

   @Bean
   public RepositoryFactory repositoryFactory() {
      return new MemoryRepositoryFactory();
   }
}
