package com.page5of4.ms.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.page5of4.ms.EndpointAddress;

@Service
public class TopologyConfiguration {
   private final AddressNamingConvention namingConvention;

   @Autowired
   public TopologyConfiguration(AddressNamingConvention namingConvention) {
      super();
      this.namingConvention = namingConvention;
   }

   public EndpointAddress getOwner(Class<?> message) {
      return null;
   }

   public EndpointAddress getLocalAddressOf(Class<?> message) {
      return null;
   }

   public EndpointAddress getSubscriptionAddressOf(Class<?> messageType) {
      return null;
   }
}
