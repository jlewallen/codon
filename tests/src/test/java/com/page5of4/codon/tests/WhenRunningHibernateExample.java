package com.page5of4.codon.tests;

import static com.page5of4.codon.support.BundleAssert.assertThat;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.ops4j.pax.exam.Option;
import org.ops4j.pax.exam.junit.Configuration;
import org.ops4j.pax.exam.junit.ExamReactorStrategy;
import org.ops4j.pax.exam.junit.JUnit4TestRunner;
import org.ops4j.pax.exam.spi.reactors.AllConfinedStagedReactorFactory;

import com.page5of4.codon.support.Provision;
import com.page5of4.codon.support.TestsConfiguration;
import com.page5of4.codon.support.WithContainer;

@RunWith(JUnit4TestRunner.class)
@ExamReactorStrategy(AllConfinedStagedReactorFactory.class)
public class WhenRunningHibernateExample extends WithContainer {
   @Configuration
   public Option[] config() {
      return new Option[] { commonConfiguration() };
   }

   @Before
   public void before() {
      Provision.with(executor()).base().hibernate().core();
      executor().executeCommand("osgi:install -s mvn:com.page5of4.codon.examples/codon-examples-messages/" + TestsConfiguration.getProjectVersion());
      executor().executeCommand("osgi:install -s mvn:com.page5of4.codon.examples/codon-examples-subscriber-hibernate/" + TestsConfiguration.getProjectVersion());
      pause();
   }

   @Test
   public void bundle_is_installed() {
      assertThat(executor().getInstalledBundle("com.page5of4.codon.bundles.hibernate")).isActive();
   }
}
