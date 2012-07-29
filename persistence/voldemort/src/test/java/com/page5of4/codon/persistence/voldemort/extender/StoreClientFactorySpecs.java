package com.page5of4.codon.persistence.voldemort.extender;

import static org.fest.assertions.Assertions.assertThat;

import java.util.Dictionary;
import java.util.Hashtable;

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
      properties.put("schema.user", User.class.getName());

      storeClientFactory.updated("com.page5of4.testing", properties);

      assertThat(storeClientFactory.getRegistrations().get("com.page5of4.testing")).isNotNull();

      storeClientFactory.deleted("com.page5of4.testing");

      assertThat(storeClientFactory.getRegistrations().get("com.page5of4.testing")).isNull();
   }
}
