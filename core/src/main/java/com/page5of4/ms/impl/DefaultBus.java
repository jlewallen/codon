package com.page5of4.ms.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.page5of4.ms.Bus;
import com.page5of4.ms.EndpointAddress;
import com.page5of4.ms.subscriptions.SubscriptionStorage;
import com.page5of4.ms.subscriptions.messages.SubscribeMessage;
import com.page5of4.ms.subscriptions.messages.UnsubscribeMessage;

@Service
public class DefaultBus implements Bus {
   private static final Logger logger = LoggerFactory.getLogger(DefaultBus.class);
   private final TopologyConfiguration topologyConfiguration;
   private final SubscriptionStorage subscriptionStorage;
   private final Transport transport;

   public DefaultBus(TopologyConfiguration topologyConfiguration, Transport transport, SubscriptionStorage subscriptionStorage) {
      this.topologyConfiguration = topologyConfiguration;
      this.transport = transport;
      this.subscriptionStorage = subscriptionStorage;
      logger.info("Constructed");
   }

   @Override
   public <T> void publish(T message) {
      logger.info("Publish {}", message);
      for(EndpointAddress subscriber : subscriptionStorage.findAllSubscribers(MessageUtils.getMessageType(message))) {
         transport.send(subscriber, message);
      }
   }

   @Override
   public <T> void send(T message) {
      logger.info("Send {}", message);
      transport.send(topologyConfiguration.getOwner(message.getClass()), message);
   }

   @Override
   public <T> void sendLocal(T message) {
      logger.info("SendLocal {}", message);
      transport.send(topologyConfiguration.getLocalAddressOf(message.getClass()), message);
   }

   public void subscribe(Class<?> messageType) {
      EndpointAddress local = topologyConfiguration.getLocalAddressOf(messageType);
      transport.send(topologyConfiguration.getSubscriptionAddressOf(messageType), new SubscribeMessage(local.toString(), MessageUtils.getMessageType(messageType)));
   }

   public void unsubscribe(Class<?> messageType) {
      EndpointAddress local = topologyConfiguration.getLocalAddressOf(messageType);
      transport.send(topologyConfiguration.getSubscriptionAddressOf(messageType), new UnsubscribeMessage(local.toString(), MessageUtils.getMessageType(messageType)));
   }
}
