package com.page5of4.codon.examples.subscriber.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

import com.page5of4.codon.Bus;
import com.page5of4.codon.config.OsgiBusConfig;
import com.page5of4.codon.examples.subscriber.impl.UserRegisteredHandler;

@Configuration
@Import(value = { EnvironmentConfig.class, OsgiBusConfig.class })
public class ExampleConfig {
   @Autowired
   private Bus bus;

   @Bean
   public UserRegisteredHandler userRegisteredHandler() {
      return new UserRegisteredHandler(bus);
   }

   /*
   @Bean(destroyMethod = "close")
   public UserTransactionManager atomikosTransactionManager() throws SystemException {
      UserTransactionManager bean = new UserTransactionManager();
      bean.setForceShutdown(false);
      bean.init();
      return bean;
   }

   @Bean
   public J2eeUserTransaction atomikosUserTransaction() throws SystemException {
      J2eeUserTransaction bean = new J2eeUserTransaction();
      bean.setTransactionTimeout(300);
      return bean;
   }

   @Bean
   public JtaTransactionManager transactionManager() throws SystemException {
      JtaTransactionManager bean = new JtaTransactionManager();
      bean.setTransactionManager(atomikosTransactionManager());
      bean.setUserTransaction(atomikosUserTransaction());
      bean.setAllowCustomIsolationLevels(true);
      return bean;
   }

   @Bean(destroyMethod = "close")
   DataSource dataSource() throws AtomikosSQLException {
      AtomikosDataSourceBean bean = new AtomikosDataSourceBean();
      bean.setUniqueResourceName(projectName + "Db");
      bean.setXaDataSourceClassName(xaDataSource);
      bean.setMaxPoolSize(dbMaxPoolSize);
      bean.setMinPoolSize(dbMinPoolSize);
      Properties xaProperties = new Properties();
      xaProperties.put("URL", databaseUrl);
      xaProperties.put("user", databaseUsername);
      xaProperties.put("password", databasePassword);
      bean.setXaProperties(xaProperties);
      bean.init();
      return bean;
   }

   @Value("${database.url:}")
   protected void setDatabaseUrl(String databaseUrl) {
      if(StringUtils.trimToNull(databaseUrl) == null) {
         this.databaseUrl = "jdbc:h2:mem:" + projectName + ";MODE=Oracle;DB_CLOSE_DELAY=-1;MVCC=TRUE;AUTOCOMMIT=FALSE;TRACE_LEVEL_SYSTEM_OUT=1";
      }
      else {
         this.databaseUrl = databaseUrl;
      }
   }

   @Bean
   @DependsOn(value = { DefaultSpringConfig.SPRING_AUTOWIRER, "migrations" })
   EntityManagerFactory entityManagerFactory() throws AtomikosSQLException {
      LocalContainerEntityManagerFactoryBean bean = new LocalContainerEntityManagerFactoryBean();
      bean.setPersistenceProviderClass(HibernatePersistence.class);
      bean.setDataSource(dataSource());
      bean.setJpaVendorAdapter(new HibernateJpaVendorAdapter());
      Properties jpaProperties = getHibernateProperties();
      jpaProperties.put(HIBERNATE_TRANSACTION_JTA_PLATFORM, SpringJtaPlatform.class.getName().toString());
      bean.setJpaProperties(jpaProperties);
      bean.setPackagesToScan(packagesToScan);
      bean.setPersistenceUnitName(PERSISTENCE_UNIT);
      bean.afterPropertiesSet();
      return bean.getObject();
   }
   */
}
