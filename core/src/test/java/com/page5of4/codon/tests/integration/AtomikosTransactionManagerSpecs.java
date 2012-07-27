package com.page5of4.codon.tests.integration;

import org.apache.activemq.camel.component.ActiveMQComponent;
import org.apache.activemq.spring.ActiveMQXAConnectionFactory;
import org.apache.camel.component.jms.JmsComponent;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.ContextLoader;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.page5of4.codon.Bus;
import com.page5of4.codon.config.InMemorySubscriptionStorageConfig;
import com.page5of4.codon.config.JtaTransactionConventionConfig;
import com.page5of4.codon.config.PublisherConfig;
import com.page5of4.codon.config.StandaloneConfig;
import com.page5of4.codon.tests.integration.AtomikosTransactionManagerSpecs.AtomikosTestLoader;
import com.page5of4.codon.tests.support.AtomikosTransactionManagerConfig;
import com.page5of4.codon.tests.support.EmbeddedActiveMqBrokerConfig;
import com.page5of4.codon.tests.support.ExposeCamelContextConfig;
import com.page5of4.codon.tests.support.SimpleBusConfigurationConfig;
import com.page5of4.codon.tests.support.TestHandlersConfig;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(loader = AtomikosTestLoader.class)
public class AtomikosTransactionManagerSpecs {

   @Autowired
   Bus bus;

   @Test
   public void when_handling_one_message_then_publishing_to_another_then_failing_should_rollback() {
      bus.sendLocal(new MessageAMessage("Jacob"));
   }

   public static class AtomikosTestLoader implements ContextLoader {
      @Override
      public String[] processLocations(Class<?> clazz, String... locations) {
         return locations;
      }

      @Override
      public ApplicationContext loadContext(String... locations) throws Exception {
         AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext();
         applicationContext.register(EmbeddedActiveMqBrokerConfig.class);
         applicationContext.register(SimpleBusConfigurationConfig.class);
         applicationContext.register(StandaloneConfig.class);
         applicationContext.register(PublisherConfig.class);
         applicationContext.register(ExposeCamelContextConfig.class);
         applicationContext.register(InMemorySubscriptionStorageConfig.class);
         applicationContext.register(AtomikosTransactionManagerConfig.class);
         applicationContext.register(JtaTransactionConventionConfig.class);
         applicationContext.register(TestHandlersConfig.class);
         applicationContext.register(XaActiveMqConfig.class);
         applicationContext.refresh();
         applicationContext.registerShutdownHook();
         return applicationContext;
      }
   }

   @Configuration
   public static class XaActiveMqConfig {
      @Bean(name = "activemq")
      @Scope("prototype")
      public JmsComponent activemq() {
         ActiveMQComponent component = new ActiveMQComponent();
         component.setConnectionFactory(new ActiveMQXAConnectionFactory());
         return component;
      }
   }
}
