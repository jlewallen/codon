package com.page5of4.ms;

import java.util.Arrays;

import org.apache.camel.CamelContext;

import com.page5of4.ms.camel.AlwaysFailComponentResolver;
import com.page5of4.ms.camel.CamelTransport;
import com.page5of4.ms.camel.ComponentResolver;
import com.page5of4.ms.camel.InvokeHandlerProcessor;
import com.page5of4.ms.impl.ApplicationContextResolver;
import com.page5of4.ms.impl.DefaultBus;
import com.page5of4.ms.impl.HandlerRegistry;
import com.page5of4.ms.impl.InstanceResolver;
import com.page5of4.ms.impl.MessageUtils;
import com.page5of4.ms.impl.TopologyConfiguration;
import com.page5of4.ms.subscriptions.Subscription;
import com.page5of4.ms.subscriptions.impl.InMemorySubscriptionStorage;

public class BusBuilder {
   private final CamelContext camelContext;
   private final HandlerRegistry handlerRegistry = new HandlerRegistry(null);
   private final InstanceResolver resolver = new ApplicationContextResolver(null);
   private final ComponentResolver template = new AlwaysFailComponentResolver();
   private final InMemorySubscriptionStorage subscriptionStorage = new InMemorySubscriptionStorage();

   public BusBuilder(CamelContext camelContext) {
      this.camelContext = camelContext;
   }

   public static BusBuilder make(CamelContext camelContext) {
      return new BusBuilder(camelContext);
   }

   public BusBuilder subscribed(String uri, Class<?> messageType) {
      subscriptionStorage.addSubscriptions(Arrays.asList(new Subscription(uri, MessageUtils.getMessageType(messageType))));
      return this;
   }

   public Bus build() {
      return new DefaultBus(new TopologyConfiguration(new PropertiesConfiguration("test", "testing-server")), new CamelTransport(camelContext, template, new InvokeHandlerProcessor(handlerRegistry, resolver)), subscriptionStorage);
   }
}
