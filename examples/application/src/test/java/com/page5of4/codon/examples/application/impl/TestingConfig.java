package com.page5of4.codon.examples.application.impl;

import static org.mockito.Mockito.mock;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.page5of4.codon.Bus;

@Configuration
public class TestingConfig {
   @Bean
   public Bus bus() {
      return mock(Bus.class);
   }
}
