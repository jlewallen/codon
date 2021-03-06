package com.page5of4.codon.tests;

import static com.page5of4.codon.tests.support.BundleAssert.assertThat;
import static com.page5of4.codon.tests.support.CodonKarafDistributionOption.featuresBoot;
import static org.fest.assertions.Assertions.assertThat;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.ops4j.pax.exam.Option;
import org.ops4j.pax.exam.junit.Configuration;
import org.ops4j.pax.exam.junit.ExamReactorStrategy;
import org.ops4j.pax.exam.junit.JUnit4TestRunner;
import org.ops4j.pax.exam.spi.reactors.AllConfinedStagedReactorFactory;

import com.page5of4.codon.Bus;
import com.page5of4.codon.tests.support.Provision;
import com.page5of4.codon.tests.support.WithContainer;

@RunWith(JUnit4TestRunner.class)
@ExamReactorStrategy(AllConfinedStagedReactorFactory.class)
public class WhenRunningExampleApplicationSpecs extends WithContainer {
   @Configuration
   public Option[] config() {
      return new Option[] { commonConfiguration(), featuresBoot("config", "codon-dependencies", "codon-core", "activemq", "activemq-spring") };
   }

   @Before
   public void before() {
      Provision.with(executor()).broker().base().core().hibernate();
      executor().executeCommands("features:install codon-example");
      pause();
   }

   @Test
   public void bundles_are_installed_and_active() {
      String[] expected = new String[] {
            "com.page5of4.codon.examples.messages",
            "com.page5of4.codon.examples.application",
            "com.page5of4.codon.examples.subscriber"
      };
      for(String name : expected) {
         assertThat(executor().getInstalledBundle(name)).isActive();
      }
   }

   @Test
   public void can_get_bus() {
      Bus bus = helper().getBus();
      assertThat(bus).isNotNull();
   }

   @After
   public void after() {
      executor().executeCommands("features:uninstall codon-example");
   }
}
