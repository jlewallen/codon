package com.page5of4.codon.tests;

import static com.page5of4.codon.support.BundleAssert.assertThat;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.ops4j.pax.exam.Option;
import org.ops4j.pax.exam.junit.Configuration;
import org.ops4j.pax.exam.junit.ExamReactorStrategy;
import org.ops4j.pax.exam.junit.JUnit4TestRunner;
import org.ops4j.pax.exam.spi.reactors.AllConfinedStagedReactorFactory;

import com.page5of4.codon.support.Provision;
import com.page5of4.codon.support.WithContainer;

@RunWith(JUnit4TestRunner.class)
@ExamReactorStrategy(AllConfinedStagedReactorFactory.class)
public class WhenRunningExampleApplicationSpecs extends WithContainer {
   @Configuration
   public Option[] config() {
      return new Option[] { commonConfiguration() };
   }

   @Before
   public void before() {
      Provision.with(executor()).base().hibernate().core();
      executor().executeCommands("features:install codon-example");
      pause();
   }

   @Test
   public void bundles_are_installed_and_active() throws InterruptedException {
      String[] expected = new String[] {
            "com.page5of4.codon.examples.messages",
            "com.page5of4.codon.examples.application"
      };
      for(String name : expected) {
         assertThat(executor().getInstalledBundle(name)).isActive();
      }
   }

   @After
   public void after() {
      executor().executeCommands("features:uninstall codon-example");
      executor().executeCommands("features:uninstall codon-core");
   }
}
