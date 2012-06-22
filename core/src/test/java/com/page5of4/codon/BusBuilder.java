package com.page5of4.codon;

import java.util.Arrays;

import org.apache.camel.CamelContext;

import com.page5of4.codon.camel.AlwaysFailComponentResolver;
import com.page5of4.codon.camel.CamelTransport;
import com.page5of4.codon.camel.ComponentResolver;
import com.page5of4.codon.camel.InvokeHandlerProcessor;
import com.page5of4.codon.impl.ApplicationContextResolver;
import com.page5of4.codon.impl.DefaultBus;
import com.page5of4.codon.impl.HandlerRegistry;
import com.page5of4.codon.impl.InstanceResolver;
import com.page5of4.codon.impl.MessageUtils;
import com.page5of4.codon.impl.TopologyConfiguration;
import com.page5of4.codon.subscriptions.Subscription;
import com.page5of4.codon.subscriptions.impl.InMemorySubscriptionStorage;

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
