package com.page5of4.codon.subscriptions.impl;

import java.util.Collections;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.page5of4.codon.AutomaticallySubscribe;
import com.page5of4.codon.MessageHandler;
import com.page5of4.codon.subscriptions.Subscription;
import com.page5of4.codon.subscriptions.SubscriptionStorage;
import com.page5of4.codon.subscriptions.messages.SubscribeMessage;

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
