package com.page5of4.codon.examples.messages;

import java.io.Serializable;
import java.util.Date;
import java.util.UUID;

public class UserRegisteredMessage implements Serializable {
   private static final long serialVersionUID = 1L;

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

   public UserRegisteredMessage(UUID id, String firstName, String lastName, Date registeredAt) {
      super();
      this.id = id;
      this.firstName = firstName;
      this.lastName = lastName;
      this.registeredAt = registeredAt;
   }

   @Override
   public String toString() {
      return "UserRegisteredMessage [id=" + id + ", firstName=" + firstName + ", lastName=" + lastName + ", registeredAt=" + registeredAt + "]";
   }
}
