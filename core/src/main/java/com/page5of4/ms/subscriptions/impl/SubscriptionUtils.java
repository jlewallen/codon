package com.page5of4.ms.subscriptions.impl;

import java.util.ArrayList;
import java.util.List;

import com.page5of4.ms.EndpointAddress;
import com.page5of4.ms.subscriptions.Subscription;

public abstract class SubscriptionUtils {
   public static List<EndpointAddress> filter(List<Subscription> subscriptions, String messageType) {
      List<EndpointAddress> addresses = new ArrayList<EndpointAddress>();
      for(Subscription subscription : subscriptions) {
         if(subscription.getMessageType().equalsIgnoreCase(messageType)) {
            addresses.add(subscription.toEndpointAddress());
         }
      }
      return addresses;
   }
}
