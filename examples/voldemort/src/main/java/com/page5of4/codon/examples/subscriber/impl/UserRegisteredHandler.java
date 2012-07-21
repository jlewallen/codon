package com.page5of4.codon.examples.subscriber.impl;

import java.util.Date;
import java.util.UUID;

import org.codehaus.jackson.annotate.JsonCreator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.page5of4.codon.Bus;
import com.page5of4.codon.MessageHandler;
import com.page5of4.codon.examples.messages.UserRegisteredMessage;

@Service
@MessageHandler
public class UserRegisteredHandler {
   private static final Logger logger = LoggerFactory.getLogger(UserRegisteredHandler.class);
   private final Bus bus;

   @Autowired
   public UserRegisteredHandler(Bus bus) {
      super();
      this.bus = bus;
   }

   @MessageHandler
   public void handle(UserRegisteredMessage message) {

   }

   public static class User {
      private final UUID id;
      private final String firstName;
      private final String lastName;
      private final Date registeredAt;

      public UUID getId() {
         return id;
      }

      public String getFirstName() {
         return firstName;
      }

      public String getLastName() {
         return lastName;
      }

      public Date getRegisteredAt() {
         return registeredAt;
      }

      @JsonCreator
      public User(UUID id, String firstName, String lastName, Date registeredAt) {
         super();
         this.id = id;
         this.firstName = firstName;
         this.lastName = lastName;
         this.registeredAt = registeredAt;
      }
   }
}
