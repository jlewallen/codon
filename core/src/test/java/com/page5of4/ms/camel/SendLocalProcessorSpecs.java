package com.page5of4.ms.camel;

import org.apache.camel.CamelContext;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.component.mock.MockComponent;
import org.apache.camel.component.mock.MockEndpoint;
import org.apache.camel.test.junit4.CamelTestSupport;
import org.junit.Test;

import com.page5of4.ms.Bus;
import com.page5of4.ms.BusConfiguration;
import com.page5of4.ms.impl.ApplicationContextResolver;
import com.page5of4.ms.impl.DefaultBus;
import com.page5of4.ms.impl.HandlerRegistry;
import com.page5of4.ms.impl.InstanceResolver;
import com.page5of4.ms.impl.TopologyConfiguration;
import com.page5of4.ms.subscriptions.impl.InMemorySubscriptionStorage;

public class SendLocalProcessorSpecs extends CamelTestSupport {
   @Override
   protected RouteBuilder createRouteBuilder() throws Exception {
      HandlerRegistry handlerRegistry = new HandlerRegistry(null);
      InstanceResolver resolver = new ApplicationContextResolver(null);
      final Bus bus = new DefaultBus(new TopologyConfiguration(new BusConfiguration("test", "testing-server")), new CamelTransport(context, new ActiveMQComponentResolver(), new InvokeHandlerProcessor(handlerRegistry, resolver)), new InMemorySubscriptionStorage());
      return new RouteBuilder() {
         @Override
         public void configure() throws Exception {
            from("direct:incoming").process(new SendLocalProcessor(bus));
         }
      };
   }

   @Override
   protected CamelContext createCamelContext() throws Exception {
      CamelContext context = super.createCamelContext();
      context.addComponent("testing-server", new MockComponent());
      return context;
   }

   @Test
   public void when_processing_message() throws Exception {
      MockEndpoint mock = getMockEndpoint("testing-server:test.java.lang.String");
      mock.expectedMessageCount(1);
      mock.expectedBodiesReceived("Message body");

      template.sendBody("direct:incoming", "Message body");

      mock.assertIsSatisfied();
   }
}
