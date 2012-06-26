package com.page5of4.codon.config;

import org.apache.camel.spi.TransactedPolicy;
import org.apache.camel.spring.spi.SpringTransactionPolicy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.PlatformTransactionManager;

import com.page5of4.codon.camel.EmptyTransactionPolicy;

@Configuration
public class TransactionConfig {
   public static final String TRANSACTION_POLICY_NAME = "PROPAGATION_REQUIRED";

   @Autowired(required = false)
   private PlatformTransactionManager platformTransactionManager;

   @Bean(name = TRANSACTION_POLICY_NAME)
   public TransactedPolicy propagationRequired() {
      if(platformTransactionManager == null) return new EmptyTransactionPolicy();
      SpringTransactionPolicy policy = new SpringTransactionPolicy();
      policy.setTransactionManager(platformTransactionManager);
      policy.setPropagationBehaviorName(TRANSACTION_POLICY_NAME);
      return policy;
   }
}