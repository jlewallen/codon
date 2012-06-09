package com.page5of4.ms.subscriptions;

import java.util.Collection;
import java.util.List;

public interface SubscriptionStorage {
   List<Subscription> findAllSubscriptions();

   void addSubscriptions(Collection<Subscription> subscriptions);

   void removeSubscriptions(Collection<Subscription> subscriptions);
}
