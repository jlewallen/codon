package com.page5of4.codon.examples.publisher.impl;

import java.util.Date;
import java.util.UUID;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.page5of4.codon.Bus;
import com.page5of4.codon.MessageHandler;
import com.page5of4.codon.examples.messages.LaunchWorkMessage;
import com.page5of4.codon.examples.messages.UserRegisteredMessage;

@Service
@MessageHandler
public class Publisher {
   private static final Logger logger = LoggerFactory.getLogger(Publisher.class);
   private final Bus bus;

   @Autowired
   public Publisher(Bus bus) {
      super();
      this.bus = bus;
   }

   @MessageHandler
   public void launchWork(LaunchWorkMessage message) {
      bus.publish(new UserRegisteredMessage(UUID.randomUUID(), "Jacob", "Lewallen", new Date()));
   }

   @PostConstruct
   public void publish() {
      bus.publish(new UserRegisteredMessage(UUID.randomUUID(), "Jacob", "Lewallen", new Date()));
   }
}
