package com.page5of4.ms.examples.publisher.impl;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.page5of4.ms.Bus;

@Service
public class Publisher {
   private static final Logger logger = LoggerFactory.getLogger(Publisher.class);

   private final Bus bus;

   @Autowired
   public Publisher(Bus bus) {
      super();
      this.bus = bus;
   }

   @PostConstruct
   public void publish() {
      logger.info("Publishing!");
   }
}
