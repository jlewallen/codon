package com.page5of4.codon.tests.integration;

import static org.fest.assertions.Assertions.assertThat;

import java.util.concurrent.TimeUnit;

import org.apache.camel.builder.NotifyBuilder;
import org.apache.camel.model.ModelCamelContext;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.page5of4.codon.Bus;
import com.page5of4.codon.PropertiesConfiguration;
import com.page5of4.codon.subscriptions.SubscriptionStorage;
import com.page5of4.codon.tests.support.TestLoader;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(loader = TestLoader.class)
public class SubscriptionSpecs {
   @Autowired
   Bus bus;
   @Autowired
   SubscriptionStorage subscriptionStorage;
   @Autowired
   PropertiesConfiguration configuration;
   @Autowired
   ModelCamelContext camelContext;

   @Before
   public void before() {
      configuration.clear();
      configuration.put("bus.owner." + MessageAMessage.class.getName(), configuration.getLocalComponentName() + ":" + configuration.getApplicationName() + ".{messageType}");
   }

   @Test
   @DirtiesContext
   public void when_receiving_subscription_its_stored() {
      NotifyBuilder notify = new NotifyBuilder(camelContext).whenCompleted(1).create();

      bus.subscribe(MessageAMessage.class);

      assertThat(notify.matches(5L, TimeUnit.SECONDS)).isTrue();

      assertThat(subscriptionStorage.findAllSubscriptions().size()).isEqualTo(1);
      assertThat(subscriptionStorage.findAllSubscriptions().get(0).getMessageType()).isEqualTo(MessageAMessage.class.getName());
   }

   @Test
   @DirtiesContext
   public void when_receiving_unsubscribe_its_stored() {
      NotifyBuilder notify = new NotifyBuilder(camelContext).whenCompleted(2).create();

      bus.subscribe(MessageAMessage.class);
      bus.unsubscribe(MessageAMessage.class);

      assertThat(notify.matches(5L, TimeUnit.SECONDS)).isTrue();

      assertThat(subscriptionStorage.findAllSubscriptions()).isEmpty();
   }
}
