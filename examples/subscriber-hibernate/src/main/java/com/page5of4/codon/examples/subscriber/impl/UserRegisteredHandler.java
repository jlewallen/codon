package com.page5of4.codon.examples.subscriber.impl;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.page5of4.codon.Bus;
import com.page5of4.codon.MessageHandler;
import com.page5of4.codon.examples.messages.UserRegisteredMessage;
import com.page5of4.codon.examples.subscriber.model.User;

@Service
@MessageHandler
public class UserRegisteredHandler {
   private static final Logger logger = LoggerFactory.getLogger(UserRegisteredHandler.class);
   private final Bus bus;

   @PersistenceContext
   private EntityManager entityManager;

   @Autowired
   public UserRegisteredHandler(Bus bus) {
      super();
      this.bus = bus;
   }

   @MessageHandler
   public void handle(UserRegisteredMessage message) {
      logger.info("Received {}", message);
      User user = new User(message.getId(), message.getFirstName(), message.getLastName(), message.getRegisteredAt());
      entityManager.persist(user);
   }
}
