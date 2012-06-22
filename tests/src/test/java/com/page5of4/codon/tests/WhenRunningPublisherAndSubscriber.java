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
public class WhenRunningPublisherAndSubscriber extends WithContainer {
   @Configuration
   public Option[] config() {
      return new Option[] { commonConfiguration() };
   }

   @Before
   public void before() {
      Provision.with(executor()).base();
   }

   @Test
   public void bundle_is_installed() throws InterruptedException {
      executor().executeCommands(
            "features:install codon-core",
            "features:install codon-example"
            );
      Thread.sleep(10000);
      assertThat(executor().getInstalledBundle("com.page5of4.codon.examples.publisher")).isActive();
   }

   @After
   public void after() {
      executor().executeCommands("features:uninstall codon-example");
   }
}
