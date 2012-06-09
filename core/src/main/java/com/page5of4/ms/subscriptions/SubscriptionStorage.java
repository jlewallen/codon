package com.page5of4.ms.subscriptions;

import java.util.Collection;
import java.util.List;

import com.page5of4.ms.EndpointAddress;

public interface SubscriptionStorage {
   List<Subscription> findAllSubscriptions();

   List<EndpointAddress> findAllSubscribers(String messageType);

   void addSubscriptions(Collection<Subscription> subscriptions);

   void removeSubscriptions(Collection<Subscription> subscriptions);
}
