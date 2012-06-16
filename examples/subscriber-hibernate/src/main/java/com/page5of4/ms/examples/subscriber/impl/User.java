package com.page5of4.ms.examples.subscriber.impl;

import java.util.Date;
import java.util.UUID;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import com.sun.istack.NotNull;

@Entity
public class User {
   @Id
   @GeneratedValue(strategy = GenerationType.AUTO)
   private final UUID id;
   @NotNull
   private String firstName;
   @NotNull
   private String lastName;
   @NotNull
   private Date registeredAt;

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
