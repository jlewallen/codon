package com.page5of4.ms.impl;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

import org.apache.camel.CamelContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class Bootstrap {
   private static final Logger logger = LoggerFactory.getLogger(Bootstrap.class);
   private final CamelContext camelContext;

   @Autowired
   public Bootstrap(CamelContext camelContext) {
      super();
      this.camelContext = camelContext;
   }

   @PostConstruct
   public void startup() {
      logger.info("Starting");
      logger.info("Ready");
   }

   @PreDestroy
   public void shutdown() {
      logger.info("Stopped");
   }
}
