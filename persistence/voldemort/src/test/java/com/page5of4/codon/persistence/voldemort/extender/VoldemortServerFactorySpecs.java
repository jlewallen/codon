package com.page5of4.codon.persistence.voldemort.extender;

import static org.fest.assertions.Assertions.assertThat;

import java.util.Dictionary;
import java.util.Hashtable;

import org.junit.Before;
import org.junit.Test;
import org.osgi.service.cm.ConfigurationException;
import org.springframework.osgi.mock.MockBundleContext;

public class VoldemortServerFactorySpecs {
   private VoldemortServerFactory serverFactory;
   private StoreClientFactory storeClientFactory;
   private MockBundleContext bundleContext;

   @Before
   public void before() {
      bundleContext = new MockBundleContext();
      storeClientFactory = new StoreClientFactory(bundleContext);
      serverFactory = new VoldemortServerFactory(storeClientFactory);
   }

   @Test
   public void when_updating_with_configuration() throws ConfigurationException {
      Dictionary<String, Object> properties = new Hashtable<String, Object>();
      properties.put("store.user.serializer.keys", "uuid");
      properties.put("store.user.serializer.values", "gson");
      properties.put("store.user.schema", User.class.getName());

      serverFactory.updated("com.page5of4.testing", properties);

      assertThat(serverFactory.getClusters().get("com.page5of4.testing")).isNotNull();
      assertThat(storeClientFactory.getRegistrations().get("com.page5of4.testing")).isNotNull();

      serverFactory.deleted("com.page5of4.testing");

      assertThat(serverFactory.getClusters().get("com.page5of4.testing")).isNull();
      assertThat(storeClientFactory.getRegistrations().get("com.page5of4.testing")).isNull();
   }
}
