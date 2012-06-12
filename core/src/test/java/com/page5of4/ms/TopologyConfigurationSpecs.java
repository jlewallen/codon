package com.page5of4.ms;

import static org.fest.assertions.Assertions.assertThat;

import java.util.HashMap;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;

import com.page5of4.ms.impl.TopologyConfiguration;
import com.page5of4.ms.subscriptions.messages.SubscribeMessage;

public class TopologyConfigurationSpecs {
   private TopologyConfiguration topology;

   @Before
   public void before() {
      BusConfiguration configuration = new BusConfiguration("test", "testing-server");
      Map<String, String> properties = new HashMap<String, String>();
      properties.put("bus.owner.com.page5of4.ms", "remote.{messageType}@server");
      configuration.addProperties(properties);
      topology = new TopologyConfiguration(configuration);
   }

   @Test(expected = BusException.class)
   public void when_getting_owner_thats_missing_should_throw() {
      topology.getOwner(Long.class);
   }

   @Test
   public void when_getting_owning_address_of_message() {
      assertThat(topology.getOwner(ExampleMessage.class)).isEqualTo(new EndpointAddress("remote.com.page5of4.ms.TopologyConfigurationSpecs$ExampleMessage@server"));
   }

   @Test
   public void when_getting_local_address_of_message() {
      assertThat(topology.getLocalAddressOf(ExampleMessage.class)).isEqualTo(new EndpointAddress("test.com.page5of4.ms.TopologyConfigurationSpecs$ExampleMessage@testing-server"));
   }

   @Test
   public void when_getting_subscription_address_of_message() {
      assertThat(topology.getSubscriptionAddressOf(ExampleMessage.class, SubscribeMessage.class)).isEqualTo(new EndpointAddress("remote.com.page5of4.ms.subscriptions.messages.SubscribeMessage@server"));
   }

   public static class ExampleMessage {}
}
