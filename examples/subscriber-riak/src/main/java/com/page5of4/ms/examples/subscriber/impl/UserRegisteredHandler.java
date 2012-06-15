package com.page5of4.ms.examples.subscriber.impl;

import java.util.Date;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.basho.riak.client.IRiakClient;
import com.basho.riak.client.RiakException;
import com.basho.riak.client.bucket.Bucket;
import com.basho.riak.client.operations.StoreObject;
import com.page5of4.ms.Bus;
import com.page5of4.ms.MessageHandler;
import com.page5of4.ms.examples.messages.UserRegisteredMessage;

@Service
@MessageHandler
public class UserRegisteredHandler {
   private static final Logger logger = LoggerFactory.getLogger(UserRegisteredHandler.class);
   private final Bus bus;
   private final RiakDataSource dataSource;

   @Autowired
   public UserRegisteredHandler(Bus bus, RiakDataSource dataSource) {
      super();
      this.bus = bus;
      this.dataSource = dataSource;
   }

   @MessageHandler
   public void handle(UserRegisteredMessage message) throws RiakException {
      logger.info("Received {}", message);

      User user = new User(message.getId(), message.getFirstName(), message.getLastName(), message.getRegisteredAt());

      IRiakClient riakClient = dataSource.getRiakClient();
      Bucket users = riakClient.createBucket("users").execute();
      StoreObject<User> stored = users.store(user.getId().toString(), user);
      stored.execute();
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

      public User(UUID id, String firstName, String lastName, Date registeredAt) {
         super();
         this.id = id;
         this.firstName = firstName;
         this.lastName = lastName;
         this.registeredAt = registeredAt;
      }
   }
}
