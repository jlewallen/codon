package com.page5of4.ms.examples.subscriber.config;

import java.util.Properties;

import javax.persistence.EntityManagerFactory;

import org.apache.commons.dbcp.BasicDataSource;
import org.hibernate.ejb.HibernatePersistence;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.util.StringUtils;

import com.page5of4.ms.Bus;
import com.page5of4.ms.config.CoreConfig;

@Configuration
@Import(value = { EnvironmentConfig.class, CoreConfig.class })
public class ExampleConfig {
   @Autowired
   private Bus bus;

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
   */

   @Bean
   public BasicDataSource dataSource() {
      BasicDataSource dataSource = new BasicDataSource();
      dataSource.setDriverClassName(driverClassName);
      dataSource.setUrl(databaseUrl);
      dataSource.setUsername(databaseUsername);
      dataSource.setPassword(databasePassword);
      return dataSource;
   }

   @Value("${database.url:}")
   protected void setDatabaseUrl(String databaseUrl) {
      if(!StringUtils.hasText(databaseUrl)) {
         this.databaseUrl = "jdbc:h2:mem:" + "gnucash" + ";MODE=Oracle;DB_CLOSE_DELAY=-1;MVCC=TRUE;AUTOCOMMIT=FALSE;TRACE_LEVEL_SYSTEM_OUT=1";
         this.databaseUrl = "jdbc:h2:" + "~/gnucash" + ";MODE=Oracle;DB_CLOSE_DELAY=-1;MVCC=TRUE;AUTOCOMMIT=FALSE;TRACE_LEVEL_SYSTEM_OUT=1";
      }
      else {
         this.databaseUrl = databaseUrl;
      }
   }

   @Value("${database.username:sa}")
   private String databaseUsername;

   @Value("${database.password:}")
   private String databasePassword;

   private String databaseUrl;

   @Value("${database.driverClassName:org.h2.Driver}")
   private String driverClassName;

   @Value("${hiberante.naming_strategy:org.hibernate.cfg.ImprovedNamingStrategy}")
   private String namingStrategy;

   @Value("${hibernate.dialect:org.hibernate.dialect.Oracle10gDialect}")
   private String hibernateDialect;

   @Value("${hibernate.default_schema:public}")
   private String defaultSchema;

   @Bean
   public EntityManagerFactory entityManagerFactory() {
      LocalContainerEntityManagerFactoryBean entityManagerFactory = new LocalContainerEntityManagerFactoryBean();
      entityManagerFactory.setPersistenceUnitName("persistenceUnit");
      entityManagerFactory.setPersistenceProviderClass(HibernatePersistence.class);
      entityManagerFactory.setDataSource(dataSource());
      entityManagerFactory.setJpaVendorAdapter(new HibernateJpaVendorAdapter());
      Properties properties = new Properties();
      properties.put("hibernate.dialect", hibernateDialect);
      properties.put("hibernate.format_sql", "false");
      properties.put("hibernate.use_sql_comments", "true");
      properties.put("hibernate.default_schema", defaultSchema);
      properties.put("hibernate.ejb.naming_strategy", namingStrategy);
      entityManagerFactory.setJpaProperties(properties);
      entityManagerFactory.afterPropertiesSet();
      return entityManagerFactory.getObject();
   }
}
