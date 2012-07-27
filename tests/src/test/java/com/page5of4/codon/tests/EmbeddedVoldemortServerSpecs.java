package com.page5of4.codon.tests;

import static com.page5of4.codon.tests.support.BundleAssert.assertThat;
import static com.page5of4.codon.tests.support.CodonKarafDistributionOption.featuresBoot;

import java.util.UUID;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.ops4j.pax.exam.Option;
import org.ops4j.pax.exam.junit.Configuration;
import org.ops4j.pax.exam.junit.ExamReactorStrategy;
import org.ops4j.pax.exam.junit.JUnit4TestRunner;
import org.ops4j.pax.exam.spi.reactors.EagerSingleStagedReactorFactory;

import voldemort.client.ClientConfig;
import voldemort.client.SocketStoreClientFactory;
import voldemort.client.StoreClient;
import voldemort.client.StoreClientFactory;
import voldemort.serialization.SerializerDefinition;
import voldemort.versioning.Versioned;

import com.page5of4.codon.tests.support.WithContainer;
import com.page5of4.nagini.CustomizableSerializerFactory;
import com.page5of4.nagini.VoldemortCluster;
import com.page5of4.nagini.VoldemortClusterBuilder;

@RunWith(JUnit4TestRunner.class)
@ExamReactorStrategy(EagerSingleStagedReactorFactory.class)
public class EmbeddedVoldemortServerSpecs extends WithContainer {
   @Configuration
   public Option[] config() {
      return new Option[] {
            commonConfiguration(),
            featuresBoot("config", "codon-dependencies", "codon-core", "codon-persistence-voldemort")
      };
   }

   @Test
   public void bundle_is_active() {
      assertThat(executor().getInstalledBundle("com.page5of4.codon.bundles.voldemort")).isActive();
      assertThat(executor().getInstalledBundle("com.page5of4.nagini")).isActive();
   }

   @Test
   public void able_start_server() {
      VoldemortCluster cluster = VoldemortClusterBuilder.make().
            numberOfNodes(2).
            withStore("junk", new SerializerDefinition("uuid"), new SerializerDefinition("string")).
            start();

      String bootstrapUrl = cluster.getBootstrapUrl();
      StoreClientFactory factory = new SocketStoreClientFactory(new ClientConfig().setSerializerFactory(new CustomizableSerializerFactory()).setBootstrapUrls(bootstrapUrl));
      StoreClient<UUID, String> client = factory.getStoreClient("junk");

      UUID id = UUID.randomUUID();
      for(short i = 0; i < 10; ++i) {
         Versioned<String> version = client.get(id, new Versioned<String>(""));
         logger.info("Got: " + version.getValue());
         version.setObject("#" + i);
         client.put(id, version);
      }

      cluster.stop();
   }
}
