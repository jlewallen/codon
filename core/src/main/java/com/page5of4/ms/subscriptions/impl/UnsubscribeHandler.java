package com.page5of4.ms.subscriptions.impl;

import java.util.Collections;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.page5of4.ms.AutomaticallySubscribe;
import com.page5of4.ms.MessageHandler;
import com.page5of4.ms.subscriptions.Subscription;
import com.page5of4.ms.subscriptions.SubscriptionStorage;
import com.page5of4.ms.subscriptions.messages.UnsubscribeMessage;

@MessageHandler(autoSubscribe = AutomaticallySubscribe.NEVER)
public class UnsubscribeHandler {
   private static final Logger logger = LoggerFactory.getLogger(UnsubscribeHandler.class);
   private final SubscriptionStorage storage;

   @Autowired
   public UnsubscribeHandler(SubscriptionStorage storage) {
      super();
      this.storage = storage;
   }

   @MessageHandler
   public void handle(UnsubscribeMessage message) {
      logger.info("Passing {} to {}", message, storage);
      storage.removeSubscriptions(Collections.singleton(new Subscription(message.getAddress(), message.getMessageType())));
   }
}
