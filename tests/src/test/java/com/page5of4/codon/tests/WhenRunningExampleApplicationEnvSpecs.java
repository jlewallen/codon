package com.page5of4.codon.tests;

import static com.page5of4.codon.tests.support.BundleAssert.assertThat;
import static com.page5of4.codon.tests.support.CodonKarafDistributionOption.featuresBoot;

import org.junit.After;
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
public class WhenRunningExampleApplicationEnvSpecs extends WithContainer {
   @Configuration
   public Option[] config() {
      return new Option[] { commonConfiguration(), featuresBoot("config", "codon-dependencies", "codon-core", "activemq", "activemq-spring") };
   }

   @Before
   public void before() {
      Provision.with(executor()).broker().base().hibernate();
      executor().executeCommands("osgi:install -s mvn:com.page5of4.codon.examples/codon-examples-env/" + TestsConfiguration.getProjectVersion());
      pause();
   }

   @Test
   public void bundles_are_installed_and_active() {
      String[] expected = new String[] {
            "com.page5of4.codon.examples.env"
      };
      for(String name : expected) {
         assertThat(executor().getInstalledBundle(name)).isActive();
      }
   }

   @After
   public void after() {

   }
}
