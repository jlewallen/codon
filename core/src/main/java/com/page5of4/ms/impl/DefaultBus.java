package com.page5of4.ms.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.page5of4.ms.Bus;

@Service
public class DefaultBus implements Bus {
   private static final Logger logger = LoggerFactory.getLogger(DefaultBus.class);

   public DefaultBus() {
      logger.info("Constructed");
   }

   @Override
   public <T> void publish(T message) {

   }

   @Override
   public <T> void send(T message) {

   }

   @Override
   public <T> void sendLocal(T message) {

   }
}
