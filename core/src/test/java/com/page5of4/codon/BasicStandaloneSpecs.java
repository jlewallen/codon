package com.page5of4.codon;

import static org.fest.assertions.Assertions.assertThat;

import java.io.Serializable;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicLong;

import org.apache.camel.Exchange;
import org.apache.camel.Predicate;
import org.apache.camel.builder.AdviceWithRouteBuilder;
import org.apache.camel.builder.NotifyBuilder;
import org.apache.camel.model.ModelCamelContext;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.page5of4.codon.camel.CamelTransport;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(loader = TestLoader.class)
public class BasicStandaloneSpecs {
   @Autowired
   Bus bus;
   @Autowired
   CamelTransport transport;
   ModelCamelContext camelContext;

   @Before
   public void before() {
      camelContext = transport.getCamelContext();
   }

   @Test
   @DirtiesContext
   public void when_sending_message_locally() throws Exception {
      NotifyBuilder after = new NotifyBuilder(camelContext).whenExactlyCompleted(1).create();

      bus.sendLocal(new MessageAMessage("Jacob"));

      assertThat(after.matches(5L, TimeUnit.SECONDS)).isTrue();
   }

   @Test
   @DirtiesContext
   public void when_sending_message_locally_that_fails() throws Exception {
      RouteUtils.find(camelContext, "listen:.+MessageAMessage").adviceWith(camelContext, new AdviceWithRouteBuilder() {
         @Override
         public void configure() throws Exception {
            weaveAddLast().throwException(new RuntimeException("Fake connection error"));
         }
      });

      NotifyBuilder after = new NotifyBuilder(camelContext).whenExactlyFailed(5).create();

      bus.sendLocal(new MessageAMessage("Jacob"));

      assertThat(after.matches(5L, TimeUnit.SECONDS)).isTrue();
   }

   @Test
   @DirtiesContext
   public void when_sending_message_locally_that_fails_the_once() throws Exception {
      RouteUtils.find(camelContext, "listen:.+MessageAMessage").adviceWith(camelContext, new AdviceWithRouteBuilder() {
         @Override
         public void configure() throws Exception {
            weaveAddLast()
                  .choice()
                  .when(header("JMSRedelivered").isEqualTo("false"))
                  .throwException(new RuntimeException("Fake connection error"))
                  .end();
         }
      });

      NotifyBuilder after = new NotifyBuilder(camelContext).whenExactlyFailed(1).and().whenExactlyCompleted(1).create();

      bus.sendLocal(new MessageAMessage("Jacob"));

      assertThat(after.matches(5L, TimeUnit.SECONDS)).isTrue();
   }

   @Test
   @DirtiesContext
   public void when_sending_message_locally_that_fails_the_twice() throws Exception {
      RouteUtils.find(camelContext, "listen:.+MessageAMessage").adviceWith(camelContext, new AdviceWithRouteBuilder() {
         @Override
         public void configure() throws Exception {
            weaveAddLast()
                  .choice()
                  .when(new Predicate() {
                     private final AtomicLong counter = new AtomicLong();

                     @Override
                     public boolean matches(Exchange exchange) {
                        return counter.incrementAndGet() <= 2;
                     }
                  })
                  .throwException(new RuntimeException("Fake connection error"))
                  .end();
         }
      });

      NotifyBuilder after = new NotifyBuilder(camelContext).whenExactlyFailed(2).and().whenExactlyCompleted(1).create();

      bus.sendLocal(new MessageAMessage("Jacob"));

      assertThat(after.matches(5L, TimeUnit.SECONDS)).isTrue();
   }

   public static class MessageAMessage implements Serializable {
      private static final long serialVersionUID = 1L;
      private String name;

      public MessageAMessage(String name) {
         super();
         this.name = name;
      }

      public String getName() {
         return name;
      }

      public void setName(String name) {
         this.name = name;
      }
   }

   @MessageHandler
   public static class MessageAHandler {
      private static final Logger logger = LoggerFactory.getLogger(MessageHandler.class);

      @MessageHandler
      public void handle(MessageAMessage message, Exchange exchange) {
         logger.info("Entering");
      }
   }
}
