package com.page5of4.ms.tests;

import static com.page5of4.ms.support.BundleAssert.assertThat;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.ops4j.pax.exam.Option;
import org.ops4j.pax.exam.junit.Configuration;
import org.ops4j.pax.exam.junit.ExamReactorStrategy;
import org.ops4j.pax.exam.junit.JUnit4TestRunner;
import org.ops4j.pax.exam.spi.reactors.AllConfinedStagedReactorFactory;

import com.page5of4.ms.support.Provision;
import com.page5of4.ms.support.TestsConfiguration;
import com.page5of4.ms.support.WithContainer;

@RunWith(JUnit4TestRunner.class)
@ExamReactorStrategy(AllConfinedStagedReactorFactory.class)
public class WhenRunningHibernateExample extends WithContainer {
   @Configuration
   public Option[] config() {
      return new Option[] { commonConfiguration() };
   }

   @Before
   public void before() {
      Provision.with(executor()).base().core();

      executor().executeCommand("osgi:install -s mvn:org.knowhowlab.osgi.shell/felix-gogo/1.0.1");
      executor().executeCommand("osgi:install -s mvn:org.osgi/org.osgi.compendium/4.2.0");
      executor().executeCommand("osgi:install -s mvn:org.osgi/org.osgi.enterprise/4.2.0");
      executor().executeCommand("osgi:install -s mvn:org.apache.geronimo.specs/geronimo-jpa_2.0_spec/1.1");
      executor().executeCommand("osgi:install -s mvn:org.apache.derby/derby/10.8.1.2");

      executor().executeCommand("osgi:install -s mvn:commons-collections/commons-collections/3.2.1");
      // executor().executeCommand("osgi:install -s mvn:org.slf4j/slf4j-api/1.6.1");
      // executor().executeCommand("osgi:install -s mvn:org.slf4j/slf4j-simple/1.6.1@nostart");
      executor().executeCommand("osgi:install -s mvn:org.apache.geronimo.specs/geronimo-jta_1.1_spec/1.1.1");
      executor().executeCommand("osgi:install -s mvn:org.apache.servicemix.bundles/org.apache.servicemix.bundles.serp/1.13.1_3");
      executor().executeCommand("osgi:install -s mvn:org.apache.aries/org.apache.aries.util/0.3");
      executor().executeCommand("osgi:install -s mvn:org.apache.aries.jpa/org.apache.aries.jpa.api/0.3");
      executor().executeCommand("osgi:install -s mvn:org.apache.aries.jpa/org.apache.aries.jpa.container/0.3");
      executor().executeCommand("osgi:install -s mvn:org.apache.aries.transaction/org.apache.aries.transaction.manager/0.3");

      executor().executeCommand("osgi:install -s mvn:javax.persistence/com.springsource.javax.persistence/2.0.0");
      executor().executeCommand("osgi:install -s mvn:javax.transaction/com.springsource.javax.transaction/1.1.0");
      executor().executeCommand("osgi:install -s mvn:org.apache.commons/com.springsource.org.apache.commons.collections/3.2.1");
      executor().executeCommand("osgi:install -s mvn:com.page5of4.ms.bundles/com.page5of4.ms.bundles.hibernate/4.0.0");
      executor().executeCommand("osgi:install -s mvn:com.page5of4.ms.examples/messages/" + TestsConfiguration.getProjectVersion());
      executor().executeCommand("osgi:install -s mvn:com.page5of4.ms.examples/subscriber-hibernate/" + TestsConfiguration.getProjectVersion());
   }

   @Test
   public void bundle_is_installed() {
      assertThat(executor().getInstalledBundle("com.page5of4.ms.bundles.hibernate")).isActive();
   }
}
