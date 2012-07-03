package com.page5of4.codon.config;

import org.apache.camel.spi.TransactedPolicy;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.page5of4.codon.camel.AutomaticTransactionPolicy;
import com.page5of4.codon.impl.JmsTransactionManagerConvention;
import com.page5of4.codon.impl.TransactionConvention;

@Configuration
public class TransactionPolicyConfig {
   @Bean
   public TransactionConvention transactionConvention() {
      return new JmsTransactionManagerConvention();
   }

   @Bean
   public TransactedPolicy transactedPolicy() {
      return new AutomaticTransactionPolicy(transactionConvention());
   }
}
