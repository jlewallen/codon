package com.page5of4.codon.tests.integration;

import static org.fest.assertions.Assertions.assertThat;

import java.util.concurrent.TimeUnit;

import org.apache.camel.builder.NotifyBuilder;
import org.apache.camel.model.ModelCamelContext;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.page5of4.codon.Bus;
import com.page5of4.codon.tests.support.TestLoader;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(loader = TestLoader.class)
public class BasicStandaloneSpecs {
   @Autowired
   Bus bus;
   @Autowired
   ModelCamelContext camelContext;

   @Test
   @DirtiesContext
   public void when_sending_one_message_to_self() throws Exception {
      NotifyBuilder after = new NotifyBuilder(camelContext).whenExactlyCompleted(1).create();

      bus.sendLocal(new MessageAMessage("Jacob"));

      assertThat(after.matches(5L, TimeUnit.SECONDS)).isTrue();
   }

   @Test
   @DirtiesContext
   public void when_sending_ten_messages_to_self() throws Exception {
      NotifyBuilder after = new NotifyBuilder(camelContext).whenExactlyCompleted(10).create();

      for(int i = 0; i < 10; ++i) {
         bus.sendLocal(new MessageAMessage("Jacob"));
      }

      assertThat(after.matches(5L, TimeUnit.SECONDS)).isTrue();
   }
}
