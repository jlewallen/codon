package com.page5of4.codon.impl;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import javax.jms.ConnectionFactory;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jms.connection.JmsTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;

import com.page5of4.codon.BusException;
import com.page5of4.codon.EndpointAddress;

public class JmsTransactionManagerConvention implements TransactionConvention {
   private static final Logger logger = LoggerFactory.getLogger(JmsTransactionManagerConvention.class);
   private final Map<String, PlatformTransactionManager> cache = new ConcurrentHashMap<String, PlatformTransactionManager>();

   @Override
   public PlatformTransactionManager locate(EndpointAddress address, ConnectionFactory connectionFactory) {
      String key = getKey(address);
      if(cache.containsKey(key)) {
         logger.info("Returning cached PlatformTransactionManager for '{}' ('{}')", address, key);
         return cache.get(key);
      }
      logger.info("Creating PlatformTransactionManager for '{}'", address);
      JmsTransactionManager manager = new JmsTransactionManager(connectionFactory);
      cache.put(key, manager);
      return manager;
   }

   @Override
   public PlatformTransactionManager locate(EndpointAddress address) {
      String key = getKey(address);
      if(cache.containsKey(key)) {
         logger.info("Returning cached PlatformTransactionManager for '{}' ('{}')", address, key);
         return cache.get(key);
      }
      throw new BusException(String.format("No PlatformTransactionManager available for '%s'", address));
   }

   public String getKey(EndpointAddress address) {
      return address.getHost();
   }
}
