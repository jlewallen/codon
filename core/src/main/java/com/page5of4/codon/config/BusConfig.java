package com.page5of4.codon.config;

import org.apache.camel.spring.SpringCamelContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

import com.page5of4.codon.Bus;
import com.page5of4.codon.BusException;
import com.page5of4.codon.HandlerRegistry;
import com.page5of4.codon.Transport;
import com.page5of4.codon.camel.ActiveMQComponentResolver;
import com.page5of4.codon.camel.CamelTransport;
import com.page5of4.codon.camel.InvokeHandlerProcessor;
import com.page5of4.codon.impl.ApplicationContextResolver;
import com.page5of4.codon.impl.BusContextProvider;
import com.page5of4.codon.impl.DefaultBus;

@Configuration
@Import(value = { CoreConfig.class, TransactionConfig.class })
public class BusConfig {
   @Autowired
   private ApplicationContext applicationContext;
   @Autowired
   private HandlerRegistry handlerRegistry;
   @Autowired
   private BusContextProvider contextProvider;

   @Bean
   public Bus bus() {
      return new DefaultBus(contextProvider, transport());
   }

   @Bean
   public Transport transport() {
      try {
         ActiveMQComponentResolver resolver = new ActiveMQComponentResolver();
         SpringCamelContext camelContext = new SpringCamelContext(applicationContext);
         camelContext.afterPropertiesSet();
         return new CamelTransport(camelContext, resolver, invokeHandlerProcessor());
      }
      catch(Exception e) {
         throw new BusException(e);
      }
   }

   @Bean
   public InvokeHandlerProcessor invokeHandlerProcessor() {
      return new InvokeHandlerProcessor(handlerRegistry, new ApplicationContextResolver(applicationContext));
   }
}