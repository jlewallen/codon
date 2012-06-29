package com.page5of4.codon.tests;

import static com.page5of4.codon.tests.support.BundleAssert.assertThat;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.ops4j.pax.exam.Option;
import org.ops4j.pax.exam.junit.Configuration;
import org.ops4j.pax.exam.junit.ExamReactorStrategy;
import org.ops4j.pax.exam.junit.JUnit4TestRunner;
import org.ops4j.pax.exam.spi.reactors.AllConfinedStagedReactorFactory;

import com.page5of4.codon.tests.support.Provision;
import com.page5of4.codon.tests.support.TestsConfiguration;
import com.page5of4.codon.tests.support.WithContainer;

@RunWith(JUnit4TestRunner.class)
@ExamReactorStrategy(AllConfinedStagedReactorFactory.class)
public class WhenRunningJpaExample extends WithContainer {
   @Configuration
   public Option[] config() {
      return new Option[] { commonConfiguration() };
   }

   @Before
   public void before() {

   }

   @Test
   public void using_hibernate() {
      Provision.with(executor()).base().hibernate().atomikos().h2().core();
      executor().executeCommand("osgi:install -s mvn:com.page5of4.codon.examples/codon-examples-messages/" + TestsConfiguration.getProjectVersion());
      executor().executeCommand("osgi:install -s mvn:com.page5of4.codon.examples/codon-examples-subscriber-jpa/" + TestsConfiguration.getProjectVersion());
      pause();

      assertThat(executor().getInstalledBundle("com.page5of4.codon.examples.subscriber.jpa")).isActive();
   }

   @Test
   public void using_eclipselink() {
      Provision.with(executor()).base().eclipseLink().atomikos().h2().core();
      executor().executeCommand("osgi:install -s mvn:com.page5of4.codon.examples/codon-examples-messages/" + TestsConfiguration.getProjectVersion());
      executor().executeCommand("osgi:install -s mvn:com.page5of4.codon.examples/codon-examples-subscriber-jpa/" + TestsConfiguration.getProjectVersion());
      pause();

      assertThat(executor().getInstalledBundle("com.page5of4.codon.examples.subscriber.jpa")).isActive();
   }
}
