package com.page5of4.codon.examples.env;

import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import javax.sql.DataSource;

import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceRegistration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.osgi.service.exporter.support.AutoExport;
import org.springframework.osgi.service.exporter.support.ExportContextClassLoader;
import org.springframework.osgi.service.exporter.support.OsgiServiceFactoryBean;

import com.atomikos.jdbc.AtomikosDataSourceBean;
import com.atomikos.jdbc.AtomikosSQLException;

@Configuration
public class DevConfig {
   @Value("${database.xa_datasource_class:org.h2.jdbcx.JdbcDataSource}")
   private String xaDataSource;

   @Value("${database.max_pool_size:50}")
   private int dbMaxPoolSize;

   @Value("${database.min_pool_size:20}")
   private int dbMinPoolSize;

   @Value("${database.username:sa}")
   private String databaseUsername;

   @Value("${database.password:}")
   private String databasePassword;

   @Value("jdbc:h2:mem:codon-example;MODE=Oracle;DB_CLOSE_DELAY=-1;MVCC=TRUE;AUTOCOMMIT=FALSE;TRACE_LEVEL_SYSTEM_OUT=1")
   private String databaseUrl;

   @Autowired
   private BundleContext bundleContext;

   @Bean(destroyMethod = "close")
   public DataSource dataSource() throws AtomikosSQLException {
      AtomikosDataSourceBean bean = new AtomikosDataSourceBean();
      bean.setUniqueResourceName("codon-example");
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

   @Bean
   public ServiceRegistration dataSourceRegistration() throws Exception {
      Map<String, String> serviceProperties = new HashMap<String, String>();
      serviceProperties.put("name", "codon-example");
      OsgiServiceFactoryBean bean = new OsgiServiceFactoryBean();
      bean.setBundleContext(bundleContext);
      bean.setTarget(dataSource());
      bean.setServiceProperties(serviceProperties);
      bean.setAutoExport(AutoExport.INTERFACES);
      bean.setContextClassLoader(ExportContextClassLoader.UNMANAGED);
      bean.afterPropertiesSet();
      return (ServiceRegistration)bean.getObject();
   }
}
