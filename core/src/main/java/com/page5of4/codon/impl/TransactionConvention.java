package com.page5of4.codon.impl;

import javax.jms.ConnectionFactory;

import org.springframework.transaction.PlatformTransactionManager;

public interface TransactionConvention {

   PlatformTransactionManager locate(String name, ConnectionFactory connectionFactory);

   PlatformTransactionManager locate(String name);

}
