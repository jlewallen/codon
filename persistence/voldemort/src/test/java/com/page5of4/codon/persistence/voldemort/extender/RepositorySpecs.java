package com.page5of4.codon.persistence.voldemort.extender;

import static org.fest.assertions.Assertions.assertThat;

import java.util.Date;
import java.util.UUID;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import voldemort.client.ClientConfig;
import voldemort.client.SocketStoreClientFactory;
import voldemort.serialization.SerializerDefinition;

import com.page5of4.codon.persistence.voldemort.VoldemortRepository;
import com.page5of4.nagini.SerializerFactories;
import com.page5of4.nagini.VoldemortCluster;
import com.page5of4.nagini.VoldemortClusterBuilder;

public class RepositorySpecs {
   private VoldemortRepository<UUID, User> repository;
   private VoldemortCluster cluster;

   @Before
   public void before() {
      cluster = VoldemortClusterBuilder.make().numberOfNodes(2).withStore("user", new SerializerDefinition("uuid"), new SerializerDefinition("gson", "user")).start();
      SocketStoreClientFactory storeClientFactory = new SocketStoreClientFactory(new ClientConfig().setSerializerFactory(SerializerFactories.builder().mapSchema("user", User.class).build()).setBootstrapUrls(cluster.getBootstrapUrl()));
      repository = new VoldemortRepository<UUID, User>(storeClientFactory.<UUID, User> getStoreClient("user"));
   }

   @After
   public void after() {
      cluster.stop();
   }

   @Test
   public void when_adding() {
      UUID id = UUID.randomUUID();
      repository.add(id, new User(id, "Jacob", "Lewallen", new Date()));
      assertThat(repository.get(id)).isNotNull();
   }
}
