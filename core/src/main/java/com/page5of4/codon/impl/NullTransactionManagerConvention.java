package com.page5of4.codon.impl;

import javax.jms.ConnectionFactory;

import org.springframework.transaction.PlatformTransactionManager;

import com.page5of4.codon.EndpointAddress;

public class NullTransactionManagerConvention implements TransactionConvention {
   @Override
   public PlatformTransactionManager locate(EndpointAddress address, ConnectionFactory connectionFactory) {
      return null;
   }

   @Override
   public PlatformTransactionManager locate(EndpointAddress endpointAddress) {
      return null;
   }
}
