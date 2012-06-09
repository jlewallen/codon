package com.page5of4.ms.examples.subscriber.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.page5of4.ms.Bus;
import com.page5of4.ms.MessageHandler;
import com.page5of4.ms.examples.messages.UserRegisteredMessage;

@MessageHandler
public class UserRegisteredHandler {
   private static final Logger logger = LoggerFactory.getLogger(UserRegisteredHandler.class);
   private final Bus bus;

   @Autowired
   public UserRegisteredHandler(Bus bus) {
      super();
      this.bus = bus;
   }

   @MessageHandler
   public void handle(UserRegisteredMessage message) {
      logger.info("Received {}", message);
   }
}
