package com.page5of4.codon.config;

import org.apache.camel.spi.TransactedPolicy;
import org.apache.camel.spring.spi.SpringTransactionPolicy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.PlatformTransactionManager;

@Configuration
public class TransactionPolicyConfig {
   public static final String TRANSACTION_POLICY_NAME = "PROPAGATION_REQUIRED";

   @Autowired
   private PlatformTransactionManager platformTransactionManager;

   @Bean(name = TRANSACTION_POLICY_NAME)
   public TransactedPolicy propagationRequired() {
      SpringTransactionPolicy policy = new SpringTransactionPolicy();
      policy.setTransactionManager(platformTransactionManager);
      policy.setPropagationBehaviorName(TRANSACTION_POLICY_NAME);
      return policy;
   }
}
