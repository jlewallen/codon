package com.page5of4.ms.subscriptions.impl;

import java.util.Collections;

import org.springframework.beans.factory.annotation.Autowired;

import com.page5of4.ms.MessageHandler;
import com.page5of4.ms.subscriptions.Subscription;
import com.page5of4.ms.subscriptions.SubscriptionStorage;
import com.page5of4.ms.subscriptions.messages.UnsubscribeMessage;

@MessageHandler
public class UnsubscribeHandler {
   private final SubscriptionStorage storage;

   @Autowired
   public UnsubscribeHandler(SubscriptionStorage storage) {
      super();
      this.storage = storage;
   }

   @MessageHandler
   public void handle(UnsubscribeMessage message) {
      storage.removeSubscriptions(Collections.singleton(new Subscription(message.getAddress(), message.getMessageType())));
   }
}
