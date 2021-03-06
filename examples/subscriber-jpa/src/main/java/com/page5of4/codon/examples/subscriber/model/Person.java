package com.page5of4.codon.examples.subscriber.model;

import java.util.Date;
import java.util.UUID;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.sun.istack.NotNull;

@Entity
public class Person {
   @Id
   @GeneratedValue(strategy = GenerationType.AUTO)
   private Long id;
   @NotNull
   private String firstName;
   @NotNull
   private String lastName;
   @NotNull
   @Temporal(value = TemporalType.TIMESTAMP)
   private Date registeredAt;

   public Long getId() {
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

   protected Person() {
      super();
   }

   public Person(UUID id, String firstName, String lastName, Date registeredAt) {
      super();
      // this.id = id;
      this.firstName = firstName;
      this.lastName = lastName;
      this.registeredAt = registeredAt;
   }
}
