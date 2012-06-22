package com.page5of4.codon.subscriptions.impl;

import java.util.Collections;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.page5of4.codon.AutomaticallySubscribe;
import com.page5of4.codon.MessageHandler;
import com.page5of4.codon.subscriptions.Subscription;
import com.page5of4.codon.subscriptions.SubscriptionStorage;
import com.page5of4.codon.subscriptions.messages.UnsubscribeMessage;

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
