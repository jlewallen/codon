package com.page5of4.codon.impl;

import javax.jms.ConnectionFactory;

import org.springframework.transaction.PlatformTransactionManager;

public class NullTransactionManagerConvention implements TransactionConvention {
   @Override
   public PlatformTransactionManager locate(String name, ConnectionFactory connectionFactory) {
      return null;
   }

   @Override
   public PlatformTransactionManager locate(String name) {
      return null;
   }
}
