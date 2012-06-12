package com.page5of4.ms.camel;

import org.apache.camel.CamelContext;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.component.mock.MockComponent;
import org.apache.camel.component.mock.MockEndpoint;
import org.apache.camel.test.junit4.CamelTestSupport;
import org.junit.Test;

import com.page5of4.ms.Bus;
import com.page5of4.ms.BusBuilder;

public class PublishProcessorSpecs extends CamelTestSupport {
   private Bus bus;

   @Override
   protected RouteBuilder createRouteBuilder() throws Exception {
      return new RouteBuilder() {
         @Override
         public void configure() throws Exception {
            from("direct:incoming").process(new PublishProcessor(bus));
         }
      };
   }

   @Override
   protected CamelContext createCamelContext() throws Exception {
      CamelContext context = super.createCamelContext();
      context.addComponent("testing-server", new MockComponent());
      bus = BusBuilder.make(context).
            subscribed("mock:app1.java.lang.String", String.class).
            subscribed("mock:app2.java.lang.String", String.class).build();
      return context;
   }

   @Test
   public void when_processing_message() throws Exception {
      MockEndpoint mock1 = getMockEndpoint("mock:app1.java.lang.String");
      MockEndpoint mock2 = getMockEndpoint("mock:app2.java.lang.String");
      mock1.expectedMessageCount(1);
      mock1.allMessages().body().isEqualTo("Message body");
      mock1.allMessages().header(CamelTransport.MESSAGE_TYPE_KEY).isEqualTo("java.lang.String");

      mock2.expectedMessageCount(1);
      mock2.allMessages().body().isEqualTo("Message body");
      mock2.allMessages().header(CamelTransport.MESSAGE_TYPE_KEY).isEqualTo("java.lang.String");

      template.sendBody("direct:incoming", "Message body");

      mock1.assertIsSatisfied();
      mock2.assertIsSatisfied();
   }
}
