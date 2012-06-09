package com.page5of4.ms.subscriptions.impl;

import java.util.Collections;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.page5of4.ms.MessageHandler;
import com.page5of4.ms.subscriptions.Subscription;
import com.page5of4.ms.subscriptions.SubscriptionStorage;
import com.page5of4.ms.subscriptions.messages.UnsubscribeMessage;

@Service
public class UnsubscribeHandler implements MessageHandler<UnsubscribeMessage> {
   private final SubscriptionStorage storage;

   @Autowired
   public UnsubscribeHandler(SubscriptionStorage storage) {
      super();
      this.storage = storage;
   }

   @Override
   public void handle(UnsubscribeMessage message) {
      storage.removeSubscriptions(Collections.singleton(new Subscription(message.getAddress(), message.getMessageType())));
   }
}
