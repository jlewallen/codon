package com.page5of4.codon.tests.support;

import javax.transaction.SystemException;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.atomikos.icatch.jta.J2eeUserTransaction;
import com.atomikos.icatch.jta.UserTransactionManager;

@Configuration
public class AtomikosTransactionManagerConfig {
   @Bean(initMethod = "init", destroyMethod = "close")
   public UserTransactionManager atomikosTransactionManager() throws SystemException {
      UserTransactionManager bean = new UserTransactionManager();
      bean.setForceShutdown(false);
      return bean;
   }

   @Bean
   public J2eeUserTransaction atomikosUserTransaction() throws SystemException {
      J2eeUserTransaction bean = new J2eeUserTransaction();
      bean.setTransactionTimeout(300);
      return bean;
   }
}
