package com.page5of4.codon.persistence.voldemort.extender;

import static org.fest.assertions.Assertions.assertThat;

import java.util.Date;
import java.util.UUID;

import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import voldemort.client.ClientConfig;
import voldemort.client.SocketStoreClientFactory;
import voldemort.serialization.SerializerDefinition;

import com.page5of4.codon.persistence.voldemort.VoldemortRepository;
import com.page5of4.nagini.SerializerFactories;
import com.page5of4.nagini.VoldemortCluster;
import com.page5of4.nagini.VoldemortClusterBuilder;

public class RepositorySpecs {
   private static VoldemortCluster cluster;
   private static SocketStoreClientFactory storeClientFactory;
   private VoldemortRepository<UUID, User> repository;

   @BeforeClass
   public static void beforeClass() {
      cluster = VoldemortClusterBuilder.make().numberOfNodes(2).withStore("user", new SerializerDefinition("uuid"), new SerializerDefinition("gson", "user")).start();
      storeClientFactory = new SocketStoreClientFactory(new ClientConfig().setSerializerFactory(SerializerFactories.builder().mapSchema("user", User.class).build()).setBootstrapUrls(cluster.getBootstrapUrl()));
   }

   @Before
   public void before() {
      repository = new VoldemortRepository<UUID, User>(storeClientFactory.<UUID, User> getStoreClient("user"));
   }

   @AfterClass
   public static void afterClass() {
      cluster.stop();
   }

   @Test
   public void when_adding() {
      UUID id = UUID.randomUUID();
      repository.add(id, new User(id, "Jacob", "Lewallen", new Date()));
      assertThat(repository.get(id)).isNotNull();
   }

   @Test
   public void when_adding_and_overwriting() {
      UUID id = UUID.randomUUID();
      repository.add(id, new User(id, "Jacob", "Lewallen", new Date()));
      assertThat(repository.get(id)).isNotNull();
      repository.add(id, new User(id, "Andrea", "Lewallen", new Date()));
      assertThat(repository.get(id).getFirstName()).isEqualTo("Andrea");
   }

   @Test
   public void when_removing() {
      UUID id = UUID.randomUUID();
      repository.add(id, new User(id, "Jacob", "Lewallen", new Date()));
      assertThat(repository.get(id)).isNotNull();
      repository.delete(id);
      assertThat(repository.get(id)).isNull();
   }
}
