package com.page5of4.ms.camel;

import org.apache.camel.CamelContext;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.component.mock.MockComponent;
import org.apache.camel.component.mock.MockEndpoint;
import org.apache.camel.test.junit4.CamelTestSupport;
import org.junit.Test;

import com.page5of4.ms.Bus;
import com.page5of4.ms.BusBuilder;

public class SendLocalProcessorSpecs extends CamelTestSupport {
   private Bus bus;

   @Override
   protected RouteBuilder createRouteBuilder() throws Exception {
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
      bus = BusBuilder.make(context).build();
      return context;
   }

   @Test
   public void when_processing_message() throws Exception {
      MockEndpoint mock = getMockEndpoint("testing-server:test.java.lang.String");
      mock.expectedMessageCount(1);
      mock.allMessages().body().isEqualTo("Message body");
      mock.allMessages().header(CamelTransport.MESSAGE_TYPE_KEY).isEqualTo("java.lang.String");

      template.sendBody("direct:incoming", "Message body");

      mock.assertIsSatisfied();
   }
}
