package com.page5of4.codon.config;

import java.util.List;

import javax.transaction.TransactionManager;
import javax.transaction.UserTransaction;

import org.apache.camel.spi.TransactedPolicy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.jta.JtaTransactionManager;

import com.page5of4.codon.BusConfiguration;
import com.page5of4.codon.BusException;
import com.page5of4.codon.camel.AutomaticTransactionPolicy;
import com.page5of4.codon.impl.JtaTransactionManagerConvention;
import com.page5of4.codon.impl.TransactionConvention;

@Configuration
public class JtaTransactionConventionConfig {
   @Autowired
   private BusConfiguration configuration;
   @Autowired
   private TransactionManager transactionManager;
   @Autowired
   private List<UserTransaction> userTransactions;

   @Bean
   public TransactionConvention transactionConvention() {
      return new JtaTransactionManagerConvention(transactionManager());
   }

   @Bean
   public TransactedPolicy transactedPolicy() {
      return new AutomaticTransactionPolicy(configuration, transactionConvention());
   }

   @Bean
   public JtaTransactionManager transactionManager() {
      JtaTransactionManager bean = new JtaTransactionManager();
      bean.setTransactionManager(transactionManager);
      bean.setUserTransaction(getUserTransaction());
      bean.setAllowCustomIsolationLevels(true);
      return bean;
   }

   private UserTransaction getUserTransaction() {
      if(userTransactions.size() == 1) {
         return userTransactions.get(0);
      }
      for(UserTransaction transaction : userTransactions) {
         if(!transaction.equals(transactionManager)) {
            return transaction;
         }
      }
      throw new BusException("Unable to find javax.transaction.UserTransaction");
   }
}
