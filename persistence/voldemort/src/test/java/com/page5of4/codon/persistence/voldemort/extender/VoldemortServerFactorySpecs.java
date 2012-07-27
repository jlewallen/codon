package com.page5of4.codon.persistence.voldemort.extender;

import java.util.Dictionary;
import java.util.Hashtable;

import org.junit.Before;
import org.junit.Test;
import org.osgi.service.cm.ConfigurationException;
import org.springframework.osgi.mock.MockBundleContext;

public class VoldemortServerFactorySpecs {
   private VoldemortServerFactory serverFactory;
   private StoreClientFactory storeClientFactory;

   @Before
   public void before() {
      storeClientFactory = new StoreClientFactory(new MockBundleContext());
      serverFactory = new VoldemortServerFactory(storeClientFactory);
   }

   @Test
   public void when_updating_with_configuration() throws ConfigurationException {
      Dictionary<String, Object> properties = new Hashtable<String, Object>();
      properties.put("store.user.serializer.keys", "uuid");
      properties.put("store.user.serializer.values", "gson");
      properties.put("store.user.schema", User.class.getName());
      serverFactory.updated("com.page5of4.testing", properties);

      serverFactory.deleted("com.page5of4.testing");
   }
}
