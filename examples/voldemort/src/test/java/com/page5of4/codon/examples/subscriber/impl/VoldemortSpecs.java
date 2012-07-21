package com.page5of4.codon.examples.subscriber.impl;

import java.io.IOException;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import voldemort.client.ClientConfig;
import voldemort.client.SocketStoreClientFactory;
import voldemort.client.StoreClient;
import voldemort.client.StoreClientFactory;
import voldemort.versioning.Versioned;

import com.page5of4.codon.examples.subscriber.impl.VoldemortClusterBuilder.VoldemortCluster;

public class VoldemortSpecs {
   private static final Logger logger = LoggerFactory.getLogger(VoldemortSpecs.class);

   @Test
   public void test() throws IOException {
      VoldemortCluster cluster = VoldemortClusterBuilder.make().numberOfNodes(2).withStringBackedStore("junk").start();

      String bootstrapUrl = cluster.getBootstrapUrl();
      StoreClientFactory factory = new SocketStoreClientFactory(new ClientConfig().setBootstrapUrls(bootstrapUrl));
      StoreClient<String, String> client = factory.getStoreClient("junk");
      Versioned<String> version = client.get("some_key", new Versioned<String>(""));
      version.setObject("new_value");
      client.put("some_key", version);

      cluster.stop();
   }
}
