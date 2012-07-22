package com.page5of4.codon.tests;

import static com.page5of4.codon.tests.support.BundleAssert.assertThat;
import static org.ops4j.pax.exam.CoreOptions.mavenBundle;

import java.util.UUID;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.ops4j.pax.exam.Option;
import org.ops4j.pax.exam.junit.Configuration;
import org.ops4j.pax.exam.junit.ExamReactorStrategy;
import org.ops4j.pax.exam.junit.JUnit4TestRunner;
import org.ops4j.pax.exam.spi.reactors.AllConfinedStagedReactorFactory;

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
@ExamReactorStrategy(AllConfinedStagedReactorFactory.class)
public class WhenInstallingVoldemort extends WithContainer {
   @Configuration
   public Option[] config() {
      return new Option[] {
            commonConfiguration(),
            mavenBundle().groupId("com.google.guava").artifactId("guava").version("12.0"),
            mavenBundle().groupId("org.apache.servicemix.bundles").artifactId("org.apache.servicemix.bundles.jdom").version("1.1_3"),
            mavenBundle().groupId("commons-codec").artifactId("commons-codec").version("1.6"),
            mavenBundle().groupId("com.page5of4.codon.bundles").artifactId("com.page5of4.codon.bundles.voldemort").version("0.90.1"),
            mavenBundle().groupId("com.page5of4.nagini").artifactId("nagini").version("1.0.0-SNAPSHOT")
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
