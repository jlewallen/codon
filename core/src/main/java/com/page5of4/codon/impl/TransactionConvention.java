package com.page5of4.codon.impl;

import javax.jms.ConnectionFactory;

import org.springframework.transaction.PlatformTransactionManager;

import com.page5of4.codon.EndpointAddress;

public interface TransactionConvention {

   PlatformTransactionManager locate(EndpointAddress address, ConnectionFactory connectionFactory);

   PlatformTransactionManager locate(EndpointAddress endpointAddress);

}
