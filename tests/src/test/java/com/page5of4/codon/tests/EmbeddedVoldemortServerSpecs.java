package com.page5of4.codon.tests;

import static com.page5of4.codon.tests.support.BundleAssert.assertThat;
import static com.page5of4.codon.tests.support.CodonKarafDistributionOption.featuresBoot;

import java.io.IOException;
import java.util.Dictionary;
import java.util.Hashtable;
import java.util.UUID;

import javax.inject.Inject;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.ops4j.pax.exam.Option;
import org.ops4j.pax.exam.junit.Configuration;
import org.ops4j.pax.exam.junit.ExamReactorStrategy;
import org.ops4j.pax.exam.junit.JUnit4TestRunner;
import org.ops4j.pax.exam.spi.reactors.EagerSingleStagedReactorFactory;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceReference;
import org.osgi.service.cm.ConfigurationAdmin;

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
import com.page5of4.nagini.VoldemortSpecs.User;

@RunWith(JUnit4TestRunner.class)
@ExamReactorStrategy(EagerSingleStagedReactorFactory.class)
public class EmbeddedVoldemortServerSpecs extends WithContainer {
   @Inject
   private BundleContext bundleContext;

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

   @Test
   public void when_setting_configuration() throws IOException {
      ServiceReference configurationAdminReference = bundleContext.getServiceReference(ConfigurationAdmin.class.getName());
      if(configurationAdminReference != null) {
         ConfigurationAdmin confAdmin = (ConfigurationAdmin)bundleContext.getService(configurationAdminReference);

         String name = "com.page5of4.codon.persistence.voldemort.extender.StoreClientFactory";
         org.osgi.service.cm.Configuration configuration = confAdmin.createFactoryConfiguration(name);
         Dictionary<String, Object> properties = new Hashtable<String, Object>();
         properties.put("bootstrap.url", "true");
         properties.put("cluster.embedded", "true");
         properties.put("cluster.nodes.number", "2");
         properties.put("store.user.serializer.keys", "uuid");
         properties.put("store.user.serializer.values", "gson");
         properties.put("store.user.schema", User.class.getName());
         configuration.update(properties);
      }
   }
}
