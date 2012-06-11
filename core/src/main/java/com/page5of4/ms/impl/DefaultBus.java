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

   @Override
   public <T> void send(EndpointAddress address, T message) {
      logger.info("Send {} -> {}", message, address);
      transport.send(address, message);
   }

   @Override
   public void subscribe(Class<?> messageType) {
      logger.info("Subscribing {}", messageType);
      EndpointAddress local = topologyConfiguration.getLocalAddressOf(messageType);
      transport.send(topologyConfiguration.getSubscriptionAddressOf(messageType, SubscribeMessage.class), new SubscribeMessage(local.toString(), MessageUtils.getMessageType(messageType)));
   }

   @Override
   public void unsubscribe(Class<?> messageType) {
      logger.info("Unsubscribing {}", messageType);
      EndpointAddress local = topologyConfiguration.getLocalAddressOf(messageType);
      transport.send(topologyConfiguration.getSubscriptionAddressOf(messageType, UnsubscribeMessage.class), new UnsubscribeMessage(local.toString(), MessageUtils.getMessageType(messageType)));
   }
}
