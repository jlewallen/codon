package com.page5of4.ms.impl;

import java.util.Collections;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.page5of4.ms.BusConfiguration;
import com.page5of4.ms.EndpointAddress;

@Service
public class TopologyConfiguration {
   private final BusConfiguration configuration;

   @Autowired
   public TopologyConfiguration(BusConfiguration configuration) {
      super();
      this.configuration = configuration;
   }

   public EndpointAddress getOwner(Class<?> message) {
      String messageType = MessageUtils.getMessageType(message);
      String ownerAddress = configuration.getOwnerAddress(messageType);
      return new EndpointAddress(replace(ownerAddress, Collections.singletonMap("messageType", messageType)));
   }

   public EndpointAddress getLocalAddressOf(Class<?> message) {
      String messageType = MessageUtils.getMessageType(message);
      String localAddress = configuration.getLocalAddress(messageType);
      return new EndpointAddress(replace(localAddress, Collections.singletonMap("messageType", messageType)));
   }

   public EndpointAddress getSubscriptionAddressOf(Class<?> otherMessage, Class<?> message) {
      String ownerAddress = configuration.getOwnerAddress(MessageUtils.getMessageType(otherMessage));
      return new EndpointAddress(replace(ownerAddress, Collections.singletonMap("messageType", MessageUtils.getMessageType(message))));
   }

   private String replace(String template, Map<String, String> values) {
      String value = template;
      for(Map.Entry<String, String> entry : values.entrySet()) {
         value = value.replace("{" + entry.getKey() + "}", entry.getValue());
      }
      return value;
   }
}
