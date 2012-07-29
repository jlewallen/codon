package com.page5of4.codon.tests;

import static com.page5of4.codon.tests.support.CodonKarafDistributionOption.featuresBoot;
import static org.fest.assertions.Assertions.assertThat;

import java.util.Date;
import java.util.Dictionary;
import java.util.Hashtable;
import java.util.UUID;
import java.util.concurrent.Callable;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.ops4j.pax.exam.Option;
import org.ops4j.pax.exam.junit.Configuration;
import org.ops4j.pax.exam.junit.ExamReactorStrategy;
import org.ops4j.pax.exam.junit.JUnit4TestRunner;
import org.ops4j.pax.exam.spi.reactors.EagerSingleStagedReactorFactory;
import org.ops4j.pax.swissbox.core.ContextClassLoaderUtils;

import com.page5of4.codon.persistence.voldemort.extender.VoldemortServerFactory;
import com.page5of4.codon.tests.support.WithContainer;
import com.page5of4.codon.useful.repositories.GenericRepositoryProxy;
import com.page5of4.codon.useful.repositories.Repository;
import com.page5of4.codon.useful.repositories.RepositoryFactory;

@RunWith(JUnit4TestRunner.class)
@ExamReactorStrategy(EagerSingleStagedReactorFactory.class)
public class VoldemortPersistenceSpecs extends WithContainer {
   @Configuration
   public Option[] config() {
      return new Option[] {
            commonConfiguration(),
            featuresBoot("config", "codon-dependencies", "codon-core", "codon-persistence-voldemort")
      };
   }

   @Test
   public void when_configuring_an_embedded_server() throws Exception {
      org.osgi.service.cm.Configuration configuration = helper().getConfigurationAdmin().createFactoryConfiguration(VoldemortServerFactory.NAME, null);
      Dictionary<String, Object> properties = new Hashtable<String, Object>();
      properties.put("bootstrap.url", "true");
      properties.put("cluster.nodes.number", "2");
      properties.put("store.user.serializer.keys", "uuid");
      properties.put("store.user.serializer.values", "gson");
      properties.put("store.user.schema", "user");
      properties.put("schema.user", User.class.getName());
      configuration.update(properties);

      ContextClassLoaderUtils.doWithClassLoader(getClass().getClassLoader(), new Callable<Object>() {
         @Override
         public Object call() throws Exception {
            RepositoryFactory repositoryFactory = helper().getService(RepositoryFactory.class);
            GenericRepositoryProxy<UUID, User> repository = new GenericRepositoryProxy<UUID, User>(repositoryFactory, UserRepository.class);
            UUID id = UUID.randomUUID();
            repository.add(id, new User(id, "Jacob", "Lewallen", new Date()));

            assertThat(repository.get(id)).isNotNull();
            return new Object();
         }
      });
   }

   public interface UserRepository extends Repository<UUID, User> {

   }
}
