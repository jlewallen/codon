package com.page5of4.ms.camel;

import org.apache.camel.CamelContext;
import org.apache.camel.ProducerTemplate;
import org.apache.camel.impl.DefaultCamelContext;
import org.junit.Test;

import com.page5of4.ms.subscriptions.messages.SubscribeMessage;

public class XmlWireFormatterSpecs {
   @Test
   public void when_marshalling() {
      try {
         CamelContext context = new DefaultCamelContext();
         ProducerTemplate producer = context.createProducerTemplate();
         OutgoingProcessor outgoing = new OutgoingProcessor(new SubscribeMessage("Jacob", "Lewallen"));
         producer.send("mock:a", outgoing);
      }
      catch(Exception e) {
         System.out.println(e);
      }
   }

   public static class TestMessage {
      private final String name;

      public String getName() {
         return name;
      }

      public TestMessage(String name) {
         super();
         this.name = name;
      }
   }
}
