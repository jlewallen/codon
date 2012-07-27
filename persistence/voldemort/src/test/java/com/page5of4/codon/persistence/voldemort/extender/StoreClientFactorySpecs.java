package com.page5of4.codon.persistence.voldemort.extender;

import java.util.Date;
import java.util.Dictionary;
import java.util.Hashtable;
import java.util.UUID;

import org.junit.Before;
import org.junit.Test;
import org.osgi.service.cm.ConfigurationException;
import org.springframework.osgi.mock.MockBundleContext;

public class StoreClientFactorySpecs {
   private StoreClientFactory storeClientFactory;

   @Before
   public void before() {
      storeClientFactory = new StoreClientFactory(new MockBundleContext());
   }

   @Test
   public void when_updating_with_configuration() throws ConfigurationException {
      Dictionary<String, Object> properties = new Hashtable<String, Object>();
      properties.put("bootstrap.url", "tcp://127.0.0.1:5466");
      storeClientFactory.updated("com.page5of4.testing", properties);
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
