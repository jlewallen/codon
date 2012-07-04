package com.page5of4.codon.impl;

import javax.jms.ConnectionFactory;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.PlatformTransactionManager;

public class JtaTransactionManagerConvention implements TransactionConvention {
   private static final Logger logger = LoggerFactory.getLogger(JtaTransactionManagerConvention.class);
   private final PlatformTransactionManager platformTransactionManager;

   @Autowired
   public JtaTransactionManagerConvention(PlatformTransactionManager platformTransactionManager) {
      super();
      this.platformTransactionManager = platformTransactionManager;
   }

   @Override
   public PlatformTransactionManager locate(String name, ConnectionFactory connectionFactory) {
      return platformTransactionManager;
   }

   @Override
   public PlatformTransactionManager locate(String name) {
      return platformTransactionManager;
   }
}
