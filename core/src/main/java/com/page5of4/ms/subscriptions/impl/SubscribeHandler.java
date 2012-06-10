package com.page5of4.ms.subscriptions.impl;

import java.util.Collections;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.page5of4.ms.AutomaticallySubscribe;
import com.page5of4.ms.MessageHandler;
import com.page5of4.ms.subscriptions.Subscription;
import com.page5of4.ms.subscriptions.SubscriptionStorage;
import com.page5of4.ms.subscriptions.messages.SubscribeMessage;

@Service
@MessageHandler(autoSubscribe = AutomaticallySubscribe.NEVER)
public class SubscribeHandler {
   private static final Logger logger = LoggerFactory.getLogger(SubscribeHandler.class);
   private final SubscriptionStorage storage;

   @Autowired
   public SubscribeHandler(SubscriptionStorage storage) {
      super();
      this.storage = storage;
   }

   @MessageHandler
   public void handle(SubscribeMessage message) {
      logger.info("Passing {} to {}", message, storage);
      storage.addSubscriptions(Collections.singleton(new Subscription(message.getAddress(), message.getMessageType())));
   }
}
